package org.crazyit.res.drawrecord;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.zeno.lib.draw.DrawingFactory;
import com.zeno.lib.draw.WeikeDrawing;

public class DrawPage {
	private int type = 1, creator;
	private long id;
	private int index;
	private String content;
	private int currentContent = 0;
	private boolean isKey = false;
	private SparseArray<ArrayList<WeikeDrawing>> PaintLines = new SparseArray<ArrayList<WeikeDrawing>>();
	private int width, height;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public DrawPage(int type, int creator, long id, int index, String content,
			int currentContent) {
		super();
		this.type = type;
		this.creator = creator;
		this.id = id;
		this.index = index;
		this.content = content;
		this.currentContent = currentContent;
	}

	public DrawPage(Attributes attributes) {
		super();
		init(attributes);
	}

	private void init(Attributes attributes) {
		PaintLines.clear();
		setType(Integer
				.parseInt(TextUtils.isEmpty(attributes.getValue("type")) ? "0"
						: attributes.getValue("type")));
		setId(Long.parseLong(TextUtils.isEmpty(attributes.getValue("id")) ? "0"
				: attributes.getValue("id")));
		setCreator(Integer.parseInt(TextUtils.isEmpty(attributes
				.getValue("creator")) ? "0" : attributes.getValue("creator")));
		setKey("1".equals(attributes.getValue("key")));
	}

	public static DrawPage dp;

	public static DrawPage setupByPageXml(String xmlStr) {
		try {
			dp = new DrawPage();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			reader.setContentHandler(new DefaultHandler() {
				@Override
				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					if (localName.equals("pages")) {
						dp.setWidth(Integer.parseInt(TextUtils
								.isEmpty(attributes.getValue("width")) ? String
								.valueOf(0) : attributes.getValue("width")));
						dp.setHeight(Integer.parseInt(TextUtils
								.isEmpty(attributes.getValue("height")) ? String
								.valueOf(0) : attributes.getValue("height")));
					}
					if (localName.equals("page")) {
						dp.init(attributes);
					}
					if (localName.equals("content")) {
						dp.setContent(attributes.getValue("url"));
						dp.setCurrentContent(Integer.parseInt(TextUtils
								.isEmpty(attributes
										.getValue("current_document")) ? "0"
								: attributes.getValue("current_document")));
						if (dp.PaintLines.get(dp.getCurrentContent()) == null)
							dp.PaintLines.put(dp.getCurrentContent(),
									new ArrayList<WeikeDrawing>());
					}
					if (localName.equals("object")) {
						if (dp.PaintLines.get(dp.getCurrentContent()) == null)
							dp.PaintLines.put(dp.getCurrentContent(),
									new ArrayList<WeikeDrawing>());
						int type = 9;
						try {
							type = Integer.parseInt(attributes.getValue("type"));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						WeikeDrawing line = DrawingFactory.createDrawing(type);
						line.init(attributes);
						dp.PaintLines.get(dp.getCurrentContent()).add(line);
					}
				}
			});
			reader.parse(new InputSource(new ByteArrayInputStream(xmlStr
					.getBytes())));
			return dp;
		} catch (Exception e) {
			Log.v("xmlException", xmlStr);
			e.printStackTrace();
			return null;
		}
	}

	public SparseArray<ArrayList<WeikeDrawing>> getPaintLines() {
		return PaintLines;
	}

	public void setPaintLines(SparseArray<ArrayList<WeikeDrawing>> paintLines) {
		PaintLines = paintLines;
	}

	public boolean isKey() {
		return isKey;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	public DrawPage() {
		super();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCurrentContent() {
		return currentContent;
	}

	public void setCurrentContent(int currentContent) {
		this.currentContent = currentContent;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "DrawPage [type=" + type + ", creator=" + creator + ", id=" + id
				+ ", index=" + index + ", content=" + content
				+ ", currentContent=" + currentContent + ", isKey=" + isKey
				+ ", PaintLines=" + PaintLines + "]";
	}

}
