package com.baiting.http.netsong;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;

import com.baiting.bean.NetSong;
import com.baiting.bean.netsong.Song;
import com.baiting.bean.netsong.XmlSong;
import com.baiting.http.neturl.JsoupParseNetMusicFactory;
import com.baiting.layout.MusicTable;
import com.baiting.layout.NetSongPanel;
import com.baiting.layout.ShowMsgPanel;
import com.baiting.layout.SongListTable;
import com.baiting.util.CommonUtil;
import com.baiting.util.StringUtil;

public class BaiduSearchSongList extends SearchNetSongHttp {
	
	private static final String startBody = "<html><body><a href='#'>";
	private static final String endBody = "</a></body></html>";
	
	public BaiduSearchSongList() {
		super();
	}
	
	public BaiduSearchSongList(NetSong netSong) {
		this();
		super.netSong = netSong ;
	}
	
	public synchronized List<NetSong> startGradSongList() {
		if(StringUtil.isEmpty(netSong.getSongName()) && 
				StringUtil.isEmpty(netSong.getSinger())) {
			return null;
		}
		
		List<NetSong> netSongList = new ArrayList<NetSong>();
		String result = "";
		if(!StringUtil.isEmpty(netSong.getSongName())) {
			result = netSong.getSongName();
		}
		if(!StringUtil.isEmpty(netSong.getSinger())) {
			if(StringUtil.isEmpty(result)) {
				result = netSong.getSinger();
			} else {
				result += "+"+netSong.getSinger();
			}
		}
		
//		System.out.println(result);
		
		try {
			for(XmlSong xs: xss) {
				if("BaiduMusic".equalsIgnoreCase(xs.getCode())){ //TODO:百度配置模块{此为百度搜索}，可留作扩展
					List<Song> songs = JsoupParseNetMusicFactory.getFactory().getSearchSongs(xs, result) ;
					for (Song song : songs) {
						try {
							NetSong netSongTmp = new NetSong() ;
							netSongTmp.setSid(song.getSongId());
							netSongTmp.setSongName(song.getSongName());
							netSongTmp.setAlbum(song.getAlbumName());
							netSongTmp.setSinger(song.getArtistName());
							netSongTmp.setUrl(song.getSongLink());
							netSongTmp.setType(NET_SONG_LIST_SEARCH);
							netSongList.add(netSongTmp);
						} catch (Exception e) {
							continue ;
						}
					}
				}
			}
		} catch (Exception e) {
			log.info("搜索歌曲---HttpRequest---异常");
			e.printStackTrace();
		}  finally {
			
		}
		return (netSongList.size()>0?netSongList:null);
	}
	
	@Override
	public void run() {
		ShowMsgPanel msgPanel = ShowMsgPanel.getInstance();
		msgPanel.setOpaque(false);
		NetSongPanel.getInstance().create().removeAll();
		NetSongPanel.getInstance().create().repaint();
		NetSongPanel.getInstance().create().add(msgPanel,BorderLayout.CENTER);
		NetSongPanel.getInstance().create().updateUI();
		msgPanel.setMsg(getConfigMap().get("searching.song.msg").toString());
		log.info("正在搜索歌曲信息...");
		List<NetSong> lists = startGradSongList();
		if(null != lists && lists.size()>0) {
			String[] titles = getConfigMap().get("search.song.table.header").toString().split(",");
			Object[][] datas = new Object[lists.size()][titles.length];
			int count = 0;
			for(NetSong netSong : lists) {
				datas[count][0] = new JCheckBox();
				datas[count][1] = netSong.getSongName();
				datas[count][2] = netSong.getSinger();
				datas[count][3] = netSong.getAlbum();
				datas[count][4] = startBody+getConfigMap().get("search.listen.test.label").toString()+endBody;
				datas[count][5] = startBody+getConfigMap().get("add.label").toString()+endBody;
				datas[count][6] = startBody+getConfigMap().get("down.label").toString()+endBody;
				count++;
			}
			//log.info(lists.size()+"");
			SongListTable table = new SongListTable(datas,titles,lists);
			this.setMusicTable(table);
			table.getTableHeader().setBackground(CommonUtil.getColor("#f8eae7"));
			JScrollPane tablePanel = new JScrollPane();
			tablePanel.setBorder(null);
			tablePanel.setOpaque(false);
			tablePanel.getViewport().setOpaque(false);
			tablePanel.setViewportView(table);
			tablePanel.setColumnHeaderView(table.getTableHeader()); //设置头部（HeaderView部分）  
			tablePanel.getColumnHeader().setOpaque(false); //再取出头部，并设置为透明 
			tablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			tablePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			tablePanel.setPreferredSize(new Dimension(getWidth()/2+60, getHeight()/2+100));
            NetSongPanel.getInstance().create().removeAll();
			NetSongPanel.getInstance().create().repaint();
			NetSongPanel.getInstance().create().add(tablePanel,BorderLayout.CENTER);
			NetSongPanel.getInstance().create().updateUI();
			tablePanel = null;
		} else {
			msgPanel.setMsg(getConfigMap().get("search.fail.msg").toString());
		}
		msgPanel = null;
	}

	@Override
	public MusicTable getMusicTable() {
		// TODO Auto-generated method stub
		return this.musicTable ;
	}

}
