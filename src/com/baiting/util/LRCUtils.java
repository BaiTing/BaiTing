package com.baiting.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.baiting.bean.SearchResult;
import com.baiting.bean.Song;


public class LRCUtils
{
  private static final String SearchPath = "http://ttlrcct2.qianqian.com/dll/lyricsvr.dll?sh?Artist={0}&Title={1}&Flags=0";
  private static final String DownloadPath = "http://ttlrcct2.qianqian.com/dll/lyricsvr.dll?dl?Id={0}&Code={1}";

  public static String fetchLyricContent(int lrcId, String lrcCode)
  {
    System.out.println("lrcId = " + lrcId + ",lrcCode = " + lrcCode);
    String url = MessageFormat.format(DownloadPath, String.valueOf(lrcId), lrcCode);
    return readURL(url);
  }
  
  public static List<SearchResult> search(String artist, String title)
  	throws Exception {
	  if (artist == null)
		  artist = "";
		else
			artist = artist.toLowerCase().replace(" ", "").replace("'", "");
	    if (title == null)
	      title = "";
	    else
	    	title = title.toLowerCase().replace(" ", "").replace("'", "");
	    String url = MessageFormat.format(SearchPath, ToQianQianHexString(artist, "UTF-16LE"), ToQianQianHexString(title, "UTF-16LE"));
	    String back = readURL(url);
	    System.out.println("back=" + back);
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	    factory.setNamespaceAware(true);

	    Document doc = factory.newDocumentBuilder().parse(new InputSource(new StringReader(back)));
	    NodeList nodes = doc.getElementsByTagName("lrc");
	    List<SearchResult> list = new ArrayList<SearchResult>();
	    for (int i = 0; i < nodes.getLength(); ++i) {
		      Node node = nodes.item(i);
		      String artistTemp = node.getAttributes().getNamedItem("artist").getTextContent();
		      String titleTemp = node.getAttributes().getNamedItem("title").getTextContent();
		      if(artistTemp.indexOf(artist) == -1){
		    	  if(i < nodes.getLength() - 1)
		    		  continue ;
		      }
		      final int lrcId = Integer.parseInt(node.getAttributes().getNamedItem("id").getTextContent());
		      final String lrcCode = CreateQianQianCode(artistTemp, titleTemp, lrcId);
		      SearchResult.Task task = new SearchResult.Task()
		      {
		        public String getLyricContent() {
		          String content = LRCUtils.fetchLyricContent(lrcId, lrcCode);
		          System.out.println("content=" + content);
		          return content;
		        }

		      };
		      String content = task.getLyricContent() ;
		      if(content.indexOf("errmsg") != -1 && content.indexOf("errcode") != -1) continue ;
		      SearchResult result = new SearchResult(lrcId, lrcCode, artistTemp, titleTemp, task);
		      list.add(result);
		}
	    if(list.size() > 1){ //弹出选择框，让用户自己选择所要下载的歌词
//	    	WebSearchDialog wd = new WebSearchDialog(lp.getPlayer().getCurrentItem(), lp) ;
//	    	wd.setList(list) ;
//	    	wd.getTable().setEnabled(true);
//	    	wd.getSave().setEnabled(true) ;
//	    	wd.setVisible(true) ;
//	    	return new ArrayList<SearchResult>();
	    }
	   
	    return list; 
  }

  public static List<SearchResult> search(Song song) throws Exception
  {
	  String artist = song.getSinger();
	  String title = song.getName();
	  return search(artist, title); 
   }

