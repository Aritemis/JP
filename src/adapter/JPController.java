/**
 * @author Ariana Fairbanks
 */

package adapter;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.JPSQLiteData;
import view.Frame;

public class JPController 
{
	public static JPanel errorPanel;
	public static String[] memberDataTableHeader = {"Last Name", "First Name", "SCA Name", "Membership #", "Expiration Date", "Is An Adult"};
	public static String[] attendanceDataTableHeader = {"Last Name", "First Name", "Is An Adult", "Is A Member", "Attended Feast"};
	private JPSQLiteData database;
	private Frame frame;
	private JPViewStates state;
	
	public void start()
	{
		errorPanel = new JPanel();
		database = new JPSQLiteData(this);
		frame = new Frame(this);
		state = JPViewStates.DEFAULT;
	}
	
	public void changeState(JPViewStates nextState)
	{
		state = nextState;
		frame.updateState(state);
	}
	
	public void importMembers(File file)
	{	
		int result = database.importMembers(file);	
		if(result == -1)
		{	JOptionPane.showMessageDialog(errorPanel, "Member data successfully imported.", "", JOptionPane.INFORMATION_MESSAGE);	}
		else
		{	JOptionPane.showMessageDialog(errorPanel, "Something went wrong at line " + result + ".", "", JOptionPane.ERROR_MESSAGE);	}
	}
	
	public void exportMembers(JTable table, File file)
	{
		boolean result = database.exportMembers(table, file);	
		if(result)
		{	JOptionPane.showMessageDialog(errorPanel, "Member data successfully exported.", "", JOptionPane.INFORMATION_MESSAGE);	}
		else
		{	JOptionPane.showMessageDialog(errorPanel, "Something went wrong at line " + result + ".", "", JOptionPane.ERROR_MESSAGE);	}
	}
	
	public void clearMemberData()
	{	database.clearMemberData();	}
	
	public ResultSet getMemberData()
	{	return database.getMemberData();	}
	
	public void clearAttendaceData()
	{	database.clearAttendanceData();	}
	
	public ResultSet getAttendanceData()
	{	return database.getAttendanceData();	}
		
	public void exportAttendaceData(JTable table, File file)
	{
		boolean result = database.exportAttendanceData(table, file);
		if(result)
		{	JOptionPane.showMessageDialog(errorPanel, "Attendace data successfully exported.", "", JOptionPane.INFORMATION_MESSAGE);	}
		else
		{	JOptionPane.showMessageDialog(errorPanel, "Something went wrong at line " + result + ".", "", JOptionPane.ERROR_MESSAGE);	}
	}
	
	public static DefaultTableModel buildTableModel(ResultSet memberRecords, int value) throws SQLException 
	{
		ResultSetMetaData metaData = memberRecords.getMetaData();

	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 0; column < columnCount; column++) 
	    {	
	    	if(value == 0)
	    	{	columnNames.add(JPController.memberDataTableHeader[column]);	}
	    	else
	    	{	columnNames.add(JPController.attendanceDataTableHeader[column]);	}
	    }

	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (memberRecords.next()) 
	    {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
	        {
	            vector.add(memberRecords.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    return new DefaultTableModel(data, columnNames);
	}
	
}
