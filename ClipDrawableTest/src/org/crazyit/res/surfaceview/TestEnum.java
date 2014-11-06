package org.crazyit.res.surfaceview;

public enum TestEnum {

	
	REDCOLOR(1),
	GREENCOLR(2);
	
	private int index;
	private TestEnum(int  index){
		this.index=index;
	}
	
	public int getIndex(){
		return index;
	}
}
