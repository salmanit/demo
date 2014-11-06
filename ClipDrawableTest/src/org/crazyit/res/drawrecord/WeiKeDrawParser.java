package org.crazyit.res.drawrecord;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.zeno.lib.draw.Brush;
import com.zeno.lib.draw.DrawingFactory;
import com.zeno.lib.draw.WeikeDrawing;
import com.zeno.lib.model.WeikePage;
import com.zeno.lib.model.WeikePageContent;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.SparseArray;


public class WeiKeDrawParser extends DefaultHandler {
	private SparseArray<WeikeDrawing> PlayPathLines;
//	private WeikePages pages = new WeikePages();
	private WeikePage page = new WeikePage();

//	public static WeikePages parserXml(InputStream is) {
//		WeiKeDrawParser weikeParser = new WeiKeDrawParser();
//		try {
//			SAXParserFactory factory = SAXParserFactory.newInstance();
//			SAXParser parser = factory.newSAXParser();
//			XMLReader reader = parser.getXMLReader();
//			reader.setContentHandler(weikeParser);
//			reader.parse(new InputSource(is));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return weikeParser.pages;
//	}
	public static WeikePage parserXml(InputStream is) {
		WeiKeDrawParser weikeParser = new WeiKeDrawParser();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			reader.setContentHandler(weikeParser);
			reader.parse(new InputSource(is));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weikeParser.page;
	}
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		// PlayPathLines = new ArrayList<WeikeDrawing>();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
//		if (TextUtils.equals(localName, "pages")) {
//			pages.getWeikePageList().put(page.getId(), page);
//		}
		if (TextUtils.equals(localName, "page")) {
			page.setLines(PlayPathLines);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
//		if (TextUtils.equals(localName, "pages")) {
//			pages.setTotal(parseString2Int(attributes.getValue("total")));
//			pages.setCurrent_page(parseString2Int(attributes
//					.getValue("current_page")));
//		}
		if (TextUtils.equals(localName, "page")) {
			page = new WeikePage();
			PlayPathLines = new SparseArray<WeikeDrawing>();
			page.setType(parseString2Int(attributes.getValue("type")));
			page.setCreator(attributes.getValue("creator"));
			page.setId(parseString2Int(attributes.getValue("id")));
			page.setStatus(parseString2Int(attributes.getValue("status")));
		}
		if (TextUtils.equals(localName, "content")) {
			page.setWeikePageContent(new WeikePageContent());
			page.getWeikePageContent().setAudio(attributes.getValue("audio"));
			page.getWeikePageContent().setImage(attributes.getValue("image"));
		}
		if (TextUtils.equals(localName, "object")) {
			setupLinesofPage(PlayPathLines, attributes);
		}
	}

	private void setupLinesofPage(SparseArray<WeikeDrawing> list,
			Attributes attributes) {
		try {
			int type = parseString2Int(attributes.getValue("type"));
			int id = parseString2Int(attributes.getValue("id"));
			type = type==9?1:type;
			WeikeDrawing ppl = DrawingFactory.createDrawing(type);
			ppl.setPaint(Brush.getPen().clone());
			String color = attributes.getValue("color");
			ppl.getPaint().setColor(
					Color.parseColor(TextUtils.isEmpty(color) ? "#000000"
							: color));
			ppl.getPaint().setStrokeWidth(
					parseString2Int(attributes.getValue("size")));
			String[] xs = attributes.getValue("xs").split("\\,");
			String[] ys = attributes.getValue("ys").split("\\,");
			String[] ts = attributes.getValue("ts").split("\\,");
			for (int i = 0; i < ts.length; i++) {
				if (i == 0)
					ppl.fingerDown(Float.parseFloat(xs[i]),
							Float.parseFloat(ys[i]), Long.parseLong(ts[i]),
							true);
				else if (i == ts.length - 1)
					ppl.fingerUp(Float.parseFloat(xs[i]),
							Float.parseFloat(ys[i]), Long.parseLong(ts[i]),
							true);
				else
					ppl.fingerMove(Float.parseFloat(xs[i]),
							Float.parseFloat(ys[i]), Long.parseLong(ts[i]),
							true);
			}
			list.put(list.size(), ppl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int parseString2Int(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
}
