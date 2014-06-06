package com.baiting.http.neturl;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
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

public class JsoupParseBaiduMusic extends JsoupParseNetMusic {
	
	private final Logger log = Logger.getLogger(JsoupParseBaiduMusic.class.getName()) ;
	
	public JsoupParseBaiduMusic(){
		super() ;
		log.info("JsoupParseBaiduMusic init ...");
	}
	
	/**
	 * 得到JSON里面的Song信息
	 * @param url
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Song> getSong(XmlSong xs, XmlSongItem xsi) throws Exception {
		if(Cache.hasCache(xsi.getCode())){
			return (List<Song>)Cache.get(xsi.getCode()) ;
		}
		List<Song> list = null;
		StringBuffer sb = new StringBuffer() ;
		List<SongItem> songItems = getSongItem(xs.getUrl() + xsi.getUrl(), xs.getEncode()) ;
		for (SongItem songItem : songItems) {
			sb.append(songItem.getSid()).append(",") ;
		}
		try {
			String songIds = sb.toString().substring(0, sb.lastIndexOf(",")) ;
			list = this.parseSongInfo(xs, songIds) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!Cache.hasCache(xsi.getCode()))
			Cache.put(xsi.getCode(), list) ;
		return list ;
	}
	
	private List<Song> parseSongInfo(XmlSong xs, String songIds) throws Exception {
		List<Song> list = new ArrayList<Song>() ;
		String info = getSongInfo(xs, URLEncoder.encode(songIds, xs.getEncode())) ;
		log.log(Level.CONFIG, info) ;
		JSONObject json = JSONObject.parseObject(info);
		JSONArray jsonArr = json.getJSONObject("data").getJSONArray("songList") ;
		for (int i=0;  i < jsonArr.size(); i++) {
			try {
				Song song = JSONObject.toJavaObject(jsonArr.getJSONObject(i), Song.class);
				list.add(song) ;
			} catch (Exception e) {
				continue ;
			}
		}
		return list ;
	}
	
	/**
	 * 根据关键字搜索歌曲
	 * @param xs
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<Song> getSearchSong(XmlSong xs, String key) throws Exception {
		if(null == key || "".equals(key)) return new ArrayList<Song>();
		
		String parseUrl = xs.getUrl() +"/"+ xs.getSearchParam() + "?" + xs.getSearchKey() + "=" + URLEncoder.encode(key, xs.getEncode()) ;
		String songIds = null;
		List<SongItem>songItems = this.getSongItem(parseUrl, xs.getEncode()) ;
		System.out.println(songItems);
		for (SongItem songItem : songItems) {
			if(null == songIds){
				songIds = songItem.getSid() ;
			} else {
				songIds += ","+songItem.getSid() ;
			}
		}
		List<Song> songs = this.parseSongInfo(xs, songIds) ;
		
		return songs ;
	}

	/**
	 * 获取li标签里面的所有SongItem
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<SongItem> getSongItem(String url, String encode) throws Exception{
		if(Cache.hasCache(url)){
			return (List<SongItem>)Cache.get(url) ;
		}
		List<SongItem> list = new ArrayList<SongItem>() ;
		Document doc = Jsoup.parse(getParseHtml(url, encode));
		//查找包含<li class="...">内容   
		Elements clicks = doc.select("li");
		for(Element et : clicks){
			String tmp = et.attr("data-songitem") ;
			if(null == tmp || "".equals(tmp) || tmp.indexOf("songItem") == -1)
				continue ;
			log.log(Level.CONFIG, tmp) ;
			JSONObject json = JSONObject.parseObject(tmp) ;
			JSONObject songItem = json.getJSONObject("songItem");
			SongItem item = JSONObject.toJavaObject(songItem, SongItem.class) ;
			list.add(item) ;
		}
		if(!Cache.hasCache(url))
			Cache.put(url, list) ;
		return list ;
	}
	
	/**
	 * 抓取百度音乐html
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
		
		String reg = "<ul>(.*)</ul>";
		htmlContent = getFilteredContent(htmlContent,reg,0);
		
		//TODO:去除空格
		htmlContent = htmlContent.trim() ;
		
		if(!Cache.hasCache(url+"_@_"+encode))
			Cache.put(url+"_@_"+encode, htmlContent) ;
		
		return htmlContent ;
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
	
	/**
	 * 读取写入歌词文件
	 * @param lrcUrl
	 * 						歌词文件地址
	 * @param fileName
	 * 						歌曲名
	 * @throws Exception
	 */
	public String getLyric(String lrcUrl) throws Exception {
		if(Cache.hasCache(lrcUrl)){
			return (String) Cache.get(lrcUrl) ;
		}
		
		if(lrcUrl.lastIndexOf(".lrc") == -1)
			return null;
		
		HttpGet httpget = new HttpGet(lrcUrl) ;
		CloseableHttpResponse response = httpclient.execute(httpget);
    	try {
		    int statusCode = response.getStatusLine().getStatusCode() ;
		    if (statusCode == HttpStatus.SC_OK) {
		    	HttpEntity entity = response.getEntity() ;
		    	String content = "" ;
		    	if(entity != null){
		    		content = EntityUtils.toString(entity, "UTF-8") ;
		    	}
		    	
		    	log.log(Level.CONFIG, content) ; ;

		    	if(!Cache.hasCache(lrcUrl))
		    		Cache.put(lrcUrl, content) ;
		    	
		    	return content ;
		    }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }  finally {
	    	response.close() ;
	    }
    	return null ;
	}
	
