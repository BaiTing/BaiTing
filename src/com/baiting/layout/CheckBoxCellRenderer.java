package com.baiting.layout;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.baiting.Music;
import com.baiting.font.Fonts;
import com.baiting.util.CommonUtil;

public class CheckBoxCellRenderer implements TableCellRenderer {

	/**
	 * 
	 */
	public CheckBoxCellRenderer() {
		super() ;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		if(null == value)
			return null;
		if(value instanceof JCheckBox){
			JCheckBox jcb = (JCheckBox)value ;
			jcb.setOpaque(false);
			jcb.setText(" " + (row+1) + "");

			if (isSelected) { //选中的样式
				jcb.setOpaque(true);
				jcb.setBackground(new Color(251, 195, 204, 20));
				jcb.setForeground(Color.BLACK);
				jcb.setFont(Fonts.songTiB13());
				return jcb;
			} else {
				jcb.setOpaque(true);
				if (row % 2 == 0) {
					jcb.setBackground(CommonUtil.getColor(Music.getConfigMap().get("table.cell.renderer.background.color1").toString(),20));
				} else {
					jcb.setBackground(CommonUtil.getColor(Music.getConfigMap().get("table.cell.renderer.background.color2").toString(),0));
				}
			}
			return jcb;
		}
		return (Component)value;
	}

}
