package com.baiting.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.baiting.Music;

//import org.lyj.tlplayer.skin.TTSkinParser;
//import org.lyj.tlplayer.skin.model.PlayerWindowModel;
//import org.lyj.tlplayer.util.ImageUtils;


/**
 * 此类包内友好。只用于滚动条的UI实现
 * 可能的话，可能把它升级为公共类。以供别
 * 的包所使用
 * @author sunshine
 */
public class MyScrollBarUI extends BasicScrollBarUI {

//	private Image btnImg, thumbImg, trackImg;
	private Image topImg, bottomImg, thumbImg, trackImg ;
//    private Image btnImgs[][], thumbImgs[][] ;
    private int x, y, width, height ;
    
    /*
    YOYOScrollBarUI() {
        super();
//        init();
    }
    */
    
    public MyScrollBarUI() { //scroll.track.icon
        super();
//        btnImg = TTSkinParser.getInstance().getImage(pwm.getPositionImage().get(TTSkinParser.SCROLLBAR).getButtons_image());
//        btnImgs = ImageUtils.cut(btnImg, btnImg.getWidth(null)/3+1, btnImg.getHeight(null)/2) ;
        topImg = new ImageIcon(Music.getIconPath() + File.separator + Music.getConfigMap().get("scroll.top.icon").toString()).getImage();

//        thumbImg = TTSkinParser.getInstance().getImage(pwm.getPositionImage().get(TTSkinParser.SCROLLBAR).getThumb_image());
//        thumbImgs = ImageUtils.cut(thumbImg, thumbImg.getWidth(null)/3, thumbImg.getHeight(null)) ;
        thumbImg = new ImageIcon(Music.getIconPath() + File.separator + Music.getConfigMap().get("scroll.bar.icon").toString()).getImage();

        trackImg = new ImageIcon(Music.getIconPath() + File.separator + Music.getConfigMap().get("scroll.track.icon").toString()).getImage();
        
//        trackImg = TTSkinParser.getInstance().getImage(pwm.getPositionImage().get(TTSkinParser.SCROLLBAR).getBar_image());
        bottomImg = new ImageIcon(Music.getIconPath() + File.separator + Music.getConfigMap().get("scroll.bottom.icon").toString()).getImage();
    }
     
    private void init(Rectangle rec) {
//        this.thumbColor = new Color(100, 100, 100);
//        this.thumbDarkShadowColor = new Color(188, 211, 219);
//        this.thumbLightShadowColor = new Color(226, 239, 245);
//        this.thumbHighlightColor = new Color(202, 220, 234);
//        this.trackColor = new Color(48, 67, 86);
//        this.trackHighlightColor = new Color(10, 10, 10);
    	this.x = rec.x ;
    	this.y = rec.y ;
    	this.width = rec.width ;
    	this.height = rec.height ;
    }

    @Override
    public Dimension getMaximumSize(JComponent c) {
        return new Dimension(thumbImg.getWidth(null), thumbImg.getHeight(null));
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(thumbImg.getWidth(null), thumbImg.getHeight(null));
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton jb = new JButton();
        jb.setPreferredSize(new Dimension(topImg.getWidth(null), topImg.getHeight(null)-1));
        jb.setOpaque(false);
        jb.setContentAreaFilled(false);
        jb.setFocusPainted(false);
        jb.setBorderPainted(false);
        jb.setIcon(new ImageIcon(topImg));
        jb.setPressedIcon(new ImageIcon(topImg));
        return jb;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton jb = new JButton();
        jb.setPreferredSize(new Dimension(bottomImg.getWidth(null), bottomImg.getHeight(null)-1));
        jb.setOpaque(false);
        jb.setContentAreaFilled(false);
        jb.setFocusPainted(false);
        jb.setBorderPainted(false);
        jb.setIcon(new ImageIcon(bottomImg));
        jb.setPressedIcon(new ImageIcon(bottomImg));
        return jb;
    }

    @Override
    protected Dimension getMaximumThumbSize() {
//        Dimension di = super.getMaximumThumbSize();
        return new Dimension(thumbImg.getWidth(null)+2, thumbImg.getHeight(null));
    }

    @Override
    protected Dimension getMinimumThumbSize() {
//        Dimension di = super.getMinimumThumbSize();
        return new Dimension(thumbImg.getWidth(null)+2, thumbImg.getHeight(null));
    }

    @Override
    protected void paintThumb(final Graphics g, JComponent c, Rectangle thumbBounds) {
        init(thumbBounds);
        g.drawImage(thumbImg, thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, null) ;
//        super.paintThumb(g, c, thumbBounds);
    }
    
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        init(trackBounds);
//        imgs = ImageUtils.cut(img, img.getWidth(null)/3, img.getHeight(null)) ;
        g.drawImage(trackImg, trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, null) ;
//        super.paintTrack(g, c, trackBounds);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
    }

    @Override
    protected void paintDecreaseHighlight(Graphics g) {
    	g.drawImage(thumbImg, x, y, width, height, null) ;
//        super.paintDecreaseHighlight(g);
    }

    @Override
    protected void paintIncreaseHighlight(Graphics g) {
    	g.drawImage(thumbImg, x, y, width, height, null) ;
//        super.paintIncreaseHighlight(g);
    }
}
