package org.crazyit.res.drawrecord;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.text.TextUtils;
import android.util.Log;

import com.zeno.lib.draw.WeikeDrawing;

public class WeiKeXmlBuilder1 {
	public static String creatXml(List<WeikeDrawing> DrawList, String sourcepath,int width,
			int height) {
		try {

			StringWriter xmlWriter = new StringWriter();
			XmlPullParserFactory pullFactory;
			pullFactory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = pullFactory.newSerializer();
			xmlSerializer.setOutput(xmlWriter);
			// xmlSerializer.startTag("", "pages");
			// xmlSerializer.attribute("", "total",
			// String.valueOf(DrawList == null ? 0 : DrawList.size()));
			// xmlSerializer.attribute("", "current_page", String.valueOf(0));
			if (DrawList != null) {
				// for (int i = 0; i < DrawList.size(); i++) {
				// List<WeikeDrawing> pageDrawlist = DrawList.get(i);
				List<WeikeDrawing> pageDrawlist = DrawList;
				xmlSerializer.startTag("", "page");
				xmlSerializer.attribute("", "type", String.valueOf(4));
				xmlSerializer.attribute("", "id", String.valueOf(0));
				xmlSerializer.attribute("", "creator", String.valueOf(0));
				xmlSerializer.attribute("", "status", String.valueOf(0));
				xmlSerializer.startTag("", "content");
				xmlSerializer.attribute("", "url", new String());
				xmlSerializer.attribute("", "current_document",
						String.valueOf(0));
//				xmlSerializer.attribute("", "image", TextUtils.isEmpty(sourcepath)?"/"+StaticString.BACKGROUND+StaticString.PIC_TYPE
//						:sourcepath+"/"+StaticString.BACKGROUND+StaticString.PIC_TYPE);
//				xmlSerializer.attribute("", "audio",   TextUtils.isEmpty(sourcepath)?"/"+StaticString.SOUND+StaticString.SOUND_TYPE
//						:sourcepath+"/"+StaticString.SOUND+StaticString.SOUND_TYPE);
				xmlSerializer.attribute("", "rectx", String.valueOf(0));
				xmlSerializer.attribute("", "recty", String.valueOf(0));
				xmlSerializer.attribute("", "rectwidth", String.valueOf(width));
				xmlSerializer.attribute("", "rectheight",
						String.valueOf(height));
				xmlSerializer.attribute("", "backgroundx", String.valueOf(0));
				xmlSerializer.attribute("", "backgroundy", String.valueOf(0));
				xmlSerializer.attribute("", "backgroundwidth",
						String.valueOf(width));
				xmlSerializer.attribute("", "backgroundheight",
						String.valueOf(height));
				xmlSerializer.attribute("", "backgroundcolor", "#ffffff");
				xmlSerializer.endTag("", "content");
				xmlSerializer.startTag("", "objects");
				if (pageDrawlist != null) {
					for (int j = 0; j < pageDrawlist.size(); j++) {
						WeikeDrawing drawing = pageDrawlist.get(j);
						if (drawing != null) {
							xmlSerializer.startTag("", "object");
							xmlSerializer
									.attribute("", "id", String.valueOf(0));
							xmlSerializer.attribute("", "type",
									String.valueOf(drawing.getDrawingId()));
							StringBuffer xs = new StringBuffer();
							StringBuffer ys = new StringBuffer();
							StringBuffer ts = new StringBuffer();
							for (int k = 0; k < (drawing.xList == null ? 0
									: drawing.xList.size()); k++) {
								xs.append(drawing.xList.get(k));
								ys.append(drawing.yList.get(k));
								ts.append(drawing.tist.get(k));
								if (k == drawing.xList.size() - 1)
									continue;
								xs.append(",");
								ys.append(",");
								ts.append(",");
							}
							xmlSerializer.attribute("", "xs", xs.toString());
							xmlSerializer.attribute("", "ys", ys.toString());
							xmlSerializer.attribute("", "ts", ts.toString());
							xmlSerializer.attribute("", "dash",
									String.valueOf(0));
							xmlSerializer.attribute(
									"",
									"size",
									drawing.getPaint() == null ? String
											.valueOf(1) : String
											.valueOf((int) drawing.getPaint()
													.getStrokeWidth()));
							xmlSerializer.attribute(
									"",
									"color",
									drawing.getPaint() == null ? "#000000"
											: String.format("#%06X",
													0xFFFFFF & drawing
															.getPaint()
															.getColor()));
							xmlSerializer.endTag("", "object");
						}
					}
				}
				xmlSerializer.endTag("", "objects");
				xmlSerializer.endTag("", "page");
			}
			// }
			// xmlSerializer.endTag("", "pages");
			xmlSerializer.endDocument();
			Log.v("生成文件", String.format(
					"<?xml version=\"1.0\" encoding=\"utf-8\" ?>%s",
					xmlWriter.toString()));
			return String.format(
					"<?xml version=\"1.0\" encoding=\"utf-8\" ?>%s",
					xmlWriter.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String creatAllXml(ArrayList<String> xmlstrings, int currunt) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		stringBuffer.append(String.format(
				"<pages current_page=\"%d\" total=\"%s\">", currunt,
				xmlstrings.size()));
		for (int i = 0; i < xmlstrings.size(); i++) {
			stringBuffer.append(xmlstrings.get(i));
		}
		stringBuffer.append("</pages>");
		return stringBuffer.toString();
	}
	public static String creatJS(ArrayList<String> xmlstrings, int currunt) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		stringBuffer.append(String.format(
				"<pages current_page=\"%d\" total=\"%s\">", currunt,
				xmlstrings.size()));
		for (int i = 0; i < xmlstrings.size(); i++) {
			stringBuffer.append(xmlstrings.get(i));
		}
		stringBuffer.append("</pages>");
		return "var mdata =\'"+stringBuffer.toString()+"\'";
	}
}
