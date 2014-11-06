package org.crazyit.res.blank;

import java.io.StringWriter;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;

public class MyObjectToXml
{
	private Context mContext;
//	private MyObject myObject;
	public MyObjectToXml(Context context)
	{
		mContext = context;
	}

	public static String CreatXmlString(MyObject myObject)
	{
		if(myObject==null)
			return "";
		try
		{
			StringWriter xmlWriter = new StringWriter();
			XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = pullFactory.newSerializer();
			xmlSerializer.setOutput(xmlWriter);
			xmlSerializer.startDocument("utf-8", true);
			xmlSerializer.startTag("", "object");
			xmlSerializer.attribute("", "v", "2.0");
			xmlSerializer.attribute("", "type", "2");
			
			for(int i=0;i<myObject.getPointList().size();i++){
				MyPoint point=myObject.getPointList().get(i);
				MyPointText text=myObject.getTextList().get(i);
				
				xmlSerializer.startTag("", "object");
				xmlSerializer.attribute("", "v", "1.0");
				xmlSerializer.attribute("", "type", "2");
				xmlSerializer.attribute("", "id", ""+point.getId());
				xmlSerializer.attribute("", "x", ""+point.getXcoordinate());
				xmlSerializer.attribute("", "y", ""+point.getYcoordinate());
				xmlSerializer.endTag("", "object");
				
				xmlSerializer.startTag("", "object");
				xmlSerializer.attribute("", "v", "1.0");
				xmlSerializer.attribute("", "type", "4");
				xmlSerializer.attribute("", "id", ""+text.getId());
				xmlSerializer.attribute("", "x", ""+text.getXcoordinate());
				xmlSerializer.attribute("", "y", ""+text.getYcoordinate());
				
				xmlSerializer.startTag("", "mk");
				xmlSerializer.text(text.getText());
				xmlSerializer.endTag("", "mk");
				
				xmlSerializer.startTag("", "dp");
				xmlSerializer.attribute("", "on", ""+text.getDp());
				xmlSerializer.endTag("", "dp");
				
				
				xmlSerializer.endTag("", "object");
				
			}
			
			for(int i=0;i<myObject.getLineList().size();i++){
				MyLine line=myObject.getLineList().get(i);
				xmlSerializer.startTag("", "object");
				xmlSerializer.attribute("", "v", "1.0");
				xmlSerializer.attribute("", "type", "3");
				xmlSerializer.attribute("", "id", ""+line.getId());
				xmlSerializer.attribute("", "sy", ""+line.getSy());
				xmlSerializer.attribute("", "cl", ""+line.getColor());
				xmlSerializer.attribute("", "sz", ""+line.getLineSize());
				
				xmlSerializer.startTag("", "dp");
				xmlSerializer.attribute("", "on", ""+line.getDp_from());
				xmlSerializer.endTag("", "dp");
				
				xmlSerializer.startTag("", "dp");
				xmlSerializer.attribute("", "on", ""+line.getDp_to());
				xmlSerializer.endTag("", "dp");
				
				
				
				xmlSerializer.endTag("", "object");
				
			}
		
			
			xmlSerializer.endTag("", "object");
			xmlSerializer.endDocument();
			return xmlWriter.toString();
		}
		catch (Exception e)
		{
			return "";
		}

	}
}
