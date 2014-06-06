package com.baiting.http.neturl;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.baiting.bean.netsong.SongItem;
import com.baiting.bean.netsong.XmlSong;
import com.baiting.cache.Cache;
import com.baiting.util.Utils;

public abstract class JsoupParseNetMusic implements IJsoupParseNetMusic {

	CloseableHttpClient httpclient = null ;
	
	public JsoupParseNetMusic() {
		super() ;
		if(null == httpclient)
			httpclient = Utils.getInstance().getConn() ;
	}
	
	@Override
	public String visitURL(String url, String encode) throws Exception {
		if(Cache.hasCache(url+"_$_"+encode)){
			return (String) Cache.get(url+"_$_"+encode) ;
		}
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpget);
	    try {
		    int statusCode = response.getStatusLine().getStatusCode() ;
		    if (statusCode == HttpStatus.SC_OK) {
		    	HttpEntity entity = response.getEntity() ;
		    	String content = "" ;
		    	if(entity != null){
		    		content = EntityUtils.toString(entity, encode) ;
		    	}
		    	if(!Cache.hasCache(url+"_$_"+encode)){
					Cache.put(url+"_$_"+encode, content) ;
				}
		        return content ;
		    }
	    } catch (Exception e) {
	        e.printStackTrace();
//	        JOptionPane.showMessageDialog(c, e.getLocalizedMessage(), Config.NAME, JOptionPane.INFORMATION_MESSAGE) ;
	    }  finally {
	    	try {
				response.close() ;
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return "";
	}
	
	/**
	 * 根据正则表达式匹配
	 * @param htmlContent
	 * @param reg
	 * @param i
	 * @return
	 */
	public String getFilteredContent(String htmlContent, String reg, int i) {
		String content = "";
		int k=1;
		Pattern pp = Pattern.compile(reg, Pattern.DOTALL);
		Matcher m = pp.matcher(htmlContent);
		while (m.find()) {
			content = m.group();
			if(k++==i)
				break;
		}
		
		return content;
	}
	
	@Override
	public String getParseHtml(String url, String encode) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getSongInfo(XmlSong xs, String songIds) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SongItem> getSongItem(String url, String encode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLyric(String lrcUrl) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSongUrl(XmlSong xs, String songId) throws Exception {
		return null ;
	}
}