	@Override
	public String getSongUrl(XmlSong xs, String songId) throws Exception {
		// TODO Auto-generated method stub
		String info = getSongInfo(xs, songId) ;
		log.log(Level.CONFIG, info) ;
		JSONObject json = JSONObject.parseObject(info);
		JSONArray jsonArr = json.getJSONObject("data").getJSONArray("songList") ;
		for (int i=0;  i < jsonArr.size(); i++) {
			try {
				Song song = JSONObject.toJavaObject(jsonArr.getJSONObject(i), Song.class);
				return song.getSongLink() ;
			} catch (Exception e) {
				continue ;
			}
		}
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			JsoupParseBaiduMusic jpsm = new JsoupParseBaiduMusic() ;
			
			Dom4jParseNetSongXml netSong = Dom4jParseNetSongXml.getInstance() ;
			List<XmlSong> songs = netSong.getAllSongsWithItems() ;
			for (XmlSong xs : songs) {
				if(!JsoupParseBaiduMusic.class.getName().equals(xs.getParseClass()))
					continue;
				String encode = xs.getEncode() ;
				for(XmlSongItem xsi : xs.getXmlSongItems()){
					String url = xs.getUrl() + xsi.getUrl() ;
					String htmlContent = jpsm.visitURL(url, encode);
					htmlContent = jpsm.getParseHtml(url, encode);
					try {
						File file = new File("C:"+File.separator + xsi.getCode() +".txt") ;
						FileOutputStream fos = new FileOutputStream(file) ;
						fos.write(htmlContent.getBytes()) ;
						fos.close() ;
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
					
					try {
						File file = new File("C:"+File.separator + xsi.getCode() +"_r.txt") ;
						FileOutputStream fos = new FileOutputStream(file) ;
						fos.write(jpsm.getParseHtml(url, encode).getBytes()) ;
						fos.close() ;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					try {
						List<SongItem> songItems = jpsm.getSongItem(url, encode) ;
						for (SongItem songItem : songItems) {
							System.out.println(songItem);
						}
						
						List<Song> songList = jpsm.getSong(xs, xsi) ;
						for (Song song : songList) {
							System.out.println(song);
						}
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			String url = "http://music.baidu.com//top/new" ;
			String encode = "utf-8" ;
			try {
				System.out.println(jpsm.getParseHtml(url, encode));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

