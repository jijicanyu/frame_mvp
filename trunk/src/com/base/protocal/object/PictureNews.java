package com.base.protocal.object;
public class PictureNews
{
	private String picnewId;
	
	private String picnewTitle;
	
	private String picnewLink;
	
	private String disOrder;
	
	private String picnewCont;

    private int phototype;//0表示照片,1表示广告

    public  int getPhototype()
    {
        return phototype;
    }
    public void setPhototype(int t)
    {
        phototype=t;
    }

	public String getPicnewId()
	{
		return picnewId;
	}

	public void setPicnewId(String picnewId)
	{
		this.picnewId = picnewId;
	}

	public String getPicnewTitle()
	{
		return picnewTitle;
	}

	public void setPicnewTitle(String picnewTitle)
	{
		this.picnewTitle = picnewTitle;
	}

	public String getPicnewLink()
	{
		return picnewLink;
	}

	public void setPicnewLink(String picnewLink)
	{
		this.picnewLink = picnewLink;
	}

	public String getDisOrder()
	{
		return disOrder;
	}

	public void setDisOrder(String disOrder)
	{
		this.disOrder = disOrder;
	}

	public String getPicnewCont()
	{
		return picnewCont;
	}

	public void setPicnewCont(String picnewCont)
	{
		this.picnewCont = picnewCont;
	}
}
