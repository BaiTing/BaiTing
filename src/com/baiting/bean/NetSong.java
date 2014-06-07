package com.baiting.bean;

import com.baiting.bean.netsong.XmlSong;
import com.baiting.util.StringUtil;

public class NetSong implements IBaseBean {

	private static final long serialVersionUID = -2756269269796522572L;

	private String sid;
	
	/**
	 * 歌曲名称
	 */
	private String songName;
	
	/**
	 * 歌手
	 */
	private String singer;
	
	/**
	 * 专辑
	 */
	private String album;
	
	private String url;
	
	private String localPath;
	
	private int type;
	
	private XmlSong xmlSong;

	public XmlSong getXmlSong() {
		return xmlSong;
	}

	public void setXmlSong(XmlSong xmlSong) {
		this.xmlSong = xmlSong;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLocalPath() {
		if(StringUtil.isEmpty(localPath)) {
			localPath = " ";
		}
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "NetSong [sid=" + sid + ", songName=" + songName + ", singer="
				+ singer + ", album=" + album + ", url=" + url + ", localPath="
				+ localPath + ", type=" + type + ", xmlSong=" + xmlSong + "]";
	}
	
}
