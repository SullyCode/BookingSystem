package util;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.condition.EnabledIfSystemProperties;

import com.mysql.cj.xdevapi.Statement;

import model.Performance;

/**
 * 
 * @author L@S Group
 * date:12-01-2022
 *
 */
public class DBConnector {
	private Connection connection;// connection variable
	ResultSetMetaData rsmd;// declare a ResultSetMetaData variable

	/**
	 * @Constructor
	 * @throws FileNotFoundException
	 */
	public DBConnector() throws FileNotFoundException {
		connection = null;// set the connection to null
	}

	/**
	 * @param connect to the database and handle the exceptions if any
	 * 
	 */
	public void connect() throws FileNotFoundException {

		try {
			// connect to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TheatreRoyal", "root", "Utorok1!");

		} catch (SQLException e) {
			// catch exception,print a message and the actual error
			System.out.println("Connection failure");
			// come out of the method call
			return;
		}
		// if the connection is not null then don't do nothing
		if (connection != null) {

		} else {
			// print a message letting the user know the connection failed
			System.out.println(
					"something went wrong with the Connection. we are working on" + "fixing it,please bear with us!");
		}

	}
	
	
	/**
	 * @param Method that {@link EnabledIfSystemProperties} the admin to connect to the database
	 * using their details.
	 * this was written separately to protect the database 
	 * @throws FileNotFoundException
	 */
    public void adminConnect() throws FileNotFoundException {

    	try {
    		//declare and initialise a scanner object to get the input from the user
    		Scanner scan = new Scanner(System.in);
    		//prompt the user to enter the URL
			System.out.println("please input your URL");
			//get the input from the user and store it as URL
			String url = scan.nextLine();
			//prompt the user to enter the username
			System.out.println("please input your username (this could be 'root')");
			//get the input from the user and store it as username
			String username = scan.nextLine();
			//prompt the user to enter their password
			System.out.println("please input your password");
			//get the input from the user and store it as password
			String password = scan.nextLine();
			//use the input to gain access to the database
			connection = DriverManager.getConnection(url, username, password);
			scan.close();	    		
			
		} catch (SQLException e) {
			//catch exception,print a message and the actual error
			System.out.println("Connection failure");
			//come out of the method call
			return;
		}
    	     //if the connection is not null then 
    	if (connection != null ) {
    		//print to the Admin that they've successfully connected to the database
    		System.out.println("Connection established.");
    	}else {
    		//print a message letting the Admin know the connection failed
			System.out.println("Connection is null.");
		}
    	
}

	/**
	 * @param A method that runs the the SQL query
	 * 
	 * @return Result set
	 */
	public ResultSet runQuery(String sql) {
		// declare PreparedStatement pst and set it to null
		PreparedStatement pst = null;

		try {
			// initialise pst
			pst = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// execute the query
			pst.execute();
			// declare and initialise a ResultSet object
			ResultSet results = pst.getResultSet();
			// if the table isn't empty then
			if (results != null) {
				// declare and set a variable rowCount to be 0
				int rowCount = 0;
				if (results.last()) {
					// if the row is the last row
					rowCount = results.getRow();
					// get the number of that row and set it to be rowCount
					results.beforeFirst();
					// move the cursor back the position before last because the cursor starts from
					// the position thats
					// after the one that it was in
				}

			}
			// return the results and exit the method call
			return results;

			// catch any SQLException
		} catch (SQLException e) {
			// let the user know there was an error

			System.out.println("An error occured please bear with us while we are fixing it");
			// return null and exit the method call
			//return null
			return null;
		}

	}

	/**
	 * @param A method that prints the Resultset
	 * The method takes in one parameter 
	 */
	public void printResults(ResultSet rs) {
		try {
			// declare a ResultSetMetaData object and nmae it rmsd and set it to the result
			// that our resultset holds as data
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			// declare and initialise local variable columnNumber and set it to the number
			// of colums we get from our ResulSetMetadata
			int columnNumber = rsmd.getColumnCount();
			// user friendly printing
	
			for (int i = 1; i <= columnNumber; i++) {
				// go through the columns that the resultset holds and print the names of the
				// columns
			
			}
			java.sql.DatabaseMetaData md = connection.getMetaData();
			//if the resultset has a next row then 

				// while there is another row
			while (rs.next()) {
				// go through all the columns
				for (int i = 1; i <= columnNumber; i++) {
					// declare and set a local variable ColumnValue and set it to be the the value
					// that the name of the column holds
					String ColumnValue = rs.getString(i);
					// print the name od the column and the value it holds
					System.out.println("- " + rsmd.getColumnName(i) + " --> " + ColumnValue + "." + "\t");
					System.out.println("-------");
					System.out.println("");
				}

			}


		}catch (SQLException e) {//handle the exception and 
			// let the user know that there was an error and we are working on it
			System.out.println("An error happened bear with us while we fix it");

		}
	}
	

	/**
	 * @param A method that closes the connection to the database
	 * 
	*/
	public void close() {
		try {
			// close the connection
			connection.close();
			
		} catch (SQLException e) {
			// handle the exception and print to the user that the connection wasn't closed
			System.out.println("An error happened with closing the connection. the team is working hard to fix it..\n"
					+ "please bear with us");
		
		}

	}

}
