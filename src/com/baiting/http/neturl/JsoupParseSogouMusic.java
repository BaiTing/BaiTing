package com.baiting.http.neturl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.baiting.bean.netsong.Song;
import com.baiting.bean.netsong.XmlSong;
import com.baiting.bean.netsong.XmlSongItem;
import com.baiting.cache.Cache;

public class JsoupParseSogouMusic extends JsoupParseNetMusic {
	
	private final Logger log = Logger.getLogger(JsoupParseSogouMusic.class.getName()) ;
	
	public JsoupParseSogouMusic() {
		super();
		log.info("JsoupParseSogouMusic init ...");
	}

	@Override
	public List<Song> getSong(XmlSong xs, XmlSongItem xsi) throws Exception {
		// TODO Auto-generated method stub
		List<Song> list = new ArrayList<Song>() ;
		String url = xs.getUrl() + xsi.getUrl();
		String encode = xs.getEncode();
		String html = getParseHtml(url, encode);
		Document doc = Jsoup.parse(html);
		//查找<a>标签
		Elements clicks = doc.select("a");
		for(Element et : clicks){
			String tmp = et.attr("onclick") ;
//			System.out.println(html);
			String temp = tmp.substring(tmp.indexOf("('")+2, tmp.lastIndexOf("');")) ;
			temp = temp.replaceAll("\"", "'");
			temp = temp.replaceAll("#", "\"");
			temp = temp.substring(1, temp.length()-2) ;
			String strs[] = temp.split("],") ;
			for (String string : strs) {
				string = string.substring(1, string.length()) ;
//				System.out.println(string);
				string = string.replaceAll("\\[", "(").replaceAll("]", ")");
				string ="["+string +"]" ;
//				System.out.println(string);
				try {
					JSONArray ja = JSONArray.parseArray(string);
//					System.out.println(ja.toJSONString());
					Song song = new Song();
					song.setSongLink(ja.getString(2));
					song.setSongName(ja.getString(3));
					song.setArtistName(ja.getString(5));
					song.setAlbumName(ja.getString(7));
					song.setTime(ja.getIntValue(8));
					//截取出歌曲格式
					String format = song.getSongLink().substring(0,song.getSongLink().indexOf("?")) ;
					song.setFormat(format.substring(format.lastIndexOf(".")+1));
					list.add(song);
				} catch (Exception e) {
					e.printStackTrace();
					continue ;
				}
			}
			/*
			JSONArray arr = JSONArray.parseArray(temp);
			for (int i = 0; i < arr.size(); i++) {
				JSONArray ja = arr.getJSONArray(i) ;
				System.out.println(ja.toJSONString());
				Song song = new Song();
				song.setSongLink(ja.getString(2));
				song.setSongName(ja.getString(3));
				song.setArtistName(ja.getString(5));
				song.setAlbumName(ja.getString(7));
				song.setTime(ja.getIntValue(8));
				list.add(song);
			}
			*/
		}
		
		return list;
	}
	
	public String getParseHtml(String url, String encode) throws Exception {
		if(Cache.hasCache(url+"_@_"+encode)){
			return (String) Cache.get(url+"_@_"+encode) ;
		}
		
		String htmlContent = visitURL(url, encode);
		
		if ((htmlContent == null) || (htmlContent.isEmpty())) {
		      return "ERROR";
		}
		//过滤条件
		String reg = "<div class=\"cmain\">(.*?)</div>";
		htmlContent = getFilteredContent(htmlContent,reg,0);
		htmlContent = htmlContent.replaceAll("\b\r\t\n", "") ;
		
		//TODO:去除空格
		htmlContent = htmlContent.trim() ;
		
		if(!Cache.hasCache(url+"_@_"+encode))
			Cache.put(url+"_@_"+encode, htmlContent) ;
		
		return htmlContent ;
	}
	
	public static void main(String[] args) {
//			JsoupParseSogouMusic jpsm = new JsoupParseSogouMusic() ;
			/*
			Dom4jParseNetSongXml netSong = Dom4jParseNetSongXml.getInstance() ;
			List<XmlSong> songs = netSong.getAllSongsWithItems() ;
			for (XmlSong xs : songs) {
				String code = xs.getCode();
				if("SogouMusic".equals(code))
				for(XmlSongItem xsi : xs.getXmlSongItems()){
					List<Song> songList = jpsm.getSong(xs, xsi);
					System.out.println(songList.get(0));
					break;
				}
			}
			*/
//			String url = "http://music.sogou.com/static_new/topsong_liuxing.html" ;
//			String encode = "GBK";
//		String html = jpsm.getParseHtml(url, encode) ;

				/*
				System.out.println(temp);
				JSONArray arr = JSONArray.parseArray(temp);
				for (int i = 0; i < arr.size(); i++) {
					JSONArray ja = arr.getJSONArray(i) ;
					System.out.println(ja.toJSONString());
				}*/
//			}
//		}
		String url= "http://stream15.qqmusic.qq.com/35036372.mp3?key=7993F3FC7586E0F3C8681134E4EAC6EB3B3849EAD61C13FF&qqmusic_fromtag=0";
		url = url.substring(0,url.indexOf("?")) ;
		System.out.println(url.substring(url.lastIndexOf(".")+1));
	}

	@Override
	public List<Song> getSearchSong(XmlSong xs, String key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
