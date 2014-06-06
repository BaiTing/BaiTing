package com.baiting.layout;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class CheckBoxCellEditor extends DefaultCellEditor implements ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6571859238825041136L;
	private JCheckBox checkBox;

	public CheckBoxCellEditor(JCheckBox checkBox) {
		super(checkBox);
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
		checkBox.removeItemListener(this);
		return checkBox;
	}

	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}

}
