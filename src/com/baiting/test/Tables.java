package com.baiting.test;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Tables implements MouseListener {

	public Tables() {
		JFrame frame = new JFrame("sjh");
		frame.setLayout(null);

		JTable table = this.gettable();
		table.addMouseListener(this);
		JScrollPane src = new JScrollPane(table);
		src.setBounds(0, 0, 400, 200);
		frame.setSize(new Dimension(400, 200));
		frame.add(src);
		frame.setVisible(true);
	}

	public JTable gettable() {
		JCheckBox cb = new JCheckBox("111");
		cb.setSelected(true);
		Object[][] datas = new Object[][] {
				{ cb, new JCheckBox("111"),
					new JCheckBox("111"), new JCheckBox("111"),
					new JCheckBox("111"), new JCheckBox("111") },
			{ new JCheckBox("222"), new JCheckBox("222"),
					new JCheckBox("222"), new JCheckBox("222"),
					new JCheckBox("222"), new JCheckBox("222") },
			{ new JCheckBox("333"), new JCheckBox("333"),
					new JCheckBox("333"), new JCheckBox("333"),
					new JCheckBox("333"), new JCheckBox("333") }, } ;
		Object[] header = new Object[] { "选择", "结果物", "说明", "类型", "发包要求文档", "操作" } ;
		
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(datas, header);

		JTable table = new JTable(dm) 
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = -1484887637540996963L;

			public void tableChanged(TableModelEvent e) {
				super.tableChanged(e);
				repaint();
			}
		};
		
		for (Object object : header) {
			table.getColumn(object).setCellEditor(new CheckBoxEditor(new JCheckBox()));
		}

		table.setDefaultRenderer(Object.class, new CheckBoxRenderer());

		return table;
	}

	public static void main(String args[]) {
		new Tables();
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}
}

@SuppressWarnings("serial")
class CheckBoxEditor extends DefaultCellEditor implements ItemListener {
	private JCheckBox button;

	public CheckBoxEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value == null)
			return null;
		button = (JCheckBox) value;
		button.addItemListener(this);
		return (Component) value;
	}

	public Object getCellEditorValue() {
		button.removeItemListener(this);
		return button;
	}

	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}
}

class CheckBoxRenderer implements TableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null)
			return null;
//		if(value instanceof JCheckBox){
//			JCheckBox jcb = (JCheckBox)value ;
//			jcb.setOpaque(false);
//			jcb.setText(" " + (row+1) + "");
////			jcb.setSelected(isSelected);
//			return jcb;
//		}
		return (Component) value;
	}
}
