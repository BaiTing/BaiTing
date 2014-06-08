package com.baiting.layout;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.ListSelectionModel;

import com.baiting.Music;
import com.baiting.bean.NetSong;
import com.baiting.listener.MusicMouseListener;
import com.baiting.service.DownloadSongService;
import com.baiting.service.NetSongService;

public class SongListTable extends MusicTable {

	private static final long serialVersionUID = -8298003521968394492L;
	//private Object[][] datas;
	public static List<NetSong> netSongList, selectedSongs;
	public SongListTable(Object[][] obj,String[] obj2,List<NetSong> netSongList) {
		
		super(obj, obj2);
		SongListTable.netSongList = netSongList; //所有歌曲列表
		SongListTable.selectedSongs = netSongList; //用以维持被选中的列表
		
		/* 
         * 将表格设置为透明，表格同样包括表格本身和其中的内容项 
         * 仅仅将表格本身设置为透明也没有用，应该将其中的内容项也设置为透明 
         * 内容项的设置是通过设置渲染器的透明来实现 
         */
		for(int i=0; i<obj2.length; i++){
			if(i == 0){
				this.getColumnModel().getColumn(i).setCellEditor(new CheckBoxCellEditor(new JCheckBox(), selectedSongs));
				this.getColumnModel().getColumn(i).setCellRenderer(new CheckBoxCellRenderer());
			}else{
				this.getColumnModel().getColumn(i).setCellRenderer(new SongListTableCellRenderer());
			}
		}
		
		this.setOpaque(false);
		this.setShowVerticalLines(false);
		this.setAutoscrolls(true);
		this.setShowHorizontalLines(false);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setRowHeight(22);
		this.getColumnModel().getColumn(0).setPreferredWidth(15);
		this.getColumnModel().getColumn(1).setPreferredWidth(250);
		this.getColumnModel().getColumn(2).setPreferredWidth(100);
		this.getColumnModel().getColumn(3).setPreferredWidth(100);
		this.getColumnModel().getColumn(4).setPreferredWidth(20);
		this.getColumnModel().getColumn(5).setPreferredWidth(20);
		this.getColumnModel().getColumn(6).setPreferredWidth(20);
		
		this.addMouseMotionListener(addMouseMotionListener());
		this.addMouseListener(addMouseListener(this)) ;
	}
	
	public static List<NetSong> getSelectedNetSongList(){
		return selectedSongs ;
	}
	
	public static List<NetSong> getNetSongList() {
		return netSongList;
	}
	
	private MouseListener addMouseListener(final SongListTable slt){
		return new MusicMouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					e.getPoint();
					int row = rowAtPoint(e.getPoint());
					int col = columnAtPoint(e.getPoint());
					
					if(col == 4) {
						NetSong netSong = SongListTable.getNetSongList().get(row);
						NetSongService netSongService = new NetSongService();
						netSongService.playNetSong(netSong,"");
						netSongService = null;
						netSong = null;
					} else if(col == 5) {
						NetSong netSong = SongListTable.getNetSongList().get(row);
						NetSongService netSongService = new NetSongService();
						netSongService.addNetSongToPlayList(netSong,"");
						netSongService = null;
						netSong = null;
					} else if(col == 6) {
						NetSong netSong = SongListTable.getNetSongList().get(row);
						boolean flag = DownloadSongService.getInstance().existSongByInfo(netSong.getSongName(), netSong.getSinger());
						if(!flag) {
							NetSongService netSongService = new NetSongService();
							netSongService.downloadNetSong(netSong,"");
							netSongService = null;
						} else {
							MusicListLayout.showMsg(Music.getConfigMap().get("search.msg.alert").toString());
						}
						netSong = null;
					}
				}
			}
		};
	}
	
	private MouseMotionListener addMouseMotionListener() {
		return new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int col = columnAtPoint(e.getPoint());
				if(col == 6 || col == 4 || col == 5) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}else {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		};
	}
}
