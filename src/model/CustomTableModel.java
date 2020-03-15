/**
 * @author Ariana Fairbanks
 * I made my own table model so I could keep individual cells from being selected.
 */

package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import adapter.JPController;

public class CustomTableModel extends DefaultTableModel 
{
	private static final long serialVersionUID = -1675752138627589999L;
	
    public CustomTableModel(Vector<Vector<Object>> data, Vector<String> columnNames) 
    {	super(data, columnNames);	}
    
	public boolean isCellEditable(int row, int column)
    {	return false;  }

	public static CustomTableModel buildTableModel(ResultSet memberRecords, int value) throws SQLException
	{
		var tableHeader = (value == 0 ? JPController.memberDataTableHeader : JPController.attendanceDataTableHeader);
	    var columnCount = tableHeader.length;
		Vector<String> columnNames = new Vector<>(Arrays.asList(tableHeader).subList(0, columnCount));

	    Vector<Vector<Object>> data = new Vector<>();
	    while (memberRecords.next()) 
	    {
	        Vector<Object> vector = new Vector<>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
	        {	 vector.add(memberRecords.getObject(columnIndex));	}
	        data.add(vector);
	    }
	    return new CustomTableModel(data, columnNames);
	}
}
