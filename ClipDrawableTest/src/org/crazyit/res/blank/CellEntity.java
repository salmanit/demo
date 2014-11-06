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

import android.util.Log;


public class CellEntity extends DefaultHandler{

	String xString,yString,tString,colorString;
	public String getxString() {
		return xString;
	}

	public void setxString(String xString) {
		this.xString = xString;
	}

	public String getyString() {
		return yString;
	}

	public void setyString(String yString) {
		this.yString = yString;
	}

	public String gettString() {
		return tString;
	}

	public void settString(String tString) {
		this.tString = tString;
	}

	public String getColorString() {
		return colorString;
	}

	public void setColorString(String colorString) {
		this.colorString = colorString;
	}

	public int getPaintSize() {
		return paintSize;
	}

	public void setPaintSize(int paintSize) {
		this.paintSize = paintSize;
	}

	public float[] getxCode() {
		return xCode;
	}

	public void setxCode(float[] xCode) {
		this.xCode = xCode;
	}

	public float[] getyCode() {
		return yCode;
	}

	public void setyCode(float[] yCode) {
		this.yCode = yCode;
	}

	public int[] getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(int[] timeSpan) {
		this.timeSpan = timeSpan;
	}



	int paintSize;
	float[]  xCode,yCode;
	int[]  timeSpan;
	
	ArrayList<CellEntity> list=new ArrayList<CellEntity>();
	
	public boolean parseMainXml(String xmlStr)
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
			Log.i("½âÎöxml³ö´í", e.toString());
			return false;
		}
	}

	public ArrayList<CellEntity> parseXml(String xml){
		if(parseMainXml(xml)){
		return list;	
		}
		return null;
	}
	

	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(localName.equals("object")){
			CellEntity  cellEntity=new CellEntity();
			cellEntity.setColorString(attributes.getValue("color"));
			cellEntity.setPaintSize(Integer.parseInt(attributes.getValue("size")));
			cellEntity.setxString(attributes.getValue("xs"));
			cellEntity.setyString(attributes.getValue("ys"));
			String[] tempx=cellEntity.getxString().split(",");
			float[] tempxf=new float[tempx.length];
			for(int i=0;i<tempx.length;i++){
				tempxf[i]=Float.parseFloat(tempx[i]);
			}
			cellEntity.setxCode(tempxf);
			
			String[] tempy=cellEntity.getyString().split(",");
			float[] tempyf=new float[tempy.length];
			for(int i=0;i<tempy.length;i++){
				tempyf[i]=Float.parseFloat(tempy[i]);
			}
			cellEntity.setyCode(tempyf);
			
			list.add(cellEntity);
		}
		
		
	}
	
}
