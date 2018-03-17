/**
 *	@author Ariana Fairbanks
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	public ResultSet compareLogin(String userName, String pass)
	{
		ResultSet res = null;
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT ID FROM USER WHERE userName = ? AND pass = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, pass);
			res = preparedStatement.executeQuery();
		}
		catch (SQLException e){}
		return res;
	}
	
	public ResultSet getUserInfo(int id)
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT firstName, lastName, permission, classID FROM USER WHERE ID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
		}		
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public int importUsers(File csvFile)
	{
		//CSV file should have user ID, first name, last name, class ID, permission level, new line.
		//Username default - first letter of first name, first letter of last name, last 3 digits of user ID 
		//Password default - user ID
		
		//Username default subject to change.
		//Duplicate users are ignored.
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
                	//TODO catch invalid input
                    String[] values = line.split(delimiter);
                    String idString = values[0].trim();
                    String firstName = values[1].trim();
                    String lastName = values[2].trim();
                    String classID = values[3].trim();
                    String userName = firstName.substring(0, 1) 
                    				+ lastName.substring(0, 1) 
                    				+ idString.substring(idString.length() - 4);
                    System.out.println(userName);
                    int id = Integer.parseInt(idString);
                    if(userExists(id))
                    {
                    	System.out.println("User with ID " + idString + " already exists.");
                    }
                    else
                    {
                    	addUser(id, userName, idString, firstName, lastName, classID, Integer.parseInt(values[4].trim()));
                    }
                }
                currentLine = -1;
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            } 
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
	
	public ResultSet getData()
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT * FROM USER";
			preparedStatement = con.prepareStatement(query);
			res = preparedStatement.executeQuery();
		}		
		catch (SQLException e){e.printStackTrace();}
		return res;
	}

	private void addUser(int id, String userName, String pass, String firstName, String lastName, String classID, int permissions)
	{
		if (con == null)
		{	getConnection();	}
		try 
		{
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement("INSERT INTO USER VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, userName);
			preparedStatement.setString(3, pass);
			preparedStatement.setString(4, firstName);
			preparedStatement.setString(5, lastName);
			preparedStatement.setString(6, classID);
			preparedStatement.setInt(7, permissions);
			preparedStatement.setInt(8, 0);
			preparedStatement.setBoolean(9, false);
			preparedStatement.execute();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	private boolean userExists(int ID)
	{
		boolean result = false;
		ResultSet res = null;
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT ID FROM USER WHERE ID = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, ID);
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
			//database path
			//if there is no database there a new one will be created
			String databaseFilePath = "jdbc:sqlite:C:/ProgramData/MPDKWID";
			con = DriverManager.getConnection(databaseFilePath);
		}
		catch (SQLException | ClassNotFoundException e)
		{
			//TODO maybe make this work for Mac as well???
			try			
			{
				Class.forName("org.sqlite.JDBC");
				File homedir = new File(System.getProperty("user.home"));
				String databaseFilePath = "jdbc:sqlite:" + homedir + "/MPDKWID";
				con = DriverManager.getConnection(databaseFilePath);
			}
			catch (SQLException | ClassNotFoundException e2)
			{
				e2.printStackTrace();
				System.out.println("linux fix didn't work");
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
				
				// drop table if exists
				//Commented out for demo
				//state.execute("DROP TABLE IF EXISTS USER;");
				
				ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='USER'");
				if (!res.next())
				{
					System.out.println("Building the User table.");
					state = con.createStatement();
					state.executeUpdate("CREATE TABLE USER(ID INTEGER," + "userName VARCHAR(15)," + "pass VARCHAR(15),"
							+ "firstName VARCHAR(30)," + "lastName VARCHAR(30)," + "classID VARCHAR(5),"
							+ "permission INTEGER," + "failedAttempts INTEGER," + "isLocked BOOLEAN,"
							+ "PRIMARY KEY (ID));");
					
					//addUser(000000, "root", "root", "Root", "User", "00", 0);
					//addUser(111111, "deft", "deft", "Default", "Teacher", "1A", 2);
					//addUser(222222, "defs", "defs", "Default", "Student", "1A", 3);
				}
			}
			catch (SQLException e){ e.printStackTrace(); }
		}
	}

}
