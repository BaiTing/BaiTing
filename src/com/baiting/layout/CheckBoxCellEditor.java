package com.baiting.layout;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import com.baiting.bean.NetSong;

public class CheckBoxCellEditor extends DefaultCellEditor implements ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6571859238825041136L;
	private JCheckBox checkBox;
	private List<NetSong> selectedSongs;

	public CheckBoxCellEditor(JCheckBox checkBox, List<NetSong> selcetedSongs) {
		super(checkBox);
		this.selectedSongs = selcetedSongs ;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value == null)
			return null;
		
		if(value instanceof JCheckBox){
			checkBox = (JCheckBox) value;
			checkBox.addItemListener(this);
			return checkBox ;
		}
		
		return (Component) value;
	}

	public Object getCellEditorValue() {
//		checkBox.removeItemListener(this);
		return checkBox;
	}

	public void itemStateChanged(ItemEvent e) {
		JCheckBox jcb = (JCheckBox)e.getItem() ;
		if(!jcb.isSelected()) //从列表删除没有选中的
			selectedSongs.remove(Integer.parseInt(jcb.getText().trim())-1) ;
		super.fireEditingStopped();
	}

}
