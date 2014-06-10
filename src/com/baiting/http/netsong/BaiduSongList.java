package com.baiting.http.netsong;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.baiting.bean.NetSong;
import com.baiting.bean.netsong.Song;
import com.baiting.bean.netsong.XmlSong;
import com.baiting.bean.netsong.XmlSongItem;
import com.baiting.http.neturl.JsoupParseNetMusicFactory;
import com.baiting.layout.NetSongPanel;
import com.baiting.layout.ShowMsgPanel;
import com.baiting.layout.SongListTable;
import com.baiting.ui.MyScrollBarUI;

public class BaiduSongList extends NetSongList  implements MouseListener {

	private JScrollPane tablePanel;
	
	public BaiduSongList() throws Exception {
		super();
	}
	
	public  void putXmlSong(XmlSong xs, XmlSongItem xsi){
		this.map.put(xs, xsi) ;
	}
	
	@Override
	public void setXmlSongCode(String code) {
		this.xmlSongCode = code ;
	}
	
	@Override
	public void setXmlSongItemName(String name) {
		this.xmlSomgItemName = name ;
	}
	
	public synchronized List<NetSong> startGradSongList() {
		List<NetSong> netSongs = new ArrayList<NetSong>();
		log.info("---正准备抓去歌曲列表----");
		try {
				for(Map.Entry<XmlSong, XmlSongItem> entry : map.entrySet()){
					if(xmlSongCode.equalsIgnoreCase(entry.getKey().getCode())){
						try {
							List<Song> list = JsoupParseNetMusicFactory.getFactory().getSongs(entry.getKey(), entry.getValue()) ;
							for (Song song : list) {
								NetSong ns = new NetSong();
								ns.setSongName(song.getSongName());
								ns.setAlbum(song.getAlbumName());
//								ns.setLocalPath("");
								ns.setSid(song.getSongId());
								if(song.getArtistName() != null)
									ns.setSinger(song.getArtistName());
								if(song.getSingerName() != null)
									ns.setSinger(song.getSingerName());
//								ns.setType(1);
								ns.setUrl(song.getSongLink());
								ns.setXmlSong(entry.getKey());
								netSongs.add(ns);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return netSongs.size()>0?netSongs:null ;
	}
	
	
	@Override
	public synchronized void run() {
		ShowMsgPanel msgPanel = ShowMsgPanel.getInstance();
		msgPanel.setOpaque(false);
		JPanel netSongPanel = NetSongPanel.getInstance().create();
		netSongPanel.removeAll();
		netSongPanel.repaint();
		if(null != netSongPanel) {
			netSongPanel.add(msgPanel,BorderLayout.CENTER);
			netSongPanel.updateUI();
			msgPanel.setMsg(getConfigMap().get("loading.msg").toString()+xmlSomgItemName+".....");
		}
		List<NetSong> netSongList = startGradSongList();
		if(null != netSongList && netSongList.size()>0) {
			String[] titles = getConfigMap().get("net.song.tabel.title").toString().split(",");
			Object[][] datas = new Object[netSongList.size()][titles.length];
			int count = 0;
			for(NetSong netSong:netSongList) {
				JCheckBox jcb = new JCheckBox() ;
				jcb.setSelected(true);
				datas[count][0] = jcb ; //复选框
				datas[count][1] = netSong.getSongName();
				datas[count][2] = netSong.getSinger();
				datas[count][3] = netSong.getAlbum();
				datas[count][4] = getConfigMap().get("search.listen.test.label").toString();
				datas[count][5] = getConfigMap().get("add.label").toString();
				datas[count][6] = getConfigMap().get("down.label").toString();
				count++;
			}
			SongListTable table = new SongListTable(datas,titles,netSongList);
//			table.getTableHeader().setBackground(CommonUtil.getColor(getConfigMap().get("net.song.list.table.background.color").toString()));

			tablePanel = new JScrollPane();
			tablePanel.setBorder(null);
			tablePanel.getViewport().setOpaque(false); //将JScrollPane设置为透明
			tablePanel.setOpaque(false); //将中间的viewport设置为透明 
			tablePanel.setViewportView(table); //装载表格
			tablePanel.setColumnHeaderView(table.getTableHeader()); //设置头部（HeaderView部分）  
			tablePanel.getColumnHeader().setOpaque(false); //再取出头部，并设置为透明 
			tablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//			tablePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			tablePanel.setPreferredSize(new Dimension(getWidth()/2+60, getHeight()/2+100));
			tablePanel.getVerticalScrollBar().setUI(new MyScrollBarUI());
			
			netSongPanel.removeAll();
			netSongPanel.repaint();
			netSongPanel.add(tablePanel,BorderLayout.CENTER);
			netSongPanel.updateUI();
			table = null;
		} else {
			msgPanel.setMsg(getConfigMap().get("net.loading.fail.msg").toString());
		}
		msgPanel = null;
		netSongPanel = null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
