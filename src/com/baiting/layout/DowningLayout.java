package com.baiting.layout;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.baiting.Music;
import com.baiting.bean.DowningNetSong;
import com.baiting.font.Fonts;
import com.baiting.ui.MyScrollBarUI;

public class DowningLayout extends Music {
	
	private static DowningLayout instance;
	private static JTable downingTable;
	private static DefaultTableModel tabelModel;
	private JScrollPane tablePanel;
	private static String[][] datas;
	private String[] titles = getConfigMap().get("downing.table.header").toString().split(",");
	
	private DowningLayout() {
	}
	
	public static DowningLayout getInstance() {
		if(null == instance) {
			instance = new DowningLayout();
		}
		return instance;
	}
	
	private void init() {
		int size = 0;
		if(null != downingNetSongList && downingNetSongList.size()>0) {
			size = downingNetSongList.size();
			datas = new String[size][6];
			for(int i=0;i<downingNetSongList.size();i++) {
				datas[i][0] = downingNetSongList.get(i).getStatus();
				datas[i][1] = downingNetSongList.get(i).getFileName();
				datas[i][2] = downingNetSongList.get(i).getFileSize();
				datas[i][3] = downingNetSongList.get(i).getProgress();
				datas[i][4] = downingNetSongList.get(i).getSpeed();
				datas[i][5] = downingNetSongList.get(i).getRemainTime();
			}
		} else {
			datas = new String[0][6];
		}
		tabelModel = new DefaultTableModel(datas,titles);
		downingTable = new DownloadTable(tabelModel);
		downingTable.setOpaque(false);
		
		downingTable.getTableHeader().setFont(Fonts.songTi13());
		
		downingTable.setShowVerticalLines(false);
		downingTable.setAutoscrolls(true);
		downingTable.setShowHorizontalLines(false);
//		downingTable.getTableHeader().setBackground(CommonUtil.getColor(getConfigMap().get("down.table.header.background.color").toString()));
		downingTable.setDefaultRenderer(Object.class, new MusicTableCellRenderer());
		downingTable.setRowHeight(22);
		downingTable.getColumnModel().getColumn(0).setPreferredWidth(70);
		downingTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		downingTable.getColumnModel().getColumn(2).setPreferredWidth(60);
		
		tablePanel = new JScrollPane();
		tablePanel.setBorder(null);
		tablePanel.getViewport().setOpaque(false); //将JScrollPane设置为透明
		tablePanel.setOpaque(false); //将中间的viewport设置为透明 
		tablePanel.setViewportView(downingTable); //装载表格
		tablePanel.setColumnHeaderView(downingTable.getTableHeader()); //设置头部（HeaderView部分）  
		tablePanel.getColumnHeader().setOpaque(false); //再取出头部，并设置为透明 
		tablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		tablePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tablePanel.setPreferredSize(new Dimension(getWidth()/2+60, getHeight()/2+100));
		tablePanel.getVerticalScrollBar().setUI(new MyScrollBarUI());
	}

	public JScrollPane create() {
		init();
		return tablePanel;
	}

	public static JTable getDowningTable() {
		return downingTable;
	}

	public static void setDowningTable(JTable downingTable) {
		DowningLayout.downingTable = downingTable;
	}
	
	/**
	 * 添加一行数据
	 * @param downingNetSong
	 * @return
	 */
	public static boolean addRows(DowningNetSong downingNetSong) {
		if(null == downingNetSong) {
			return false;
		}
		String[] data = new String[6];
		data[0] = downingNetSong.getStatus();
		data[1] = downingNetSong.getFileName();
		data[2] = downingNetSong.getFileSize();
		data[3] = downingNetSong.getProgress();
		data[4] = downingNetSong.getSpeed();
		data[5] = downingNetSong.getRemainTime();
		if(null != tabelModel) {
			tabelModel.addRow(data);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除一行
	 * @param row
	 * @return
	 */
	public static boolean removeRow(int row) {
		if(null != tabelModel) {
			tabelModel.removeRow(row);
			downingTable.revalidate();
			return true;
		} else {
			return false;
		}
		
	}
}
