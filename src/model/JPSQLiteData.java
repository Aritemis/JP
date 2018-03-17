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

import javax.swing.JTable;
import javax.swing.table.TableModel;

import adapter.JPController;

public class JPSQLiteData
{
	@SuppressWarnings("unused")
	private JPController base;
	private static Connection con;
	public static boolean hasData;

	public JPSQLiteData(JPController base)
	{
		this.base = base;
		hasData = false;
		getConnection();
		initial();
	}
	
	public ResultSet query(String SQLCommand)
	{
		ResultSet res = null;
		try
		{
			if (con == null)
			{	getConnection();	}
			Statement statement = con.createStatement();
			res = statement.executeQuery(SQLCommand);
		}
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public int importMembers(File csvFile)
	{
		//lastName, firstName, SCAName, membershipNumber, expirationDate, isAnAdult

        BufferedReader br = null;
        String line = "";
        String delimiter = ",";
        int currentLine = 0;

            try 
            {
                br = new BufferedReader(new FileReader(csvFile));
                while ((line = br.readLine()) != null) 
                {
                	currentLine++;
                    String[] values = line.split(delimiter);
                    try
                    {
                        String firstName = values[0].trim();
                        String lastName = values[1].trim();
                        String SCAName = values[2].trim();
                        int membershipNumber = Integer.parseInt(values[3]);
                        String expirationDate = values[4].trim();
                        boolean isAnAdult = Boolean.getBoolean(values[5]);
                        if(userExists(membershipNumber))
                        {
                			String statement = "DELETE FROM MEMBERDATA WHERE membershipNumber = ?";
                			PreparedStatement preparedStatement = con.prepareStatement(statement);
                			preparedStatement.setInt(1, membershipNumber);
                			preparedStatement.executeUpdate();
                			System.out.println("Updated user " + membershipNumber + ".");
                        }
                        addMember(firstName, lastName, SCAName, membershipNumber, expirationDate, isAnAdult);
                    }
                    catch(NumberFormatException e){	}
                    /*
                     *  Skips the line if this happens because it is almost certainly a header.
                     *  
                     *  If this program were to be used by more than one computer literate person I would have more 
                     *  checks, but that isn't the case so I won't worry about it unless I have some down time later.
                     */
                }
                currentLine = -1;
            } 
            catch (IOException | SQLException e){	e.printStackTrace();	} 
            finally 
            {
                if (br != null) 
                {
                    try 
                    {	br.close();	} 
                    catch (IOException e) 
                    {	 e.printStackTrace();	}
                }
            }
        return currentLine;
    }
	
	public boolean exportMembers(JTable tableToExport, File newFile)
	{
		boolean result = true;
		try {

	        TableModel model = tableToExport.getModel();
	        FileWriter csv = new FileWriter(newFile);
	        String fileContent = new String("");

	        for (int i = 0; i < model.getColumnCount(); i++) 
	        {	fileContent = fileContent + JPController.memberDataTableHeader[i] + ",";	}
	        fileContent = fileContent + "\n";

	        for (int i = 0; i < model.getRowCount(); i++) 
	        {
	            for (int j = 0; j < model.getColumnCount(); j++) 
	            {	fileContent = fileContent + model.getValueAt(i, j).toString() + ",";	}
	            fileContent = fileContent + "\n";
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
	
	public void clearData()
	{
		Statement temp;
		try 
		{
			temp = con.createStatement();
			temp.execute("DROP TABLE IF EXISTS USER;");
			initial();
			hasData = false;
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

	public void addMember(String firstName, String lastName, String SCAName, int membershipNumber, String expirationDate, boolean isAnAdult)
	{
		if (con == null)
		{	getConnection();	}
		try 
		{
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement("INSERT INTO MEMBERS VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setString(3, SCAName);
			preparedStatement.setInt(4, membershipNumber);
			preparedStatement.setString(5, expirationDate);
			preparedStatement.setBoolean(6, isAnAdult);
			preparedStatement.execute();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	private boolean userExists(int membershipNumber)
	{
		boolean result = false;
		ResultSet res = null;
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT membershipNumber FROM MEMBERDATA WHERE membershipNumber = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, membershipNumber);
			res = preparedStatement.executeQuery();
			result = res.next();
		}
		catch (SQLException e){}
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
			//TODO maybe make this work for Mac as well???
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
							+ "PRIMARY KEY (membershipNumber));");
					
					state.executeUpdate("CREATE TABLE ATTENDANCEDATA(lastName VARCHAR(20)," + "firstName VARCHAR(20)," 
							+ "isAnAdult BOOLEAN," + "isMember BOOLEAN," + "hadFeast BOOLEAN," 
							+ "PRIMARY KEY (lastName, firstName));");
					
				}
			}
			catch (SQLException e){ e.printStackTrace(); }
		}
	}

}
