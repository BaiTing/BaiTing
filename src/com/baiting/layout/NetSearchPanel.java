package com.baiting.layout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.baiting.Music;
import com.baiting.bean.NetSong;
import com.baiting.bean.netsong.XmlSong;
import com.baiting.bean.netsong.XmlSongItem;
import com.baiting.font.Fonts;
import com.baiting.http.netsong.BaiduSearchSongList;
import com.baiting.http.netsong.NetSongList;
import com.baiting.http.neturl.Dom4jParseNetSongXml;
import com.baiting.listener.MusicKeyListener;
import com.baiting.listener.MusicMouseListener;
import com.baiting.service.DownloadSongService;
import com.baiting.service.NetSongService;
import com.baiting.util.CommonUtil;
import com.baiting.util.StringUtil;

public class NetSearchPanel extends Music{
	
	private JPanel panel,searchPanel,hotPanel,netSongListPanel;
//	private JButton searchButton;
	private JLabel searchButton,playSelected,addSelected2List,downloadSelected;
//	private JTextField inputNameField;
	private JComboBox inputNameField;
	private JLabel songLabel, clickLabel ;
	private static NetSearchPanel instance;
	private static Thread searchSongListThread;
	private static String INPUT_DEFAULT_VALUE;
	private final static int SEARCH = 1;
	private final static int PLAY = 2;
	private final static int ADD = 3;
	private final static int DOWNLOAD = 4;
	
	private NetSearchPanel() {
		init();
	}
	
	private void init() {
		INPUT_DEFAULT_VALUE = getConfigMap().get("search.text.field.default.value").toString();
		if(panel == null) {
			panel = new JPanel();
			panel.setOpaque(false);
			panel.setLayout(new BorderLayout(0,0));
			
			searchPanel = new JPanel();
			searchPanel.setOpaque(false);
			searchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CommonUtil.getColor(getConfigMap().get("net.panel.border.color").toString(), 100)));
			
			hotPanel = new JPanel();
			hotPanel.setOpaque(false);
			hotPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, CommonUtil.getColor(getConfigMap().get("net.panel.border.color").toString(), 100)));
			
			netSongListPanel = new JPanel();
			netSongListPanel.setLayout(new BorderLayout(0,0));
			netSongListPanel.setOpaque(false);
			
			panel.add(searchPanel,BorderLayout.NORTH);
//			panel.add(hotPanel,BorderLayout.WEST);
			panel.add(hotPanel, BorderLayout.EAST);
			panel.add(netSongListPanel,BorderLayout.CENTER);
			
		}
	}
	
	public synchronized static NetSearchPanel getInstance() {
		if(instance == null){
			instance = new NetSearchPanel();
		}
		return instance;
	}
	
	public JPanel create() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setSearchPanelLayout();
				setHotPanelLayout();
				setNetSongListPanelLayout();
			}
		});
		return panel;
	}
	
	public JPanel getPanel() {
		return create();
	}
	
	
	private void setSearchPanelLayout() {
//		inputNameField = new JTextField();
		inputNameField = new JAutoCompleteComboBox() ;
		inputNameField.setOpaque(false);
		inputNameField.setFont(Fonts.songTi14());
		inputNameField.setPreferredSize(new Dimension(getWidth()/3, 25));
		inputNameField.addKeyListener(this.inputFieldKeyListener());
//		inputNameField.setText(INPUT_DEFAULT_VALUE);
//		inputNameField.setSelectedIndex(-1);
//		inputNameField.setForeground(Color.GRAY);
		
//		searchButton = new SearchButtonUI();
		searchButton = new JLabel() ;
//		searchButton.setOpaque(false);
		searchButton.setIcon(new ImageIcon(Music.getIconPath()+Music.getSeparator()+Music.getConfigMap().get("search.icon").toString()));
		searchButton.setFont(Fonts.songTi14());
//		searchButton.setForeground(CommonUtil.getColor(getConfigMap().get("net.search.foreground.color").toString(),100));
		searchButton.setText("  "+getConfigMap().get("net.search.label"));
		searchButton.setPreferredSize(new Dimension(65, 24));
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CommonUtil.getColor(getConfigMap().get("net.panel.border.color").toString(), 100)));
		searchButton.addMouseListener(this.buttonListener(SEARCH));
		
		//playSelectedButton, addSelected2ListButton, downloadSelectedButton;
		playSelected = new JLabel("播放选中") ;
		playSelected.setIcon(new ImageIcon(Music.getIconPath()+Music.getSeparator()+Music.getConfigMap().get("listening.icon").toString()));
		playSelected.setFont(Fonts.songTi14());
		playSelected.setPreferredSize(new Dimension(88, 25));
//		playSelected.setForeground(CommonUtil.getColor(getConfigMap().get("net.search.foreground.color").toString(),100));
		playSelected.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		playSelected.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CommonUtil.getColor(getConfigMap().get("net.panel.border.color").toString(), 100)));
		playSelected.addMouseListener(this.buttonListener(PLAY));
		
		addSelected2List = new JLabel("加入播放");
		addSelected2List.setIcon(new ImageIcon(Music.getIconPath()+Music.getSeparator()+Music.getConfigMap().get("add.icon").toString()));
		addSelected2List.setFont(Fonts.songTi14());
