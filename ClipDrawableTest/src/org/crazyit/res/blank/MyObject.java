package org.crazyit.res.blank;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.R.attr;
import android.util.Log;

public class MyObject extends DefaultHandler{

	private ArrayList<MyPoint>  pointList;
	private ArrayList<MyPointText>  textList;
	private ArrayList<MyLine>  lineList;
	
	public ArrayList<MyPoint> getPointList() {
		return pointList;
	}

	public void setPointList(ArrayList<MyPoint> pointList) {
		this.pointList = pointList;
	}

	public ArrayList<MyPointText> getTextList() {
		return textList;
	}

	public void setTextList(ArrayList<MyPointText> textList) {
		this.textList = textList;
	}

	public ArrayList<MyLine> getLineList() {
		return lineList;
	}

	public void setLineList(ArrayList<MyLine> lineList) {
		this.lineList = lineList;
	}

	public boolean success(String xmlStr)
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			reader.setContentHandler(this);
			reader.parse(new InputSource(
					((InputStream) new ByteArrayInputStream(xmlStr.getBytes()))));
			return true;
		}
		catch (Exception e)
		{
			Log.i("MyObject解析xml出错", e.toString());
			return false;
		}
	}
	
	public MyObject  parseXml(String xml){
		
		if(success(xml)){
			return this;
		}
		return null;
	}
	@Override
	public void startDocument() throws SAXException {
		pointList=new ArrayList<MyPoint>();
		textList=new ArrayList<MyPointText>();
		lineList=new ArrayList<MyLine>();
	}
	
	private MyPoint point;
	private MyPointText pointText;
	private MyLine line;
	boolean  tagPoint,tagText,tagLine;
	boolean  start=true;//线的起点判定
	boolean mk=false;
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(localName.equals("object")&&attributes.getValue("v").equals("1.0")&&attributes.getValue("type").equals("2")){
			tagPoint=true;
			 point=new MyPoint();
			 point.setVesion(attributes.getValue("v"));
			 point.setType(attributes.getValue("type"));
			 point.setId(attributes.getValue("id"));
			 point.setXcoordinate(Float.parseFloat(attributes.getValue("x")));
			 point.setYcoordinate(Float.parseFloat(attributes.getValue("y")));
			 pointList.add(point);
		}else if(localName.equals("object")&&attributes.getValue("v").equals("1.0")&&attributes.getValue("type").equals("4")){
			tagText=true;
			pointText=new MyPointText();
			pointText.setVersion(attributes.getValue("v"));
			pointText.setType(attributes.getValue("type"));
			pointText.setId(attributes.getValue("id"));
			pointText.setXcoordinate(Float.parseFloat(attributes.getValue("x")));
			pointText.setYcoordinate(Float.parseFloat(attributes.getValue("y")));
			
		}else if(tagText&&localName.equals("dp")){
			pointText.setDp(Integer.parseInt(attributes.getValue("on")));
			textList.add(pointText);
			tagText=false;
		}else if(localName.equals("object")&&attributes.getValue("v").equals("1.0")&&attributes.getValue("type").equals("3")){
			tagLine=true;
			line=new MyLine();
			line.setVesion(attributes.getValue("v"));
			line.setType(attributes.getValue("type"));
			line.setId(attributes.getValue("id"));
			line.setSy(attributes.getValue("sy"));
			line.setColor(attributes.getValue("cl"));
			line.setLineSize(Integer.parseInt(attributes.getValue("sz")));
		}else if(tagLine&&localName.equals("dp")&&start){
			line.setDp_from(Integer.parseInt(attributes.getValue("on")));
			start=false;
		}else if(tagLine&&localName.equals("dp")&&!start){
			line.setDp_to(Integer.parseInt(attributes.getValue("on")));
			start=true;
			lineList.add(line);
			tagLine=false;
		}else if(localName.equals("mk")){
			mk=true;
		}
		
		
	}
@Override
public void characters(char[] ch, int start, int length)
		throws SAXException {
	if(mk){
		mk=false;
		pointText.setText(new String(ch,start,length));
	}
}	

}
