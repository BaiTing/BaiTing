package com.baiting.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.baiting.Music;
import com.baiting.bean.PlayList;
import com.baiting.bean.Song;
import com.baiting.font.Fonts;
import com.baiting.listener.PlayListNameListMouseListener;
import com.baiting.listener.ShowListLabelMouseListener;
import com.baiting.listener.SongListMouseListener;
import com.baiting.service.PlayListService;
import com.baiting.service.SongListService;
import com.baiting.util.CommonUtil;

public class MusicListLayout extends Music {

	private JPanel panel,topPanel,listNamePanel,listLeftPanel,songListPanel;
	private static JPanel showMsgPanel;
	private static JLabel showMsgLabel;
	private JLabel showListLabel;  //播放列表
	private JList songList,playListNameList;
	private static Vector<String> listNameVector,songListVector;
	private static MusicListLayout instance;
	private String listName;
	
	public static MusicListLayout getInstance() {
		if(instance == null) {
			instance = new MusicListLayout();
		}
		return instance;
	}
	
	private MusicListLayout() {
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(MUSIC_WINDOW_RIGHT_WIDTH,getHeight()-(2*MUSIC_WINDOW_BOTTOM_HEIGHT+5)));
		BorderLayout borderLayout = new BorderLayout(0,0);
		panel.setLayout(borderLayout);
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, CommonUtil.getColor(getConfigMap().get("play.show.msg.border.color").toString(), 100)));
		
		topPanel = new JPanel();
		topPanel.setOpaque(false);
		
		listLeftPanel = new JPanel();
		listLeftPanel.setOpaque(false);
		
		songListPanel = new JPanel();
		songListPanel.setOpaque(false);
		
		showMsgPanel = new JPanel();
		showMsgPanel.setOpaque(false);
		showMsgPanel.setBackground(CommonUtil.getColor(getConfigMap().get("play.show.msg.background.color").toString(), 100));
		showMsgPanel.setPreferredSize(new Dimension(getWidth(), MUSIC_WINDOW_TITLE_HEIGHT));
		showMsgPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, CommonUtil.getColor(getConfigMap().get("play.show.msg.border.color").toString(), 100)));
		
		panel.add(topPanel,BorderLayout.NORTH);
		panel.add(listLeftPanel,BorderLayout.WEST);
		panel.add(songListPanel,BorderLayout.CENTER);
		panel.add(showMsgPanel,BorderLayout.SOUTH);
		
	}
	
	
	private void topPanelLayout() {
		showListLabel = new JLabel();
		showListLabel.setText(getConfigMap().get("play.list.label").toString());
		showListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		showListLabel.setOpaque(false);
		showListLabel.setFont(Fonts.songTiB13());
		showListLabel.setPreferredSize(new Dimension(MUSIC_WINDOW_RIGHT_WIDTH, MUSIC_WINDOW_TITLE_HEIGHT));
		topPanel.add(showListLabel);
		topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CommonUtil.getColor(getConfigMap().get("play.top.title.border.color").toString(), 100)));
		
	}
	
	private void middlePanelLayout() {
		//
		BoxLayout boxlayout = new BoxLayout(listLeftPanel, BoxLayout.Y_AXIS);
		listLeftPanel.setLayout(boxlayout);
		/*
		JPanel createPanel = new JPanel();
		createPanel.setOpaque(false);
		
		CreateButtonUI createListButton = new CreateButtonUI();
		createListButton.setText(getConfigMap().get("play.list.create.button.label").toString());
		createListButton.setForeground(CommonUtil.getColor(getConfigMap().get("play.list.create.button.foreground.color").toString(), 100));
		createListButton.setMargin(new Insets(0, 2,0, 2));
		createListButton.setPreferredSize(new Dimension(65,24));
		createListButton.setFont(Fonts.songTi12());
		createListButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		createListButton.addActionListener(new CreateListNameDialog());
		createPanel.add(createListButton);
		*/
		listNamePanel = new JPanel();
		listNamePanel.setLayout(new BorderLayout(0,0));
		listNamePanel.setOpaque(false);
		
		playListNameList  = new JList();
		playListNameList.setFixedCellHeight(25);
		playListNameList.setOpaque(false);
		playListNameList.setFont(Fonts.songTi12());
		
		if(listNameVector == null) {
			listNameVector = new Vector<String>();
		}
//		listNameVector.add(getConfigMap().get("play.list.default.name").toString()); //默认列表
//		listNameVector.add(getConfigMap().get("play.list.lately.name").toString()) ; //最近播放列表
		if(getPlayList() != null) {
			listNameVector.addAll(getPlayList());
		}
		playListNameList.setCellRenderer(new MusicListCellRenderer(true));
		playListNameList.setListData(listNameVector);
		playListNameList.setSelectedIndex(listNameVector.indexOf(getConfigMap().get("play.list.lately.name").toString()));
		if(playListNameList.getCellRenderer() instanceof JLabel)
			((JLabel)playListNameList.getCellRenderer()).setToolTipText(playListNameList.getName());
		listNamePanel.add(playListNameList);

     	listLeftPanel.setPreferredSize(new Dimension(MUSIC_WINDOW_PLAY_LIST_WIDTH, 0));

		listLeftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, CommonUtil.getColor("#d7b1ab", 100))); //播放列表名称
