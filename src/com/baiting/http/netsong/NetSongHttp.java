package com.baiting.http.netsong;

import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;

import com.baiting.Music;
import com.baiting.bean.NetSong;
import com.baiting.bean.netsong.Song;
import com.baiting.bean.netsong.XmlSong;
import com.baiting.http.neturl.Dom4jParseNetSongXml;
import com.baiting.http.neturl.JsoupParseNetMusicFactory;
import com.baiting.util.StringUtil;
import com.baiting.util.Utils;

public abstract class NetSongHttp extends Music implements Runnable {

	protected CloseableHttpClient httpclient;
	protected List<XmlSong> xss ;
	
	public NetSongHttp() {
		if(null == httpclient)
			httpclient = Utils.getInstance().getConn();
		if(null == xss)
			try {
				xss = Dom4jParseNetSongXml.getInstance().getAllSongsWithItems() ;
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 根据歌曲搜索播放地址
	 * @param netSong
	 * @return
	 */
	protected synchronized String parseNetSongUrl(NetSong netSong) {
		if(null == netSong) {
			return null;
		}
		
		String result = "";
		String songUrl = "";
		String singer = netSong.getSinger().replaceAll("&", ",");
		result = netSong.getSongName()+" "+singer;
		
		log.info(result);
		
		try {
			for(XmlSong xs : xss){
				if("BaiduMusic".equals(xs.getCode())){ //此处默认为百度搜索，以后扩展
					List<Song> songs = JsoupParseNetMusicFactory.getFactory().getSearchSongs(xs, result) ;
					for (Song song : songs) {
						if(song.getSongName().equals(netSong.getSongName()) &&
								song.getArtistName().equals(netSong.getSinger())) {
							songUrl = song.getSongLink() ;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			log.info("在线试听---HttpRequest---异常");
			e.printStackTrace();
			return null;
		} finally {
			netSong = null;
		}
		
		return songUrl;
	}
	
	/**
	 * 根据歌曲ID搜索播放地址
	 * @param sid
	 * @param type
	 * @param xs
	 * @return
	 */
	protected String getNetSongUrl(String sid,int type, XmlSong xs) {
		String url = null;
		if(StringUtil.isEmpty(sid)) return url;
		try {
			url = JsoupParseNetMusicFactory.getFactory().getSongUrl(xs, sid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
}
