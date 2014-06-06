package com.baiting.layout;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

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
//			jcb.setSelected(isSelected);
			return jcb;
		}
		return (Component)value;
	}

}
