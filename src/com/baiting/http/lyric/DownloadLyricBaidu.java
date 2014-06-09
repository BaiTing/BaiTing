package com.baiting.http.lyric;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.baiting.bean.SearchResult;
import com.baiting.bean.Song;
import com.baiting.util.StringUtil;
import com.baiting.util.Utils;

public class DownloadLyricBaidu extends DownloadLyric implements Runnable{
	
	public DownloadLyricBaidu() {
		super();
	}
	
	public DownloadLyricBaidu(Song songTmp) {
		super(songTmp);
	}
	
	public DownloadLyricBaidu(Song songTmp,String url) {
		super(songTmp,url);
	}
	
	public boolean startDownload() {
		song.setLrcState(2);
		song.setLrcState(2);
		String name = song.getName();
		String artist = song.getSinger();
		log.info("正在下载【"+name+"】的歌词...");
		try{
			    String lrcStr = this.getLyric(song) ;
				if(!StringUtil.isEmpty(lrcStr)) {
					String lrcPath = getLrcDir()+"/"+artist+"-"+name+LRC_EXT;
					File lrcFile = new File(lrcPath);
					if(!lrcFile.exists()) {
						log.info("正在创建["+lrcPath+"]文件......");
						lrcFile.createNewFile();
					}
					BufferedWriter writer = new BufferedWriter(new FileWriter(lrcFile));
					writer.write(lrcStr);
					writer.close();
					writer = null;
					lrcStr = null;
					song.setLrcState(1);
					song.setLrcState(1);
					log.info("【"+name+"】的歌词下载完成...");
					return true;
				}
				song.setLrcState(-1);
				log.info("【"+name+"】的歌词下载失败....");
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				song.setLrcState(-1);
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				song.setLrcState(-1);
				return false;
			}
	}
	
	/**
	   * 根据歌曲的信息去下载歌词内容
	   * @param fileName 文件本名
	   * @param info 歌曲信息
	   * @return 歌词内容
	   */
	  public String getLyric(Song info)  throws IOException
	  {
	    log.info("进来找歌词了");
	    String ly = getLyricTTPlayer(info);
	    if (ly != null)
	      log.info("TT上搜索到了...");
	    else{
	      log.info("去百度上搜索了...");	
	      ly = getLyricBaidu(info);
	    }

	    return ly;
	  }

	  /**
	   * 从千千静听的服务器上搜索歌词
	   * @param info 歌曲信息对象
	   * @return 歌词
	   * @throws java.io.IOException
	   */
	  private String getLyricTTPlayer(Song info) throws IOException
	  {
	    List<SearchResult> list = LRCUtil.search(info);
	    if (list.isEmpty())
	      return null;

	    return list.get(0).getContent();
	  }
	  
	  /**
	   * 从百度去搜索歌词
	   * @param info 播放项
	   * @return 歌词内容，可能为NULL
	   */
	  private String getLyricBaidu(Song info)
	  {
	    String song;
	    try
	    {
	      song = info.getName() ;
	      String name = info.getSinger() + " " +info.getName() ;
	      String s = getBaidu_Lyric(name);
	      if (s == null) {
	        s = getBaidu_Lyric(song);
	        return s;
	      }
	      return s;
	    } catch (Exception ex) {
	    }
	    return null;
	  }
	  
	  /**
	   * 得到在百度上搜索到的歌词的内容
	   * @param key 关键内容
	   * @return 内容
	   * @throws java.lang.Exception
	   */
	  private String getBaidu_Lyric(String key)  throws Exception
	  {
		CloseableHttpClient httpclient = Utils.getInstance().getConn() ;
		try {
			HttpHost targetHost = new HttpHost("www.baidu.com", 80, "http");
			HttpGet httpget = new HttpGet("http://www.baidu.com/s?wd=" + 
					URLEncoder.encode(new StringBuilder().append("filetype:lrc ").append(key).toString(), 
					"UTF-8"));
			
			CloseableHttpResponse response = httpclient.execute(targetHost, httpget);
			try {
				int i = response.getStatusLine().getStatusCode() ;
				log.info(i+"") ;
				
				HttpEntity entity = response.getEntity() ;
		    	String temp = "" ;
		    	if(entity != null){
		    		temp = EntityUtils.toString(entity, "UTF-8") ;
		    	}
				
				Matcher m = Pattern.compile("(?<=LRC/Lyric - <a href=\").*?(?=\" target=\"_blank\">HTML版</a>)").matcher(temp);
				String content = null;
				if (m.find()) {
				  String str = m.group();
				  content = getURLContent(str);
				  m = Pattern.compile("(?<=<body>).*?(?=</body>)").matcher(content);
				  if (m.find())
				    content = m.group();
				}
				return htmlTrim2(content);
			} catch (Exception e) {
				response.close() ;
				throw e ;
			}
		} catch (Exception e) {
			httpclient.close();
			throw e ;
		}
	  }
	  
	  /**
	   * 得到URL的内容,最好是只限百度使用
	   * @param url URL
	   * @return 内容,可能是NULL
	   * @throws java.lang.Exception
	   */
	  private String getURLContent(String url) throws IOException
	  {
		CloseableHttpClient httpclient = Utils.getInstance().getConn() ;
		HttpGet httpget = new HttpGet(url);
	    CloseableHttpResponse response = httpclient.execute(httpget);
	    Header[] hs = response.getAllHeaders() ;
	    for (Header h : hs) {
	        log.info(h.toString());
	    }
	    
	    HttpEntity entity = response.getEntity() ;
		String temp = "" ;
		if(entity != null){
			temp = EntityUtils.toString(entity, "UTF-8") ;
		}
	    
	    return temp;
	  }
	  
	  /**
	   * 去除HTML标记
	   * @param str1 含有HTML标记的字符串
	   * @return 去除掉相关字符串
	   */
	  public String htmlTrim(String str1)
	  {
	    String str = "";
	    str = str1;

	    str = str.replaceAll("</?[^>]+>", "");

	    str = str.replaceAll("\\s", "");
	    str = str.replaceAll("&nbsp;", "");
	    str = str.replaceAll("&amp;", "&");
	    str = str.replace(".", "");
	    str = str.replace("\"", "‘");
	    str = str.replace("'", "‘");
	    return str;
	  }

	  private String htmlTrim2(String str1) {
	    String str = "";
	    str = str1;

	    str = str.replaceAll("<BR>", "\n");
	    str = str.replaceAll("<br>", "\n");
	    str = str.replaceAll("</?[^>]+>", "");
	    return str;
	  }
	
	public void setSongTmp(Song song) {
		this.song = song;
	}
	
	public static void main(String[] args) {
		Song songTmp = new Song();
		songTmp.setName("陪着我的时候想着她");
		songTmp.setSinger("郭静");
		DownloadLyricBaidu lrc = new DownloadLyricBaidu(songTmp);
		try {
			String string =lrc.getLyric(songTmp) ;
			System.out.println(string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
