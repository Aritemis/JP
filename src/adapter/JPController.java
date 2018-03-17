/**
 * @author Ariana Fairbanks
 */

package adapter;

import java.io.File;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.JPSQLiteData;
import view.Frame;

public class JPController 
{
	private JPanel errorPanel;
	private JPSQLiteData database;
	private Frame frame;
	private JPViewStates state;
	
	public void start()
	{
		errorPanel = new JPanel();
		database = new JPSQLiteData(this);
		frame = new Frame(this);
		state = JPViewStates.def;
	}
	
	public void changeState(JPViewStates nextState)
	{
		state = nextState;
		frame.updateState();
	}
	
	public void importUsers(File file)
	{	
		int result = database.importUsers(file);	
		if(result == -1)
		{	JOptionPane.showMessageDialog(errorPanel, "Data successfully imported.", "", JOptionPane.INFORMATION_MESSAGE);	}
		else
		{	JOptionPane.showMessageDialog(errorPanel, "Something went wrong at line " + result + ".", "", JOptionPane.ERROR_MESSAGE);	}
	}
	
	public void clearData()
	{
		database.clearData();
	}

	public ResultSet getData()
	{
		return database.getData();
	}
	
	public JPViewStates getState() 
	{
		return state;
	}
	
	
	
}