//		listLeftPanel.add(createPanel);
		listLeftPanel.add(listNamePanel);
		
		songListPanel.setLayout(new BorderLayout(0,0));
		songListPanel.setPreferredSize(new Dimension(MUSIC_WINDOW_RIGHT_WIDTH-MUSIC_WINDOW_PLAY_LIST_WIDTH-2,0));

		songList = new JList();
		songList.setFont(Fonts.songTi13());
		songList.setOpaque(false);
		((JLabel)songList.getCellRenderer()).setOpaque(false);
		songListVector = getSongList(playListNameList.getName());
		if(songListVector != null) {
			songList.setListData(songListVector);
		}
		
		songList.setFixedCellHeight(25);
		songList.setAutoscrolls(true); 
		songList.getCellBounds(0, 0);
		MusicListCellRenderer listCellRenderer2 = new MusicListCellRenderer(false);
		listCellRenderer2.setOpaque(false);
		songList.setCellRenderer(listCellRenderer2);
	
		JScrollPane scrollPane = new JScrollPane(songList);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setAutoscrolls(true);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		songListPanel.add(scrollPane,BorderLayout.CENTER);
	}
	
	private void bottomPanelLayout() {
		showMsgLabel = new JLabel();
		showMsgLabel.setText("");
		showMsgLabel.setFont(Fonts.songTi12());
		showMsgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		showMsgPanel.setLayout(new BorderLayout(0, 0));
		showMsgPanel.add(showMsgLabel,BorderLayout.CENTER);
		showMsgPanel.setVisible(false);
	}
	
	private void mouseListener() {
		showListLabel.addMouseListener(new ShowListLabelMouseListener());
		playListNameList.addMouseListener(new PlayListNameListMouseListener(playListNameList, listName,listNamePanel));
		songList.addMouseListener(new SongListMouseListener(playListNameList));
	}
	
	
	public JPanel create() {
		
		topPanelLayout();
		middlePanelLayout();
		bottomPanelLayout();
		mouseListener();
		panel.setOpaque(false);
		return panel;
	}
	
	
	private Vector<String> getPlayList() {
		PlayListService playListService = new PlayListService();
		List<PlayList> lists = playListService.getPlayLists();
		playListService = null;
		if(lists != null  && lists.size()>0) {
			Vector<String> v = new Vector<String>();
			for (Iterator<PlayList> iterator = lists.iterator(); iterator.hasNext();) {
				PlayList playList = iterator.next();
				v.add(playList.getPlayListName());
			}
			lists = null;
			return v;
		}
		lists = null;
		return null;
	}
	
	/**
	 * 获取歌曲列表
	 * @param playListName
	 * @return
	 */
	private Vector<String> getSongList(String playListName) {
		SongListService songListService = new SongListService();
		List<Song> lists = songListService.getSongList(playListName);
		songListService = null;
		if(lists != null && lists.size()>0) {
			Vector<String> v = new Vector<String>();
			for (Iterator<Song> iterator = lists.iterator(); iterator.hasNext();) {
				Song song = iterator.next();
				String value = song.getNo()+"."+song.getName()+"-"+song.getSinger();
				v.add(value);
			}
			lists = null;
			return v;
		}
		lists = null;
		return null;
	}
	
	public static void addRefreshPlayList(String name) {
		if(listNameVector == null) {
			listNameVector = new Vector<String>();
		}
		listNameVector.add(name);
		MusicListLayout.getInstance().getPlayListNameList().setListData(listNameVector);
	}
	
	public static void delRefreshPlayList(String name) {
		listNameVector.remove(name);
		MusicListLayout.getInstance().getPlayListNameList().setListData(listNameVector);
	}
	
	public static void renameRefreshPlayList(String name,String rename) {
		listNameVector.set(getIndexVectorByValue(name), rename);
		MusicListLayout.getInstance().getPlayListNameList().setListData(listNameVector);
	}
	
	public static void refreshSongList(String playListName) {
		Vector<String> list = MusicListLayout.getInstance().getSongList(playListName);
		if(list == null) {
			list = new Vector<String>();
			list.add("");
		}
		MusicListLayout.getInstance().getSongList().setListData(list);
	}
	
	
	private static int getIndexVectorByValue(String name) {
		for(int i=0;i<listNameVector.size();i++) {
			if(listNameVector.get(i).equals(name)) {
				return i;
			}
		}
		return -1;
	}
	
	public JList getPlayListNameList() {
		return playListNameList;
	}


	public void setPlayListNameList(JList playListNameList) {
		this.playListNameList = playListNameList;
	}

	
	public JList getSongList() {
		return songList;
	}

	public void setSongList(JList songList) {
		this.songList = songList;
	}

	public static void showErrorMsg(String msg) {
		showMsgLabel.setForeground(Color.RED);
		showMsgLabel.setText(msg);
		showMsgPanel.setVisible(true);
	}
	
	public static void showMsg(String msg) {
		showMsgLabel.setForeground(Color.BLUE);
		showMsgLabel.setText(msg);
		showMsgPanel.setVisible(true);
	}
	
	public static void hiddenInfo() {
		showMsgLabel.setText("");
		showMsgPanel.setVisible(false);
	}
}
