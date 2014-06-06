package com.baiting.layout;

import java.util.Vector;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class DownloadTable extends MusicTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7265419917270050586L;

	public DownloadTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DownloadTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		// TODO Auto-generated constructor stub
	}

	public DownloadTable(Object[][] obj, Object[] obj2) {
		super(obj, obj2);
		// TODO Auto-generated constructor stub
	}

	public DownloadTable(TableModel dm, TableColumnModel cm,
			ListSelectionModel sm) {
		super(dm, cm, sm);
		// TODO Auto-generated constructor stub
	}

	public DownloadTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		// TODO Auto-generated constructor stub
	}

	public DownloadTable(TableModel dm) {
		super(dm);
		// TODO Auto-generated constructor stub
	}

	public DownloadTable(Vector<Object> rowData, Vector<Object> columnNames) {
		super(rowData, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false ;
	}

}
