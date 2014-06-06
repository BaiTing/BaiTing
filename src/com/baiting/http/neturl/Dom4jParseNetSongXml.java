package com.baiting.http.neturl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.baiting.bean.netsong.XmlSong;
import com.baiting.bean.netsong.XmlSongItem;
import com.baiting.cache.Cache;
import com.baiting.util.Utils;

public class Dom4jParseNetSongXml {

	private static final Logger log = Logger.getLogger(Dom4jParseNetSongXml.class.getName()) ;
	
	//外部配置文件路径
	private static final String confFile = System.getProperty("user.dir") + File.separator +"conf"+ File.separator +"netsong.xml" ;
	private static final String proConfFile = "conf/netsong.xml" ; //项目内配置文件路径
	private static Dom4jParseNetSongXml instance = null ;
	
	private Dom4jParseNetSongXml(){
		log.info("init Dom4jParseNetSongXml ...") ;
	}
	
	public synchronized static Dom4jParseNetSongXml getInstance() {
		if(null == instance)
			instance = new Dom4jParseNetSongXml() ;
		return instance ;
	}
	
	@SuppressWarnings("resource")
	private static InputStream getStream() throws Exception {
		InputStream in = null ;
		try { //首先直接加载外部配置文件，如果找不到则去找项目配置文件
			log.log(Level.INFO, "使用外部配置文件路径：" + confFile) ;
			in = new BufferedInputStream(new FileInputStream(confFile)) ;
		} catch (Exception e) {
			try {
				if(null == in){
					log.log(Level.INFO, "外部配置文件路径："+ confFile +"没有找到配置！");
					//找项目配置文件
					log.log(Level.INFO, "使用项目内配置文件路径：" + proConfFile);
					in = Thread.currentThread().getContextClassLoader().getResourceAsStream(proConfFile) ;
					if(null == in){
						log.log(Level.SEVERE, "在路径：" + proConfFile + " 和 " + confFile +"都没有找到配置");
						throw new RuntimeException("在路径：" + proConfFile + " 和 " + confFile +"都没有找到配置") ;
					}
				}
			} catch (Exception e1) {
				log.log(Level.SEVERE, "在路径：" + proConfFile + " 和 " + confFile +"下都没有找到配置");
				e.printStackTrace();
				throw e1 ;
			}
		}
		return in ;
	}
	
	/**
	 * 解析所有的Song节点包含SongItem子节点
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<XmlSong> getAllSongsWithItems() throws Exception {
		if(Cache.hasCache("XmlSongs_SongItem")){
			return (List<XmlSong>)Cache.get("XmlSongs_SongItem") ;
		}
		List<XmlSong> list = new ArrayList<XmlSong>() ;
		InputStream in = getStream() ;
		Document document = new SAXReader().read(in) ;
		List<Element> nodeList = document.selectNodes("//Song") ;
		for (Element element : nodeList) {
			List<Element> nodes = element.selectNodes("SongItem") ;
			List<XmlSongItem> listItem = new ArrayList<XmlSongItem>() ;
			for (Element el : nodes) {
				List<Attribute> attrs = el.attributes() ;
				XmlSongItem xsi = new XmlSongItem() ;
				for (Attribute att : attrs) {
					Utils.setPropertyValue(xsi, att.getName(), att.getValue());
				}
				 listItem.add(xsi) ;
			}
			List<Attribute> attrs = element.attributes() ;
			XmlSong xs = new XmlSong() ;
			for (Attribute attr : attrs) {
				Utils.setPropertyValue(xs, attr.getName(), attr.getValue());
			}
			xs.setXmlSongItems(listItem) ;
			list.add(xs) ;
		}
		if(!Cache.hasCache("XmlSongs_SongItem"))
			Cache.put("XmlSongs_SongItem", list) ;
		return list ;
	}
	
	/**
	 * 解析所有的Song节点不包含SongItem子节点
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<XmlSong> getAllSongsWithNotItems() throws Exception {
		if(Cache.hasCache("XmlSongs")){
			return (List<XmlSong>)Cache.get("XmlSongs") ;
		}
		List<XmlSong> list = new ArrayList<XmlSong>() ;
		InputStream in = getStream() ;
		Document document = new SAXReader().read(in) ;
		List<Element> nodeList = document.selectNodes("//Song") ;
		for (Element element : nodeList) {
			List<Attribute> attrs = element.attributes() ;
			XmlSong xs = new XmlSong() ;
			for (Attribute attr : attrs) {
				Utils.setPropertyValue(xs, attr.getName(), attr.getValue());
			}
			list.add(xs) ;
		}
		if(!Cache.hasCache("XmlSongs"))
			Cache.put("XmlSongs", list) ;
		return list ;
	}
	
	/**
	 * 根据父级code取子资源
	 * @param xml
	 * @param parentCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<XmlSongItem> getSongItemByCode(String parentCode) throws Exception{
		if(Cache.hasCache(parentCode+"_XmlSongItem")){
			return (List<XmlSongItem>)Cache.get(parentCode+"_XmlSongItem") ;
		}
		List<XmlSongItem> list = new ArrayList<XmlSongItem>() ;
		InputStream in = getStream() ;
		Document document = new SAXReader().read(in) ;
		Node node = document.selectSingleNode("//Song[@code='"+parentCode+"']") ;
		List<Element> nodeList = node.selectNodes("SongItem") ;
		 for (Element element : nodeList) {
			 List<Attribute> attrs = element.attributes() ;
			 XmlSongItem xsi = new XmlSongItem() ;
			 for (Attribute attr : attrs) {
				 Utils.setPropertyValue(xsi, attr.getName(), attr.getValue());
			}
			list.add(xsi) ;
		}
		 if(!Cache.hasCache(parentCode+"_XmlSongItem"))
				Cache.put(parentCode+"_XmlSongItem", list) ;
		return list ;
	}
	
}
