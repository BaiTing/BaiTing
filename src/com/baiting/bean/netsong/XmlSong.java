package com.baiting.bean.netsong;

import java.util.List;

import com.baiting.bean.DefaultSongModel;

/**
 * 从netsong.xml中解析Song
 * @author sunshine
 *
 */
public class XmlSong extends DefaultSongModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code ;
	private String url ;
	private String encode ;
	private String infoUrl ;
	private String idsProperty ;
	private String searchParam ;
	private String searchKey ;
	private String parseClass;
	private List<XmlSongItem> xmlSongItems ;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public List<XmlSongItem> getXmlSongItems() {
		return xmlSongItems;
	}
	public void setXmlSongItems(List<XmlSongItem> xmlSongItems) {
		this.xmlSongItems = xmlSongItems;
	}
	public String getInfoUrl() {
		return infoUrl;
	}
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}
	public String getIdsProperty() {
		return idsProperty;
	}
	public void setIdsProperty(String idsProperty) {
		this.idsProperty = idsProperty;
	}
	public String getSearchParam() {
		return searchParam;
	}
	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getParseClass() {
		return parseClass;
	}
	public void setParseClass(String parseClass) {
		this.parseClass = parseClass;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((encode == null) ? 0 : encode.hashCode());
		result = prime * result
				+ ((idsProperty == null) ? 0 : idsProperty.hashCode());
		result = prime * result + ((infoUrl == null) ? 0 : infoUrl.hashCode());
		result = prime * result
				+ ((parseClass == null) ? 0 : parseClass.hashCode());
		result = prime * result
				+ ((searchKey == null) ? 0 : searchKey.hashCode());
		result = prime * result
				+ ((searchParam == null) ? 0 : searchParam.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result
				+ ((xmlSongItems == null) ? 0 : xmlSongItems.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		XmlSong other = (XmlSong) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (encode == null) {
			if (other.encode != null)
				return false;
		} else if (!encode.equals(other.encode))
			return false;
		if (idsProperty == null) {
			if (other.idsProperty != null)
				return false;
		} else if (!idsProperty.equals(other.idsProperty))
			return false;
		if (infoUrl == null) {
			if (other.infoUrl != null)
				return false;
		} else if (!infoUrl.equals(other.infoUrl))
			return false;
		if (parseClass == null) {
			if (other.parseClass != null)
				return false;
		} else if (!parseClass.equals(other.parseClass))
			return false;
		if (searchKey == null) {
			if (other.searchKey != null)
				return false;
		} else if (!searchKey.equals(other.searchKey))
			return false;
		if (searchParam == null) {
			if (other.searchParam != null)
				return false;
		} else if (!searchParam.equals(other.searchParam))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (xmlSongItems == null) {
			if (other.xmlSongItems != null)
				return false;
		} else if (!xmlSongItems.equals(other.xmlSongItems))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "XmlSong [code=" + code + ", url=" + url + ", encode=" + encode
				+ ", infoUrl=" + infoUrl + ", idsProperty=" + idsProperty
				+ ", searchParam=" + searchParam + ", searchKey=" + searchKey
				+ ", parseClass=" + parseClass + ", xmlSongItems="
				+ xmlSongItems + "]";
	}
	
}