  public static String readURL(String url) {
	CloseableHttpClient httpClient = Utils.getInstance().getConn() ;
    try {
    	HttpGet httpget = new HttpGet(url);
    	CloseableHttpResponse response = httpClient.execute(httpget);
       try {
		    int statusCode = response.getStatusLine().getStatusCode() ;
		    if (statusCode == HttpStatus.SC_OK) {
		    	HttpEntity entity = response.getEntity() ;
		    	String content = "" ;
		    	if(entity != null){
		    		content = EntityUtils.toString(entity, "UTF-8") ;
		    	}
		        return content ;
		    }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }  finally {
	    	response.close() ;
	    }
    } catch (Exception exe) {
      exe.printStackTrace(); 
    } finally {
    	try {
			httpClient.close() ;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    return null;
  }

  private static String ToQianQianHexString(String s, String chatset) throws UnsupportedEncodingException
  {
    StringBuilder sb = new StringBuilder();
    byte[] bytes = s.getBytes(chatset);
    int len = bytes.length; 
    for (int i = 0; i < len; ++i) { 
    	byte b = bytes[i];
    	int j = b & 0xFF;
    	if (j < 16)
    		sb.append("0");
    	sb.append(Integer.toString(j, 16).toUpperCase());
    }
    return sb.toString();
  }

  private static String CreateQianQianCode(String singer, String title, int lrcId) throws UnsupportedEncodingException {
	  	int c;
	    String qqHexStr = ToQianQianHexString(singer + title, "UTF-8");
	    int length = qqHexStr.length() / 2;
	    int[] song = new int[length];
	    for (int i = 0; i < length; ++i)
	      song[i] = Integer.parseInt(qqHexStr.substring(i * 2, i * 2 + 2), 16);

	    int t1 = 0; int t2 = 0; int t3 = 0;
	    t1 = (lrcId & 0xFF00) >> 8;
	    if ((lrcId & 0xFF0000) == 0)
	      t3 = 0xFF & (t1 ^ 0xFFFFFFFF);
	    else {
	      t3 = 0xFF & (lrcId & 0xFF0000) >> 16;
	    }

	    t3 |= (0xFF & lrcId) << 8;
	    t3 <<= 8;
	    t3 |= 0xFF & t1;
	    t3 <<= 8;
	    if ((lrcId & 0xFF000000) == 0)
	      t3 |= 0xFF & (lrcId ^ 0xFFFFFFFF);
	    else {
	      t3 |= 0xFF & lrcId >> 24;
	    }

	    int j = length - 1;
	    while (j >= 0) {
	      c = song[j];
	      if (c >= 128) {
	        c -= 256;
	      }

	      t1 = (int)(c + t2 & 0xFFFFFFFF);
	      t2 = (int)(t2 << j % 2 + 4 & 0xFFFFFFFF);
	      t2 = (int)(t1 + t2 & 0xFFFFFFFF);
	      --j;
	    }
	    j = 0;
	    t1 = 0;
	    while (j <= length - 1) {
	      c = song[j];
	      if (c >= 128)
	        c -= 256;

	      int t4 = (int)(c + t1 & 0xFFFFFFFF);
	      t1 = (int)(t1 << j % 2 + 3 & 0xFFFFFFFF);
	      t1 = (int)(t1 + t4 & 0xFFFFFFFF);
	      ++j;
	    }

	    int t5 = (int)Conv(t2 ^ t3);
	    t5 = (int)Conv(t5 + (t1 | lrcId));
	    t5 = (int)Conv(t5 * (t1 | t3));
	    t5 = (int)Conv(t5 * (t2 ^ lrcId));

	    long t6 = t5;
	    if (t6 > 2147483648L)
	      t5 = (int)(t6 - 4294967296L);

	    return Integer.toString(t5);
  }

  public static long Conv(int i) {
	  long r = i % 4294967296L;
	    if ((i >= 0) && (r > 2147483648L)) {
	      r -= 4294967296L;
	    }

	    if ((i < 0) && (r < 2147483648L))
	      r += 4294967296L;

	    return r;
  }

  public static String md5(String s)
  {
	  if ((s == null) || (s.equals("")))
	      return md5("_123456_");

	    String s1 = "";
	    try {
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      md.update(s.getBytes("UTF-8"));
	      byte[] abyte0 = md.digest();
	      s1 = byte2hex(abyte0);
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return s1;
  }

  private static String byte2hex(byte[] abyte0) {
	  StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < abyte0.length; ++i) {
	      int j = abyte0[i] & 0xFF;
	      if (j < 16) {
	        sb.append("0");
	      }

	      sb.append(Integer.toString(j, 16).toLowerCase());
	    }
	    return sb.toString();
  }

  public static boolean isEmpty(String content) {
    if (content == null)
      return true;

    return (content.trim().length() == 0);
  }

  public static void main(String[] args)  throws Exception
  {
//    List<SearchResult> list = search("", "那些年");
//    if (list.size() > 0) {
//      SearchResult result = (SearchResult)list.get(0);
//      System.out.println(fetchLyricContent(result.getLrcId(), result.getLrcCode()));
//    }
  }
}