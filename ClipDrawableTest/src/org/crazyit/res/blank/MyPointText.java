package org.crazyit.res.blank;

public class MyPointText {

	//<object v="1.0" type="4" id="1" x="0.111111" y="0.034043"><mk><![CDATA[A]]></mk><dp on="0" /></object>
	
	private String version,type,id,text;
	private float xcoordinate,ycoordinate;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text==null?"":text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public float getXcoordinate() {
		return xcoordinate;
	}
	public void setXcoordinate(float xcoordinate) {
		this.xcoordinate = xcoordinate;
	}
	public float getYcoordinate() {
		return ycoordinate;
	}
	public void setYcoordinate(float ycoordinate) {
		this.ycoordinate = ycoordinate;
	}
	public int getDp() {
		return dp;
	}
	public void setDp(int dp) {
		this.dp = dp;
	}
	private int dp;
	
}
