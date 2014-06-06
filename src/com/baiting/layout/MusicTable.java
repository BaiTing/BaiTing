package com.baiting.layout;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.baiting.font.Fonts;

public abstract class MusicTable extends JTable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8019332528831298841L;

	public MusicTable() {
		super();
	}
	
	public MusicTable(TableModel dm, TableColumnModel cm) {
		super(dm,cm);
	}
	
	
	public MusicTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm,cm,sm);
	}
	
	public MusicTable(int numRows, int numColumns) {
		super(numRows,numColumns);
	}
	
	@SuppressWarnings("rawtypes")
	public MusicTable(Vector rowData, Vector columnNames) {
		super(rowData,columnNames);
	}
	
	public MusicTable(Object[][] obj,Object[] obj2) {
		super(obj, obj2);
	}
	
	public MusicTable(TableModel dm) {
		super(dm);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return super.isCellEditable(row, column);
	}

	@Override
	public void setTableHeader(JTableHeader tableHeader) {
		tableHeader.setPreferredSize(new Dimension(0, 25));
		tableHeader.setFont(Fonts.songTiB13());
		super.setTableHeader(tableHeader);
	}

	@Override
	public void setCellEditor(TableCellEditor anEditor) {
		super.setCellEditor(anEditor);
	}
	
	@Override
	public void setEditingColumn(int aColumn) {
		// TODO Auto-generated method stub
		super.setEditingColumn(aColumn);
	}


	@Override
	public void setEditingRow(int aRow) {
		// TODO Auto-generated method stub
		super.setEditingRow(aRow);
	}

	@Override
	public JTableHeader getTableHeader() {
		JTableHeader tableHeader = super.getTableHeader(); 
		tableHeader.setOpaque(false);//设置头部为透明  
		tableHeader.getTable().setOpaque(false);//设置头部里面的表格透明 
//		tableHeader.setFont(Fonts.songTiB14());
        //tableHeader.setReorderingAllowed(false);//表格列不可移动  
		/* 
         * 头部的表格也像前面的表格设置一样，还需要将里面的单元项设置为透明 
         * 因此同样需要对头部单元项进行透明度设置，这里还是用渲染器。 
         * 但这里有个问题就是，若将头部渲染器直接像上文一样设置，则它的下面没有横线 
         * 因此，我们需要一个专用的头部渲染器来手动画横线 
         */  
		tableHeader.setDefaultRenderer(new MusicTableHeaderCellRenderer());
		TableCellRenderer headerRenderer = tableHeader.getDefaultRenderer();   
        if (headerRenderer instanceof JLabel)
        {  
            ((JLabel) headerRenderer).setHorizontalAlignment(JLabel.CENTER);
            ((JLabel) headerRenderer).setVerticalAlignment(JLabel.CENTER); 
            ((JLabel) headerRenderer).setOpaque(false);   
        }
        
        return tableHeader;
	}
	
}