//		addSelected2List.setForeground(CommonUtil.getColor(getConfigMap().get("net.search.foreground.color").toString(),100));
		addSelected2List.setPreferredSize(new Dimension(85, 25));
		addSelected2List.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addSelected2List.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CommonUtil.getColor(getConfigMap().get("net.panel.border.color").toString(), 100)));
		addSelected2List.addMouseListener(this.buttonListener(ADD));
		
		downloadSelected = new JLabel("批量下载");
		downloadSelected.setIcon(new ImageIcon(Music.getIconPath()+Music.getSeparator()+Music.getConfigMap().get("down.icon").toString()));
		downloadSelected.setFont(Fonts.songTi14());
		downloadSelected.setPreferredSize(new Dimension(85, 25));
//		downloadSelected.setForeground(CommonUtil.getColor(getConfigMap().get("net.search.foreground.color").toString(),100));
		downloadSelected.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		downloadSelected.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CommonUtil.getColor(getConfigMap().get("net.panel.border.color").toString(), 100)));
		downloadSelected.addMouseListener(this.buttonListener(DOWNLOAD));
		
		searchPanel.setLayout(new FlowLayout());
		searchPanel.add(inputNameField);
		searchPanel.add(searchButton);
		searchPanel.add(playSelected);
		searchPanel.add(addSelected2List);
		searchPanel.add(downloadSelected);
	}
	
	/**
	 * 搜索按钮鼠标事件
	 * @return
	 */
	private MouseListener buttonListener(final int status){
		return new MusicMouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					boolean flag = false ;
					switch (status) {
						case SEARCH:
							flag = searchSong();
							break;
						case PLAY:
							flag = playSelectedSong();
							break;
						case ADD:
							flag = addSong2List();
							break;
						case DOWNLOAD:
							flag = dowloadSongs();
							break;
						default:
							break;
					}
					if(flag)
						log.info("成功！");
				}
			}
		};
	}
	
	/**
	 * 文本框键盘事件
	 * @return
	 */
	private KeyListener inputFieldKeyListener(){
		return new MusicKeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					searchSong();
				}
			}
		};
	}
	
	/**
	 * 搜索歌曲
	 * @return
	 */
	private boolean searchSong() {
		String value = inputNameField.getEditor().getItem().toString() ;
		if(!StringUtil.isEmpty(value) && !INPUT_DEFAULT_VALUE.equals(value)) {
			String[] cols = value.trim().split(" ");
			NetSong netSong = new NetSong();
			if(cols.length>1) {
				netSong.setSongName(cols[0].trim());
				netSong.setSinger(cols[1].trim());
			} else {
				netSong.setSongName(value.trim());
			}
			BaiduSearchSongList searchSongList = new BaiduSearchSongList(netSong);
			if(null != searchSongListThread) {
				searchSongListThread.interrupt();
			}
			searchSongListThread = new Thread(searchSongList);
			searchSongListThread.start();
			return true ;
		}
		return false ;
	}
	
	/**
	 * 播放选中歌曲
	 * @return
	 */
	private boolean playSelectedSong(){
		try {
			List<NetSong> songs = SongListTable.getSelectedNetSongList() ;
			if(null != songs){
				System.out.println(songs.size());
				
				NetSongService netSongService = new NetSongService();
				int i=0 ;
				for (NetSong netSong : songs) {
					if(i == 0)
						netSongService.playNetSong(netSong,"");
					netSongService.addNetSongToPlayList(netSong,"");
					i++;
				}
				return true ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
		return false ;
	}
	
	/**
	 * 添加选中歌曲到列表
	 * @return
	 */
	private boolean addSong2List(){
		try {
			List<NetSong> songs = SongListTable.getSelectedNetSongList() ;
			if(null != songs){
				System.out.println(songs.size());
				
				NetSongService netSongService = new NetSongService();
				for (NetSong netSong : songs) {
					netSongService.addNetSongToPlayList(netSong,"");
				}
				return true ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
		return false ;
	}
	
	/**
	 * 下载歌曲
	 * @return
	 */
	private boolean dowloadSongs(){
		try {
			List<NetSong> songs = SongListTable.getSelectedNetSongList() ;
			if(null != songs){
				NetSongService netSongService = new NetSongService();
				for (NetSong netSong : songs) {
					boolean flag = DownloadSongService.getInstance().existSongByInfo(netSong.getSongName(), netSong.getSinger());
					if(!flag) {
						netSongService.downloadNetSong(netSong,"");
						netSongService = null;
					} else {
						MusicListLayout.showMsg(Music.getConfigMap().get("search.msg.alert").toString());
					}
				}
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	/**
	 * 在线歌曲标签
	 */
	private void setHotPanelLayout() {
		JPanel hotTitlePanel = new JPanel();
		hotTitlePanel.setOpaque(false);
		hotTitlePanel.setBackground(CommonUtil.getColor(getConfigMap().get("net.hot.title.panel.background.color").toString(),100));
		
		JLabel hotTitleLabel = new JLabel();
		hotTitleLabel.setFont(Fonts.songTiB13());
		hotTitleLabel.setText(getConfigMap().get("net.hot.title.label").toString());
		hotTitleLabel.setPreferredSize(new Dimension(100, 18));
		hotTitleLabel.setOpaque(false);
		hotTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hotTitlePanel.add(hotTitleLabel);
		hotTitlePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, CommonUtil.getColor("#C5948C", 100)));
		
		final JPanel nameTabPanel = new JPanel();
		nameTabPanel.setOpaque(false);
		GridLayout gridLayout = new GridLayout() ;
		
		try {
			List<XmlSong> xss = Dom4jParseNetSongXml.getInstance().getAllSongsWithItems() ;
			int  x = 0 ;
			for (XmlSong xmlSong : xss) {
				x += xmlSong.getXmlSongItems().size() ;
			}

			gridLayout.setRows(x);
			gridLayout.setColumns(1);
			nameTabPanel.setLayout(gridLayout);
			
			int  index=0;
			for (final XmlSong xmlSong : xss) {
				for (final XmlSongItem xsi : xmlSong.getXmlSongItems()) {
					songLabel = new JLabel() ;
					songLabel.setFont(Fonts.songTiB13());
					songLabel.setText(xsi.getName());
					songLabel.setHorizontalAlignment(SwingConstants.CENTER);
					songLabel.setOpaque(false);
//					songLabel.setBackground(CommonUtil.getColor(getConfigMap().get("new.song.label.background.color").toString()));
					songLabel.setForeground(CommonUtil.getColor(getConfigMap().get("new.song.label.foreground.color").toString()));
					songLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, CommonUtil.getColor(getConfigMap().get("new.song.label.border.color").toString(), 100)));
					
					if(index == 0){ //默认第一个选中
						songLabel.setOpaque(true);
						songLabel.setBackground(CommonUtil.getColor(getConfigMap().get("new.song.label.background.color").toString(),100));
						clickLabel = songLabel ;
					}
					//添加鼠标事件
					songLabel.addMouseListener(this.addSongLabelMouseListener(nameTabPanel, xmlSong, xsi));
					nameTabPanel.add(songLabel);
					index ++ ; //序列索引
				}
				//TODO:最好放一分割线
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		hotPanel.setLayout(new BorderLayout(0,0));
		hotPanel.add(hotTitlePanel,BorderLayout.NORTH);
		hotPanel.add(nameTabPanel,BorderLayout.CENTER);
		hotTitlePanel = null;
//		nameTabPanel = null;
	}
	
	/**
	 * 列表标签鼠标事件
	 * @param nameTabPanel
	 * @param xmlSong
	 * @param xsi
	 * @return
	 */
	private MouseListener addSongLabelMouseListener(final JPanel nameTabPanel, final XmlSong xmlSong, final XmlSongItem xsi){
		return new MusicMouseListener(){
			@Override
			public void mouseExited(MouseEvent e) {
				Component now =  e.getComponent();
				if(now instanceof JLabel){
					JLabel sLabel = (JLabel)now ;
					if(sLabel != clickLabel)
						sLabel.setOpaque(false);
					nameTabPanel.repaint();
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				Component now = e.getComponent() ;
				if(now instanceof JLabel){
					JLabel sLabel = (JLabel)now ;
					sLabel.setOpaque(true);
					if(sLabel != clickLabel)
						sLabel.setBackground(CommonUtil.getColor(getConfigMap().get("hot.song.label.selected.background.color").toString(),150));
					nameTabPanel.repaint();
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					Component now =  e.getComponent();
					Component[] components = nameTabPanel.getComponents() ;
					for (int i = 0; i < components.length; i++) {
						if(components[i] == now){
							if(now instanceof JLabel){
								JLabel sLabel = (JLabel)now ;
								clickLabel = sLabel ;
								sLabel.setOpaque(true);
								sLabel.setBackground(CommonUtil.getColor(getConfigMap().get("new.song.label.background.color").toString(),100));
								nameTabPanel.repaint();
							}
						}else{
							if(components[i] instanceof JLabel){
								JLabel sLabel = (JLabel)components[i] ;
								sLabel.setOpaque(false);
								nameTabPanel.repaint();
							}
						}
					}
					
					Object netSongListClassName = getConfigMap().get("net.song.list.class");
					if(null != netSongListClassName && !StringUtil.isEmpty(netSongListClassName.toString())) {
						try {
							NetSongList netSongList = (NetSongList)Class.forName(netSongListClassName.toString()).newInstance();
							netSongList.setXmlSongCode(xmlSong.getCode());
							netSongList.setXmlSongItemName(xsi.getName());
							netSongList.putXmlSong(xmlSong, xsi);
							if(null != searchSongListThread) {
								searchSongListThread.interrupt();
							}
							searchSongListThread = new Thread(netSongList);
							searchSongListThread.start();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		};
	}
	
	private void setNetSongListPanelLayout() {
		NetSongPanel netSongPanel = NetSongPanel.getInstance();
		netSongListPanel.add(netSongPanel.create(),BorderLayout.CENTER);
		netSongPanel = null;
	}
	
}
