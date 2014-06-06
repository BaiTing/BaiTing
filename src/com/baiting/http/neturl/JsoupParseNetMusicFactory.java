package com.baiting.http.neturl;

import java.util.List;

import com.baiting.bean.netsong.Song;
import com.baiting.bean.netsong.XmlSong;
import com.baiting.bean.netsong.XmlSongItem;

public class JsoupParseNetMusicFactory {

	private static JsoupParseNetMusicFactory factory ;
	
	private JsoupParseNetMusicFactory(){} ;
	
	public synchronized static JsoupParseNetMusicFactory getFactory(){
		if(null == factory)
			factory = new JsoupParseNetMusicFactory() ;
		return factory ;
	}
	
	/**
	 * 返回排行榜歌曲列表
	 * @param xs
	 * @param xsi
	 * @return
	 * @throws Exception
	 */
	public List<Song> getSongs(XmlSong xs, XmlSongItem xsi) throws Exception {
		if(null == xs || null == xsi) return null;
		
		JsoupParseNetMusic jpnm = getJsoupParseNetMusic(xs) ;
		
		if(null == jpnm) return null;
		
		return jpnm.getSong(xs, xsi) ;
	}
	
	/**
	 * 根据关键字返回搜索到的歌曲列表
	 * @param xs
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<Song> getSearchSongs(XmlSong xs, String key) throws Exception {
		if(null == xs || null == key) return null;
		
		JsoupParseNetMusic jpnm = getJsoupParseNetMusic(xs) ;
		
		if(null == jpnm) return null;
		
		return jpnm.getSearchSong(xs, key) ;
	}
	
	/**
	 * 根据songId取到地址
	 * @param songId
	 * @return
	 * @throws Exception
	 */
	public String getSongUrl(XmlSong xs, String songId) throws Exception {
		if(null == xs || null == songId || "".equals(songId)) return null;
		
		JsoupParseNetMusic jpnm = getJsoupParseNetMusic(xs);
		
		if(null == jpnm) return null;
		
		return jpnm.getSongUrl(xs, songId);
	}
	
	/**
	 * 根据模块和songId取得歌曲信息json
	 * @param xs 模块
	 * @param songIds 歌曲id
	 * @return 歌曲信息json
	 * @throws Exception
	 */
	public String getSongInfo(XmlSong xs, String songIds) throws Exception {
		if(null == xs || null == songIds || "".equals(songIds)) return "{}" ;
		
		JsoupParseNetMusic jpnm = getJsoupParseNetMusic(xs);
		
		if(null == jpnm) return null;
		
		return jpnm.getSongInfo(xs, songIds);
	}
	
	/**
	 * 根据模块跟歌词地址取歌词内容
	 * @param xs  模块
	 * @param url 歌词地址
	 * @return 歌词内容
	 * @throws Exception
	 */
	public String getLyric(XmlSong xs, String url) throws Exception {
		if(null == xs || null == url || "".equals(url)) return null ;
		
		JsoupParseNetMusic jpnm = getJsoupParseNetMusic(xs) ;
		
		if(null == jpnm) return null;
		
		return jpnm.getLyric(url) ;
	}
	
	/**
	 * 根据模块得到模块的解析对象
	 * @param xs 模块
	 * @return 模块对应解析对象
	 * @throws Exception
	 */
	private JsoupParseNetMusic getJsoupParseNetMusic(XmlSong xs) throws Exception {
		String className = xs.getParseClass() ;
		
		if(null == className) return null ;
		
		JsoupParseNetMusic jpnm = (JsoupParseNetMusic) Class.forName(className).newInstance();
		
		return jpnm;
	}
}
