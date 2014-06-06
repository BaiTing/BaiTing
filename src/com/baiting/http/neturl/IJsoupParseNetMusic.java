package com.baiting.http.neturl;

import java.util.List;

import com.baiting.bean.netsong.Song;
import com.baiting.bean.netsong.SongItem;
import com.baiting.bean.netsong.XmlSong;
import com.baiting.bean.netsong.XmlSongItem;

/**
 * 解析网络歌曲公共接口
 * @author sunshine
 *
 */
public interface IJsoupParseNetMusic {

	/**
	 * 根据传入的url和页面编码访问url
	 * @param url 要访问的url
	 * @param encode 要访问的页面编码
	 * @return 返回来的内容
	 * @throws Exception
	 */
	public String visitURL(String url, String encode) throws Exception;
	
	/**
	 * 根据传入的url和编码抓取需要的内容
	 * @param url 要访问的url
	 * @param encode 要访问的页面编码
	 * @return 正则表达式过滤的内容
	 * @throws Exception
	 */
	public String getParseHtml(String url, String encode) throws Exception;
	
	/**
	 * 根据传入的url和页面编码返回SongItem集合
	 * @param url 要访问的url
	 * @param encode 要访问的页面编码
	 * @return 返回SongItem集合
	 * @throws Exception
	 */
	public List<SongItem> getSongItem(String url, String encode) throws Exception;
	
	/**
	 * 返回SongInfo JSON
	 * @param songIds
	 * @return
	 * @throws Exception
	 */
	public String getSongInfo(XmlSong xs, String songIds) throws Exception;
	
	/**
	 * 返回所有歌曲列表
	 * @param xs  配置文件解析对象
	 * @param xsi 配置文件解析对象
	 * @return 所有歌曲列表
	 * @throws Exception
	 */
	public List<Song> getSong(XmlSong xs, XmlSongItem xsi) throws Exception;
	
	/**
	 * 根据关键字返回搜索的歌曲列表
	 * @param xs
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<Song> getSearchSong(XmlSong xs, String key) throws Exception;
	
	/**
	 * 根据lrcUrl返回歌词内容
	 * @param lrcUrl 歌词url
	 * @return 歌词内容
	 * @throws Exception
	 */
	public String getLyric(String lrcUrl) throws Exception;
}
