package com.baiting.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.baiting.font.Fonts;

public class MusicTableHeaderCellRenderer extends DefaultTableCellRenderer {

/** 
 * 头部渲染器 
 * @author Administrator 
 * 
 */  
    private static final long serialVersionUID = 1L;  
      
    public Component getTableCellRendererComponent(JTable table, Object value,   
            boolean isSelected, boolean hasFocus, int row, int column)  
    {  
        JLabel label = new JLabel()  
        {  
            private static final long serialVersionUID = 1L;  
  
            protected void paintComponent(Graphics g)  
             {  
                //重载jlabel的paintComponent方法，在这个jlabel里手动画线  
                Graphics2D g2d = (Graphics2D) g;  
                g2d.setColor(Color.GRAY);  
//                g2d.drawLine(0, 0, this.getWidth(), 0);  
                g2d.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);  
                g2d.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1);  
                  
                //一定要记得调用父类的paintComponent方法，不然它只会划线，不会显示文字  
                super.paintComponent(g);  
             }  
         };
         label.setOpaque(false);
         label.setFont(Fonts.songTiB14());
         label.setText(value != null ? value.toString() : "unknown");   
         label.setHorizontalAlignment(JLabel.CENTER); 
         label.setVerticalAlignment(JLabel.CENTER);
         return label;   
    }  
      
}