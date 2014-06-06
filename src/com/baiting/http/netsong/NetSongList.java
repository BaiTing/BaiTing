package com.baiting.http.netsong;

import java.util.HashMap;
import java.util.Map;

import com.baiting.bean.netsong.XmlSong;
import com.baiting.bean.netsong.XmlSongItem;

public abstract class NetSongList extends NetSongHttp{

	protected Map<XmlSong, XmlSongItem> map; //保存当前点击的是哪个标签对应的配置
	protected String xmlSongCode; //保存当前点击的是哪个标签的编码
	protected String xmlSomgItemName; //保存当前点击的是哪个标签的名称
	
	public NetSongList() throws Exception {
		super();
		try {
			for (XmlSong xmlSong : xss) {
				// 默认把第一个放进去
				if(null == map){
					map = new HashMap<XmlSong, XmlSongItem>() ;
					map.put(xmlSong, xmlSong.getXmlSongItems().get(0));
				}
				if(null == xmlSongCode){
					xmlSongCode = xmlSong.getCode() ;
				}
				if(null == xmlSomgItemName){
					xmlSomgItemName = xmlSong.getXmlSongItems().get(0).getName() ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存当前点击的是哪个标签对应的配置
	 * @param xs
	 * @param xsi
	 */
	public abstract void putXmlSong(XmlSong xs, XmlSongItem xsi) ;
	
	/**
	 * 保存当前点击的是哪个标签的编码
	 * @param code
	 */
	public abstract void setXmlSongCode(String code);
	
	/**
	 * 保存当前点击的是哪个标签的名称
	 * @param name
	 */
	public abstract void setXmlSongItemName(String name) ;
}
