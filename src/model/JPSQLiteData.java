/**
 *	@author Ariana Fairbanks
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import adapter.JPController;

public class JPSQLiteData
{
	private static Connection con;
	public static boolean hasData;

	public JPSQLiteData()
	{
		hasData = false;
		getConnection();
		initial();
	}
	
	public void getTotals()
	{
		ResultSet res = getAttendanceData();
		if(res != null)
		{
			try 
			{
				while(res.next())
				{
					JPController.people++;
					JPController.adults  += res.getBoolean("isAnAdult") ? 1 : 0;
					JPController.members += res.getBoolean("isMember")  ? 1 : 0;
					JPController.feasts  += res.getBoolean("hadFeast")  ? 1 : 0;
				}
			} 
			catch (SQLException e) {	e.printStackTrace();	}
		}
	}
	
	public int importMembers(File csvFile)
	{
		//lastName, firstName, SCAName, membershipNumber, expirationDate, isAnAdult
		BufferedReader br = null;
        String line;
        String delimiter = ",";
        int currentLine = 0;
            try 
            {
            	br = new BufferedReader(new FileReader(csvFile));
                while ((line = br.readLine()) != null) 
                {
                	currentLine++;
                    String[] values = line.split(delimiter);
                    int membershipNumber = 0;
                   // System.out.println(values[3]);
                    try
                    {	membershipNumber = Integer.parseInt(values[3]);	}
                    catch(NumberFormatException ignored){ }
                    try
                    {
                        String lastName = values[0].trim();
                        String firstName = values[1].trim();
                        String SCAName = values[2].trim();
                        String expirationDate = values[4].trim();
                        boolean isAnAdult = Boolean.parseBoolean(values[5].trim());
                        if(userExists(firstName, lastName, membershipNumber))
                        {
                			String statement = "DELETE FROM MEMBERDATA WHERE membershipNumber = ? AND firstName = ? AND lastName = ?";
                			PreparedStatement preparedStatement = con.prepareStatement(statement);
                			preparedStatement.setInt(1, membershipNumber);
                			preparedStatement.setString(2, firstName);
                			preparedStatement.setString(3, lastName);
                			preparedStatement.executeUpdate();
                			System.out.println("Updated user " + firstName + " " + lastName + ".");
                        }
                        addMember(lastName, firstName, SCAName, membershipNumber, expirationDate, isAnAdult);
                    }
                    catch(NumberFormatException ignored){ }
                    /*
                     *  Skips the line if this happens because it is almost certainly a header.
                     *  
                     *  If I were being paid or this program were to be used by people other than
                     *  Jerry, then I would have more checks. But that isn't the case so I won't
                     *  worry about it unless I have some down time later.
                     *
                     */
                }
                currentLine = -1;
            } 
            catch (IOException | SQLException e){ e.printStackTrace(); }
            finally 
            {
                if (br != null) 
                {
                    try 
                    { br.close(); }
                    catch (IOException e) 
                    { e.printStackTrace(); }
                }
            }
        return currentLine;
    }
		
	public boolean exportMembers(JTable tableToExport, File newFile)
	{
		boolean result = true;
		try
		{
	        TableModel model = tableToExport.getModel();
	        FileWriter csv = new FileWriter(newFile);
	        String fileContent = "";

	        for (int row = 0; row < model.getRowCount(); row++)
	        {
	            for (int col = 0; col < model.getColumnCount(); col++)
				{
					if(row == 0)
					{ fileContent += JPController.memberDataTableHeader[row] + ","; }
					else
					{ fileContent += model.getValueAt(row, col).toString() + ","; }
				}
	            fileContent += "\n";
	        }
	        csv.write(fileContent);
	        csv.close();
	    } 
		catch (IOException e) 
		{
	        e.printStackTrace();
	        result = false;
	    }
		return result;
	}
	
	public void clearMemberData()
	{
		try 
		{
			String statement = "DELETE FROM MEMBERDATA WHERE ?";
			PreparedStatement preparedStatement = con.prepareStatement(statement);
			preparedStatement.setBoolean(1, true);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {	e.printStackTrace();	}
	}
	
	public ResultSet getMemberData()
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT * FROM MEMBERDATA ORDER BY lastName";
			preparedStatement = con.prepareStatement(query);
			res = preparedStatement.executeQuery();
		}		
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public boolean exportAttendanceData(JTable tableToExport, File newFile)
	{
		boolean result = true;
		try
		{
	        TableModel model = tableToExport.getModel();
	        FileWriter csv = new FileWriter(newFile);
	        String fileContent = "";

			for (int row = 0; row < model.getRowCount(); row++)
			{
				for (int col = 0; col < model.getColumnCount(); col++)
				{
					if(row == 0)
					{ fileContent += JPController.attendanceDataTableHeader[row] + ","; }
					else
					{ fileContent += model.getValueAt(row, col).toString() + ","; }
				}
				fileContent += "\n";
			}
	        csv.write(fileContent);
	        csv.close();
	    } 
		catch (IOException e) 
		{
	        e.printStackTrace();
	        result = false;
	    }
		return result;
	}
	
	public void clearAttendanceData()
	{
		try 
		{
			String statement = "DELETE FROM ATTENDANCEDATA WHERE ?";
			PreparedStatement preparedStatement = con.prepareStatement(statement);
			preparedStatement.setBoolean(1, true);
			preparedStatement.executeUpdate();
			JPController.clearTotals();
		}
		catch (SQLException e) {	e.printStackTrace();	}
	}
	
	public ResultSet getAttendanceData()
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT * FROM ATTENDANCEDATA ORDER BY lastName";
			preparedStatement = con.prepareStatement(query);
			res = preparedStatement.executeQuery();
		}		
		catch (SQLException e){e.printStackTrace();}
		return res;
	}

	public boolean addMember(String firstName, String lastName, String SCAName, int membershipNumber, String expirationDate, boolean isAnAdult)
	{
		boolean result = false;
		if (con == null)
		{	getConnection();	}
		try 
		{
			if(recordExists(lastName, firstName, 0))
			{	JOptionPane.showMessageDialog(JPController.errorPanel, "That person is already a member.", "Error", JOptionPane.ERROR_MESSAGE);	}
			else
			{
				PreparedStatement preparedStatement;
				preparedStatement = con.prepareStatement("INSERT INTO MEMBERDATA VALUES( ?, ?, ?, ?, ?, ?);");
				preparedStatement.setString(1, firstName);
				preparedStatement.setString(2, lastName);
				preparedStatement.setString(3, SCAName);
				preparedStatement.setInt(4, membershipNumber);
				preparedStatement.setString(5, expirationDate);
				preparedStatement.setBoolean(6, isAnAdult);
				preparedStatement.execute();
				result = true;
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(JPController.errorPanel, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}
	
	public boolean deleteMember(String lastName, String firstName)
	{
		boolean result = false;
		try 
		{
			if(recordExists(lastName, firstName, 0))
			{
				String statement = "DELETE FROM MEMBERDATA WHERE lastName = ? AND firstName = ?";
				PreparedStatement preparedStatement = con.prepareStatement(statement);
				preparedStatement.setString(1, lastName);
				preparedStatement.setString(2, firstName);
				preparedStatement.executeUpdate();
				JPController.clearTotals();
				getTotals();
				result = true;
			}
			else
			{	JOptionPane.showMessageDialog(JPController.errorPanel, "No such member.", "Error", JOptionPane.ERROR_MESSAGE);	}
		}
		catch (SQLException e) {	e.printStackTrace();	}
		return result;
	}
	
	public boolean addAttendee(String lastName, String firstName, boolean isAnAdult, boolean hadFeast)
	{
		boolean result = false;
		if (con == null)
		{	getConnection();	}
		try 
		{
			if(recordExists(lastName, firstName, 1))
			{	JOptionPane.showMessageDialog(JPController.errorPanel, "That person is already in attendance.", "Error", JOptionPane.ERROR_MESSAGE);	}
			else
			{
				boolean isMember = recordExists(lastName, firstName, 0);
				PreparedStatement preparedStatement;
				preparedStatement = con.prepareStatement("INSERT INTO ATTENDANCEDATA VALUES( ?, ?, ?, ?, ?);");
				preparedStatement.setString(1, lastName);
				preparedStatement.setString(2, firstName);
				preparedStatement.setBoolean(3, isAnAdult);
				preparedStatement.setBoolean(4, isMember);
				preparedStatement.setBoolean(5, hadFeast);
				preparedStatement.execute();
				JPController.people++;
				if(isAnAdult)
				{	JPController.adults++;	}
				if(isMember)
				{	JPController.members++;	}
				if(hadFeast)
				{	JPController.feasts++;	}
				result = true;
			}
		} 
		catch (SQLException e)
		{	
			e.printStackTrace();	
			JOptionPane.showMessageDialog(JPController.errorPanel, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}
	
	public boolean deleteAttendee(String lastName, String firstName)
	{
		boolean result = false;
		try 
		{
			if(recordExists(lastName, firstName, 1))
			{
				String statement = "DELETE FROM ATTENDANCEDATA WHERE lastName = ? AND firstName = ?";
				PreparedStatement preparedStatement = con.prepareStatement(statement);
				preparedStatement.setString(1, lastName);
				preparedStatement.setString(2, firstName);
				preparedStatement.executeUpdate();
				JPController.clearTotals();
				getTotals();
				result = true;
			}
			else
			{	JOptionPane.showMessageDialog(JPController.errorPanel, "No such person in attendance.", "Error", JOptionPane.ERROR_MESSAGE);	}
		}
		catch (SQLException e) {	e.printStackTrace();	}
		return result;
	}
	
	private boolean userExists(String firstName, String lastName, int membershipNumber)
	{
		boolean result = false;
		ResultSet res;
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT membershipNumber FROM MEMBERDATA WHERE membershipNumber = ? AND firstName = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, membershipNumber);
			preparedStatement.setString(2, firstName);
			res = preparedStatement.executeQuery();
			result = res.next();
		}
		catch (SQLException ignored){}
		return result;
	}
	
	private boolean recordExists(String lastName, String firstName, int whichTable)
	{
		boolean result = false;
		ResultSet res;
		String table = "ATTENDANCEDATA";
		try
		{
			if (con == null)
			{	getConnection();	}
			if(whichTable == 0)
			{	table = "MEMBERDATA";	}
			String query = "SELECT * FROM " + table + " WHERE lastName = ? AND firstName = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, lastName);
			preparedStatement.setString(2, firstName);
			res = preparedStatement.executeQuery();
			result = res.next();
		}
		catch (SQLException ignored){}
		return result;
	}

	private void getConnection()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			String databaseFilePath = "jdbc:sqlite:C:/ProgramData/JProject";
			con = DriverManager.getConnection(databaseFilePath);
		}
		catch (SQLException | ClassNotFoundException e)
		{
			//TODO Mac?
			try			
			{
				Class.forName("org.sqlite.JDBC");
				File homedir = new File(System.getProperty("user.home"));
				String databaseFilePath = "jdbc:sqlite:" + homedir + "/JProject";
				con = DriverManager.getConnection(databaseFilePath);
			}
			catch (SQLException | ClassNotFoundException e2)
			{
				e2.printStackTrace();
				System.out.println("This only works for PC and Linux.");
			}
		}
		initial();
	}

	private void initial() 
	{
		if (!hasData)
		{
			hasData = true;
			Statement state;
			try
			{
				state = con.createStatement();
				ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='MEMBERDATA'");
				if (!res.next())
				{
					state = con.createStatement();
					state.executeUpdate("CREATE TABLE MEMBERDATA(lastName VARCHAR(20)," + "firstName VARCHAR(20)," 
							+ "SCAName VARCHAR(20)," + "membershipNumber INTEGER," + "expirationDate VARCHAR(10)," 
							+ "isAnAdult BOOLEAN,"
							+ "PRIMARY KEY (lastName, firstName));");
					
					state.executeUpdate("CREATE TABLE ATTENDANCEDATA(lastName VARCHAR(20)," + "firstName VARCHAR(20)," 
							+ "isAnAdult BOOLEAN," + "isMember BOOLEAN," + "hadFeast BOOLEAN," 
							+ "PRIMARY KEY (lastName, firstName));");
				}
			}
			catch (SQLException e){ e.printStackTrace(); }
		}
	}
}
