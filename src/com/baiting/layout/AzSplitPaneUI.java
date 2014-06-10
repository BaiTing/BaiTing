package com.baiting.layout;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * @author sunshine
 * @version 1.0 24/03/07
 */
public class AzSplitPaneUI extends BasicSplitPaneUI {

    private ImageIcon splitLeftIcon;
    private ImageIcon splitLeftFocusIcon;
    private ImageIcon splitRightIcon;
    private ImageIcon splitRightFocusIcon;

    public void setImageIcons(ImageIcon splitLeftIcon,
            ImageIcon splitLeftFocusIcon, ImageIcon splitRightIcon,
            ImageIcon splitRightFocusIcon) {
        this.splitLeftIcon = splitLeftIcon;
        this.splitLeftFocusIcon = splitLeftFocusIcon;
        this.splitRightIcon = splitRightIcon;
        this.splitRightFocusIcon = splitRightFocusIcon;
    }

    /**
     * Creates a new MetalSplitPaneUI instance
     */
    public static ComponentUI createUI(JComponent x) {
        return new AzSplitPaneUI();
    }
    
    @Override
	public void paint(Graphics g, JComponent jc) {
		// TODO Auto-generated method stub
    	g.setColor(new Color(139, 186, 198));
		super.paint(g, jc);
	}

	/**
     * Creates the default divider.
     */
    @Override
    public BasicSplitPaneDivider createDefaultDivider() {
        return new AzSplitPaneDivider(this, splitLeftIcon, splitLeftFocusIcon,
                splitRightIcon, splitRightFocusIcon);
    }
}
