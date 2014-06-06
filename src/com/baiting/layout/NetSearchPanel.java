package com.baiting.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
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
import com.baiting.ui.SearchButtonUI;
import com.baiting.util.CommonUtil;
import com.baiting.util.StringUtil;

public class NetSearchPanel extends Music{
	
	private JPanel panel,searchPanel,hotPanel,netSongListPanel;
	private SearchButtonUI searchButton;
//	private JTextField inputNameField;
	private JComboBox inputNameField;
	private JLabel songLabel;
	private static NetSearchPanel instance;
	private static Thread searchSongListThread;
	private static String INPUT_DEFAULT_VALUE;
	
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
//		inputNameField.setText(INPUT_DEFAULT_VALUE);
//		inputNameField.setSelectedIndex(-1);
		inputNameField.setForeground(Color.GRAY);
		
		searchButton = new SearchButtonUI();
		searchButton.setOpaque(false);
		searchButton.setFont(Fonts.songTi14());
		searchButton.setForeground(CommonUtil.getColor(getConfigMap().get("net.search.foreground.color").toString(),100));
		searchButton.setText(getConfigMap().get("net.search.label").toString());
		searchButton.setPreferredSize(new Dimension(70, 24));
		
		searchPanel.setLayout(new FlowLayout());
		
		searchPanel.add(inputNameField);
		searchPanel.add(searchButton);
		searchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CommonUtil.getColor(getConfigMap().get("net.panel.border.color").toString(), 100)));
		searchButton.addMouseListener(new MusicMouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					searchSong();
				}
			}
		});
		inputNameField.addKeyListener(new MusicKeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					searchSong();
				}
			}
		});
		/*
		inputNameField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				String value = inputNameField.getText();
				if(StringUtil.isEmpty(value)) {
					inputNameField.setText(INPUT_DEFAULT_VALUE);
					inputNameField.setForeground(Color.GRAY);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				String value = inputNameField.getText();
				if(INPUT_DEFAULT_VALUE.equals(value)) {
					inputNameField.setText("");
					inputNameField.setForeground(Color.BLACK);
				}
			}
		});*/
	}
	
	private boolean searchSong() {
//		String value = inputNameField.getText();
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
//			netSongType = 0;
//			clickSongLabel = 0 ;
			searchSongListThread = new Thread(searchSongList);
			searchSongListThread.start();
			return true ;
		}
		return false ;
	}
	
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
					
//					if(index == xmlSong.getXmlSongItems().size() * xss.size() - 1) //最后一个需要上下各加一条线
//						songLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, CommonUtil.getColor(getConfigMap().get("new.song.label.border.color").toString(), 100)));
//					else  //其余只需上面加一条线
						songLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, CommonUtil.getColor(getConfigMap().get("new.song.label.border.color").toString(), 100)));
					
					if(index == 0){ //默认第一个选中
						songLabel.setOpaque(true);
						songLabel.setBackground(CommonUtil.getColor(getConfigMap().get("new.song.label.background.color").toString(),100));
					}
					
					songLabel.addMouseListener(new MusicMouseListener(){
						@Override
						public void mouseExited(MouseEvent e) {
//							Component now =  e.getComponent();
//							if(now instanceof JLabel){
//								JLabel sLabel = (JLabel)now ;
//								sLabel.setOpaque(false);
//								nameTabPanel.repaint();
//							}
						}
						
						@Override
						public void mouseEntered(MouseEvent e) {
//							Component now = e.getComponent() ;
//							if(now instanceof JLabel){
//								JLabel sLabel = (JLabel)now ;
//								sLabel.setOpaque(true);
//								sLabel.setBackground(CommonUtil.getColor(getConfigMap().get("hot.song.label.selected.background.color").toString(),100));
//								nameTabPanel.repaint();
//							}
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
					});
					nameTabPanel.add(songLabel);
					index ++ ; //序列索引
				}
				//TODO:最好放一分割线
			}
//			clickSongLabel = 1 ;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		hotPanel.setLayout(new BorderLayout(0,0));
		hotPanel.add(hotTitlePanel,BorderLayout.NORTH);
		hotPanel.add(nameTabPanel,BorderLayout.CENTER);
		hotTitlePanel = null;
//		nameTabPanel = null;
	}
	
	private void setNetSongListPanelLayout() {
		NetSongPanel netSongPanel = NetSongPanel.getInstance();
		netSongListPanel.add(netSongPanel.create(),BorderLayout.CENTER);
		netSongPanel = null;
	}
	
}
