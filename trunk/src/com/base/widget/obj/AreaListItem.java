package com.base.widget.obj;

public class AreaListItem {
	
	private String name;
	
	private String pcode;
	
	private int pId;
	
	public String getName(){
		return name;
	}
	public String getPcode(){
		return pcode;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setPcode(String pcode){
		this.pcode=pcode;
	}
	public int getpId()
	{
		return pId;
	}
	public void setpId(int pId)
	{
		this.pId = pId;
	}
	@Override
	public String toString()
	{
		return name;
	}
}
