package com.baiting.http.neturl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baiting.bean.netsong.Song;
import com.baiting.bean.netsong.SongItem;
import com.baiting.bean.netsong.XmlSong;
import com.baiting.bean.netsong.XmlSongItem;
import com.baiting.cache.Cache;

public class JsoupParseSo360Music extends JsoupParseNetMusic {

	private final Logger log = Logger.getLogger(JsoupParseSo360Music.class.getName()) ;
	
	public JsoupParseSo360Music() {
		super() ;
		log.info("JsoupParseSo360Music init ...");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Song> getSong(XmlSong xs, XmlSongItem xsi) throws Exception {
		if(Cache.hasCache(xsi.getCode())){
			return (List<Song>)Cache.get(xsi.getCode()) ;
		}
		List<Song> list = new ArrayList<Song>() ;
		
		StringBuffer sb = new StringBuffer() ;
		List<SongItem> songItems = getSongItem(xs.getUrl() + xsi.getUrl(), xs.getEncode()) ;
		for (SongItem songItem : songItems) {
			sb.append(songItem.getSid()).append("_") ;
		}
		
		String songIds = sb.toString().substring(0, sb.lastIndexOf("_")) ;
		
		String info = getSongInfo(xs, songIds) ;
//		System.out.println(info);
//		log.info(info) ;
		JSONArray jsonArray = JSONArray.parseArray(info) ;
		for (int i=0;  i < jsonArray.size(); i++) {
			try {
				JSONObject json = jsonArray.getJSONObject(i) ;
				Song song = JSONObject.toJavaObject(json, Song.class);
				if(json.containsKey("duration")){ //歌曲时间
				String duration = json.getString("duration");
					if(null != duration && !"".equals(duration)){
						String dur[] = duration.split(":") ;
						int time = Integer.parseInt(dur[0])*60 + Integer.parseInt(dur[1]) ;
						song.setTime(time);
					}
				}
				song.setFormat("mp3");
				list.add(song) ;
			} catch (Exception e) {
				continue ;
			}
		}
		if(!Cache.hasCache(xsi.getCode()))
			Cache.put(xsi.getCode(), list) ;
		return list ;
	}
	
	/**
	 * 根据songId取到地址
	 * @param songId
	 * @return
	 * @throws Exception
	 */
	public String getSongUrl(XmlSong xs, String songId) throws Exception {
//		String url = "http://s.music.so.com/player/song?projectName=360music&id=" + songId ;
		String url = xs.getInfoUrl().replace("list", "")+ "?" + xs.getIdsProperty().substring(0, xs.getIdsProperty().lastIndexOf("s")) + "=" + songId ;
//		System.out.println("======>"+url);
		String jsonInfo = visitURL(url, null) ;
		JSONObject jsonObject = JSONObject.parseObject(jsonInfo) ;
		
		return jsonObject.getString("playlinkUrl") ;
	}
	
	/**
	 * 返回SongInfo JSON
	 * @param songIds
	 * @return
	 * @throws Exception
	 */
	public String getSongInfo(XmlSong xs, String songIds) throws Exception {
		if(Cache.hasCache(songIds)){
			return (String) Cache.get(songIds) ;
		}
		
		String url = xs.getInfoUrl() + "?" + xs.getIdsProperty() + "="+songIds ;
		
		String encode = xs.getEncode() ;
		
		String rtn = visitURL(url, encode) ;
		
		if(!Cache.hasCache(songIds)){
			Cache.put(songIds, rtn) ;
		}

		return rtn ;
	}
	
	@SuppressWarnings("unchecked")
	public List<SongItem> getSongItem(String url, String encode) throws Exception{
		if(Cache.hasCache(url)){
			return (List<SongItem>)Cache.get(url) ;
		}
		List<SongItem> items = new ArrayList<SongItem>() ;
		Document doc = Jsoup.parse(getParseHtml(url, encode));
		//查找包含<li class="...">内容   
		Elements clicks = doc.select("li");
		for(Element et : clicks){
			Elements eles = et.children() ;
			SongItem si = new SongItem() ;
			for (Element ele : eles) {
				String cls = ele.attr("class") ;
				Element el  = null ;
				if("chkbox".equalsIgnoreCase(cls)){
					el  = ele.child(0) ;
					si.setSid(el.attr("js-data-id"));
				}else if("title".equalsIgnoreCase(cls)){
					el  = ele.child(0) ;
					si.setSname(el.attr("title"));
				}else if("artist".equalsIgnoreCase(cls)){
					el  = ele.child(0) ;
					si.setAuthor(el.attr("title"));
				}else {
					continue;
				}
			}
			items.add(si) ;
		}
		if(!Cache.hasCache(url))
			Cache.put(url, items) ;
		
		return items ;
	}
	
	/**
	 * 抓取360音乐html
	 * @throws Exception 
	 */
	public String getParseHtml(String url, String encode) throws Exception {
		
		if(Cache.hasCache(url+"_@_"+encode)){
			return (String) Cache.get(url+"_@_"+encode) ;
		}
		
		String htmlContent = visitURL(url, encode);
		
		if ((htmlContent == null) || (htmlContent.isEmpty())) {
		      return "ERROR";
		}
		
		String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

	    String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

	    htmlContent = htmlContent.replaceAll(regEx_script, "");

	    htmlContent = htmlContent.replaceAll(regEx_style, "");

	    htmlContent = htmlContent.replaceAll("\\s+", " ");
		
		String reg = "<ol class=\"songlist\">(.*)</ol>";
		htmlContent = getFilteredContent(htmlContent,reg,0);
		
		//TODO:去除空格
		htmlContent = htmlContent.trim() ;
		
		if(!Cache.hasCache(url+"_@_"+encode))
			Cache.put(url+"_@_"+encode, htmlContent) ;
		
		return htmlContent ;
	}
	
	public static void main(String[] args) {
		JsoupParseSo360Music jpsm = new JsoupParseSo360Music() ;
//		String url = "http://music.so.com/top/love.html" ;
//		String encode = "utf-8" ;
		try {
			/*
			Document doc = Jsoup.parse(jpsm.getParseHtml(url, encode));
			//查找包含<li class="...">内容   
			Elements clicks = doc.select("li");
			List<SongItem> items = new ArrayList<SongItem>() ;
			for(Element et : clicks){
				Elements eles = et.children() ;
				SongItem si = new SongItem() ;
				for (Element ele : eles) {
					String cls = ele.attr("class") ;
//					System.out.println(cls);
					Element el  = null ;
					if("title".equalsIgnoreCase(cls)){
						el  = ele.child(0) ;
						si.setSid(el.attr("js-data-id"));
						si.setSname(el.attr("title"));
					}else if("artist".equalsIgnoreCase(cls)){
						el  = ele.child(0) ;
						si.setAuthor(el.attr("title"));
					}else {
						continue;
					}
				}
				System.out.println(si);
				items.add(si) ;
				System.out.println(items.size());
			}
			*/
			
			Dom4jParseNetSongXml netSong = Dom4jParseNetSongXml.getInstance() ;
			List<XmlSong> songs = netSong.getAllSongsWithItems() ;
			for (XmlSong xs : songs) {
				if(!JsoupParseSo360Music.class.getName().equals(xs.getParseClass()))
					continue;
				String encode = xs.getEncode() ;
				for(XmlSongItem xsi : xs.getXmlSongItems()){
					if(!"/top/love.html".equals(xsi.getUrl())) continue;
					String url = xs.getUrl() + xsi.getUrl() ;
					try {
						List<SongItem> songItems = jpsm.getSongItem(url, encode) ;
//						System.out.println(songItems);
						for (SongItem songItem : songItems) {
							System.out.println(songItem);
						}
						
						List<Song> songList = jpsm.getSong(xs, xsi) ;
//						System.out.println(songList);
						for (Song song : songList) {
							System.out.println(song);
						}
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Song> getSearchSong(XmlSong xs, String key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
