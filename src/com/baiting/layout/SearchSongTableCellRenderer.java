package com.baiting.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.baiting.Music;
import com.baiting.font.Fonts;
import com.baiting.util.CommonUtil;

public class SearchSongTableCellRenderer extends MusicTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1377815137771487287L;
	
	public SearchSongTableCellRenderer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		label = new JLabel()
		{  
            private static final long serialVersionUID = 1L;  
  
            protected void paintComponent(Graphics g)  
             {  
                //重载jlabel的paintComponent方法，在这个jlabel里手动画线  
                Graphics2D g2d = (Graphics2D) g;  
                g2d.setColor(new Color(251, 195, 204, 30));  
//                g2d.drawLine(0, 0, this.getWidth(), 0);  
                g2d.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);  
                g2d.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1);  
                  
                //一定要记得调用父类的paintComponent方法，不然它只会划线，不会显示文字  
                super.paintComponent(g);  
             }  
         };
		label.setOpaque(false);
		if (column == 0 || column == 6 || column == 4 || column == 5) {
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setPreferredSize(new Dimension(50, 30));
			if (column == 0) {
				label.setText(" " + (row + 1) + " ");
			} else if(column == 4){
				ImageIcon playIcon2 = new ImageIcon(Music.getIconPath()+Music.getSeparator()+Music.getConfigMap().get("listening.icon").toString());
				label.setIcon(playIcon2);
			} else if(column == 5) {
				ImageIcon playIcon2 = new ImageIcon(Music.getIconPath()+Music.getSeparator()+Music.getConfigMap().get("add.icon").toString());
				label.setIcon(playIcon2);
			} else if(column == 6) {
				ImageIcon playIcon2 = new ImageIcon(Music.getIconPath()+Music.getSeparator()+Music.getConfigMap().get("down.icon").toString());
				label.setIcon(playIcon2);
			} else {
				label.setText(value.toString());
			}
		}  else {
			label.setPreferredSize(new Dimension(100, 30));
			label.setText(" " + value.toString());
		}
		if (isSelected) {
			label.setOpaque(true);
			label.setBackground(new Color(251, 195, 204,30));
			label.setForeground(Color.BLACK);
			label.setFont(Fonts.songTiB12());
			return label;
		} else {
			label.setOpaque(true);
			if (row % 2 == 0) {
				label.setBackground(CommonUtil.getColor(Music.getConfigMap().get("table.cell.renderer.background.color1").toString(),30));
			} else {
				label.setBackground(CommonUtil.getColor(Music.getConfigMap().get("table.cell.renderer.background.color2").toString()));
			}
			label.setFont(Fonts.songTi12());
			return label;
		}
		
	}

	
	
}
