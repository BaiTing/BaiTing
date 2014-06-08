package com.baiting.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.baiting.MusicBackgroudPanel;
import com.baiting.MusicFrame;
import com.baiting.MusicWindow;
import com.baiting.util.CommonUtil;
import com.baiting.util.WindowUtils;

public class MusicWindowLayout extends MusicWindow {

	private static MusicFrame frame;
	private JPanel panelTop,panelLeft,panelRight,panelButtom;
	private ShowMainPanel showLRCPanel;

   boolean isDragged = false;
   private MusicBackgroudPanel backgroudPanel;
	
	public MusicWindowLayout() {
		super();
	}
	
	public MusicWindowLayout(String title,int width,int height) {
		super(title, width, height);
	}
	
	public MusicWindowLayout(String title,int width,int height,int locationX,int locationY) {
		super(title, width, height, locationX, locationY);
	}
	
	
	
	public void show() {
		frame = createWindow();
		if(WindowUtils.isWindowAlphaSupported()){
        	//TODO:关键啊！！！可以是播放器呈现透明
//            WindowUtils.setWindowAlpha(frame, 0.9f);
//			WindowUtils.setWindowTransparent(frame, true);
        }
		
		Container contentPane = frame.getContentPane();
		((JPanel)contentPane).setOpaque(false);
		contentPane.setLayout(new BorderLayout(0,0));
		
		backgroudPanel = new MusicBackgroudPanel();
		backgroudPanel.setLayout(new BorderLayout(0,0));
		backgroudPanel.setOpaque(false);
		//contentPane.setFont(Fonts.songTi16());
		
		panelTop = new JPanel();
		panelTop.setOpaque(false);
		
		panelLeft = new JPanel();
		panelLeft.setBackground(Color.WHITE);
		panelLeft.setOpaque(false);
		
		panelRight = new JPanel();
		panelRight.setOpaque(false);
		
		panelButtom = new JPanel();
		panelButtom.setOpaque(false);
		
		initPanel();
		
		backgroudPanel.add(panelTop,BorderLayout.NORTH);
		backgroudPanel.add(panelLeft,BorderLayout.CENTER);
		backgroudPanel.add(panelRight,BorderLayout.WEST);
		backgroudPanel.add(panelButtom,BorderLayout.SOUTH);
		//setDragable();
		contentPane.add(backgroudPanel,BorderLayout.CENTER);
	}
	
	
	private void initPanelTop() {
		panelTop.setPreferredSize(new Dimension(getWidth(), MUSIC_WINDOW_TOP_HEIGHT));
		panelTop.setOpaque(false);
		panelTop.setLayout(new BorderLayout(0,0));
		TabMenuLayout tabMenuLayout = new TabMenuLayout();
		panelTop.add(tabMenuLayout.create(),BorderLayout.CENTER);
		//tabMenuLayout = null;
	}

	private void initPanelLeft() {
		//panelLeft.setBackground(Color.WHITE);
		ShowLyricPanel lrcPanel =  ShowLyricPanel.getInstance();
		showLRCPanel = ShowMainPanel.getInstance(lrcPanel.create());
		//lrcPanel = null;
		panelLeft.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, CommonUtil.getColor(getConfigMap().get("panel.right.border.color").toString(), 100)));
		panelLeft.setLayout(new BorderLayout(0,0));
		panelLeft.add(showLRCPanel.create(),BorderLayout.CENTER);
	}
	
	private void initPanelRight() {
		//panelRight.setBackground(Color.WHITE);
		MusicListLayout musicListLayout = MusicListLayout.getInstance();
		panelRight.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, CommonUtil.getColor(getConfigMap().get("panel.right.border.color").toString(), 100)));
		panelRight.setLayout(new BorderLayout(0,0));
		panelRight.add(musicListLayout.create(),BorderLayout.NORTH);
		//musicListLayout = null;
	}
	
	private void initPanel() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				initPanelTop();
				initPanelLeft();
				initPanelRight();
				initPanelButtom();
				ShowMsgPanel msgPanel = ShowMsgPanel.getInstance();
				NetSongPanel.getInstance().create().add(msgPanel,BorderLayout.CENTER);
			}
		});
	}
	
	private void initPanelButtom() {
		panelButtom.setPreferredSize(new Dimension(getWidth()-10, MUSIC_WINDOW_BOTTOM_HEIGHT));
		panelButtom.setOpaque(false);
		panelButtom.add(MusicPlayControllerLayout.getInstance().create());
	}

	public static MusicFrame getFrame() {
		return frame;
	}

	public static void setFrame(MusicFrame frame) {
		MusicWindowLayout.frame = frame;
	}
	
}