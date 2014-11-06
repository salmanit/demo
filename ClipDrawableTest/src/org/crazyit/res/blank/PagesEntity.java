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

public class PagesEntity extends DefaultHandler{

	private String state,type,creater;
	private String url,currentIndex;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(String currentIndex) {
		this.currentIndex = currentIndex;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeigh() {
		return heigh;
	}
	public void setHeigh(String heigh) {
		this.heigh = heigh;
	}

	private String x,y,width,heigh;
	private ArrayList<CellEntity>  celllist;
	
	public ArrayList<CellEntity> getCelllist() {
		return celllist;
	}
	public void setCelllist(ArrayList<CellEntity> celllist) {
		this.celllist = celllist;
	}

	private ArrayList<PagesEntity>  pagelist=new ArrayList<PagesEntity>();
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
			Log.i("pagesentity½âÎöxml³ö´í", e.toString());
			return false;
		}
	}
	
	
	public ArrayList<PagesEntity>  parseXml(String xml){
		
		if(parseMainXml(xml)){
			return pagelist;
		}
		return null;
	}
	PagesEntity  pagesEntity;
	boolean  pagetag=false;
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(localName.equals("page")){
			
			pagetag=true;
			pagesEntity=new PagesEntity();
			pagesEntity.setState(attributes.getValue("status"));
			pagesEntity.setCreater(attributes.getValue("creator"));
			pagesEntity.setType(attributes.getValue("type"));
		}
		if(pagetag){
			if(localName.equals("content")){
				pagesEntity.setUrl(attributes.getValue("url"));
				pagesEntity.setCurrentIndex(attributes.getValue("current_document"));
			}
			if(localName.equals("objects")){
				
				System.out.println("==="+attributes.getValue("objects"));
				pagesEntity.setCelllist(new CellEntity().parseXml(attributes.getValue("objects")));
			}
			if(localName.equals("object")){
				System.out.println("object==="+attributes.getValue("object"));
			}
			
		}
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(localName.equals("page")){
			pagetag=false;
			pagelist.add(pagesEntity);
		}
	}
	
}
