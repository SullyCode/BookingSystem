package model;

import java.io.FileNotFoundException;
import java.sql.ResultSet;

import util.DBConnector;

/**
 * @author L@S Group 
 * date: 12-01-2022
 * A class that models a particular performance and
 *  handles everything to do with it
 *
 */
public class Performance {

	private static DBConnector db;// declare a DBConnector object
	private String title;// declare a variable title
	private String type;// can only be theatre, concert, musical,opera
	private String description;// declare a variable description
	private String language;// declare a variable language
	private boolean liveMusic;// boolean variable (yes or no)
	private int durationInMinutes; // declare a variable durationInMinutes
	private double stallPrice;// declare a variable stallPrice
	private double circlePrice;// declare a variable circlePrice

	/**
	 * @constructor that takes two parameters
	 * @param stallPrice
	 * @param circlePrice
	 * @throws FileNotFoundException
	 */
	public Performance(double stallPrice, double circlePrice) throws FileNotFoundException {
		db = new DBConnector();// initialise the DBConnector object
		this.stallPrice = stallPrice;// initialise the stallPrice variable
		this.circlePrice = circlePrice;// initialise the circlePrice variable
	}

	/**
	 * @constructor
	 * @throws FileNotFoundException
	 */
	public Performance() throws FileNotFoundException {
		db = new DBConnector();// initialise the DBConnector object
	}

	/**
	 * @constructor
	 * @param title
	 * @throws FileNotFoundException
	 */
	public Performance(String title) throws FileNotFoundException {
		this.title = title;// initialise the title variable
		db = new DBConnector();// initialise the DBConnector object
	}
	
	/**
	 * @constructor
	 * @param pTitle
	 * @param pType
	 * @throws FileNotFoundException
	 */
	public Performance(String pTitle, String pType) throws FileNotFoundException {
		db = new DBConnector();// initialise the DBConnector object
		this.title = pTitle;// initialise the title variable
		this.type = pType;// initialise the type variable
	}

	/**
	 * @constructor
	 * @param title
	 * @param type
	 * @param description
	 * @param language
	 * @param liveMusic
	 * @param durationInMinutes
	 * @param stallPrice
	 * @param circlePrice
	 * @throws FileNotFoundException
	 */
	public Performance(String title, String type, String description, String language, boolean liveMusic,
			int durationInMinutes, double stallPrice, double circlePrice) throws FileNotFoundException {

		db = new DBConnector();// initialise the DBConnector object
		this.title = title;// initialise the title variable
		this.type = type;// initialise the type variable
		this.description = description;// initialise the description variable
		this.language = language;// initialise the language variable
		this.liveMusic = liveMusic;// initialise the liveMusic variable
		this.durationInMinutes = durationInMinutes;// initialise the durationInMinutes variable
		this.stallPrice = stallPrice;// initialise the stallPrice variable
		this.circlePrice = circlePrice;// initialise the circlePrice variable
	}
	
	/**
	 * A method that gets called by the admin whenever they want to delete a Performance
	 * @param title
	 */
	public void deletePerformance(String title) {
		try {//connect to the database
			db.connect();
			//build an sql String
			String sql = "DELETE FROM Performance WHERE Title = '" + title + "';";
			//run it
			ResultSet rs = db.runQuery(sql);

			db.close();
		} catch (Exception e) {
			//handle the exception
			System.out.println("An error happened while we tried to delete the Performance.\n"
					+ "we are working hard to fix it please bear with us ");
		}//close the connection
		

	}
	
	
	
	
	/**
	 * @param A method to change the stall price of a specific performance 
	 * it takes in two parameters  title and newPrice 
	 * 
	 */
	public void changeStallPrice(String title, double newPrice) {
		
		try {//connect to the database
			db.connect();
			//build an sql String
			String sql = "UPDATE Performance set StallPrice = " + newPrice + " where title = '"+ title + "';";
			//run it
			ResultSet rs = db.runQuery(sql);
			//close the connection
			db.close();
		} catch (Exception e) {
			//handle the exception
			e.printStackTrace();
		}
		

	}
	
	/**
	 * @param A method to change the stall price of a specific performance
	 * it takes in two parameters  title and newPrice 
	 * 
	 */
	public void changeCirclePrice(String title, double newPrice) {
		
		try {//connect to the database
			db.connect();
			//build an sql String
			String sql = "UPDATE Performance set CirclePrice = " + newPrice + " where title = '"+ title + "';";
			//run it
			ResultSet rs = db.runQuery(sql);
			//close the connection
			db.close();
		} catch (Exception e) {
			//handle the exception
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * A method that gets all the performances in the database
	 */
	public void getPerformances() {

		try {
			//connect to the database
			db.connect();
			//build an sql String
			String sql = "SELECT  title, performanceType FROM Performance ;";
			//run it
			ResultSet rs = db.runQuery(sql);
			//print what's all the performances 
			db.printResults(rs);

			//close the connection
			db.close();
		} catch (Exception e) {
			//handle the exception and print to the client 
			System.out.println("something went wrong while getting the performances available\n"
					+ "we are working very hard to fix it please bear with us");
		}

	}

	/**
	 * @param A method gets the Circle Price of a certain performance 
	 * @returns circlePrice
	 */
	public double getCirclePrice(String title) {//THIS SHOULD BE FOR A CERTAIN TITLE
		// need to get this from the database
		try {//connect to the database
			db.connect();
			//build an sql String
			String sql = "SELECT  CirclePrice FROM Performance WHERE  title = '"+ title+ "'" ;
			//run it
			ResultSet rs = db.runQuery(sql);
			
			//declare and initialise ResultSetMetaData object
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			//declare and initialise a variable columnNumber
			int columnNumber = rsmd.getColumnCount();
			//while there is another row
			while (rs.next()) {
				// go through all the columns
				for (int i = 1; i <= columnNumber; i++) {
					// declare and set a local variable ColumnValue and set it to be the the value
					// that the name of the column holds
					int ColumnValue = rs.getInt(i);
					circlePrice = ColumnValue;
				}
			}

			//close the connection
			db.close();
		} catch (Exception e) {
			//handle the exception and print to the client in case there is an error
			System.out.println("an error occured while getting the circle ticket price \n"
					+ "we are working very hard to fix it. please bear with us");
			e.printStackTrace();//SHOULD BE DELETED BEFORE SUBMISSION
		}

		// System.out.println(circlePrice);
		//return the circle ticket price
		return circlePrice;
	}

	
	
	
	
	/**
	 * A method that calculates the concessionary stall ticket price for the specified performance
	 * (considering the fact that the performance price(stall or circle) can vary at one point in the future)
	 * this method adapts to that
	 * @param title
	 * @returns stallConcessionaryPrice after the calculation
	 */
	public double getStallConcessionaryPrice(String title) {
		//calculate how much the price will be after taking 25% off
		double stallConcessionaryPrice = getCirclePrice(title) / 4.00 * 3.00;
		//return the adapted stall concessionary price
		return stallConcessionaryPrice;
	}
	
	
	
	
	
	/**
	 * A method that calculates the concessionary ticket price for the specified performance
	 * (considering the fact that the performance price(stall or circle) can vary at one point in the future)
	 * this method adapts to that
	 * @param title
	 * @returns circleConcessionaryPrice after the calculation
	 */
	public double getCircleConcessionaryPrice(String title) {
		//calculate how much the price will be after taking 25% off
		double circleConcessionaryPrice = getCirclePrice(title) / 4.00 * 3.00;
		//return the adapted circle concessionary price
		return circleConcessionaryPrice;
	}
	
	
	
	
	

	/**
	 * A method that gets the price of Stall seats at a particular Performance
	 * @returns stallPrice
	 */
	public double getStallPrice(String title) {//THESE TWO METHODS COULD BE IMPROVED BUT 
		                                        //IF NO TIME ITS FINE 
		
		// needs to get this info from the database
		try {//connect to the database
			db.connect();
			//build an sql String
			String sql = "SELECT  stallPrice FROM Performance WHERE  title = '"+ title+ "'" ;
			//run it
			ResultSet rs = db.runQuery(sql);
			//declare and initialise ResultSetMetaData object
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			//declare and initialise a variable columnNumber
			int columnNumber = rsmd.getColumnCount();
			//while there is another row
			while (rs.next()) {
				// go through all the columns
				for (int i = 1; i <= columnNumber; i++) {
					// declare and set a local variable ColumnValue and set it to be the the value
					// that the name of the column holds
					int ColumnValue = rs.getInt(i);
					//assign ColumnValue to stallPrice
					stallPrice = ColumnValue;
					
				}
			}

			//close the connection
			db.close();
		} catch (Exception e) {
			//handle the exception and print to the client in case there is an error
			System.out.println("an error occured while getting the stall ticket price \n"
					+ "we are working very hard to fix it. please bear with us");
			
		}
		//return the price of the stall ticket
		return stallPrice;
	}

	
	
	/**
	 * A method that gets the title of the performance
	 * @returns title
	 */
	public String getTitle() {
		return title;
	}
	
	
	/**
	 * A method that sets 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	

	/**
	 * @param A method that gets the Type of the performance 
	 * @returns type 
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param A method that gets the description of the performance 
	 * @returns description
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param A method that gets the language of the performance 
	 * @returns language
	 */
	public String getLanguage() {
		return language;
	}


	/**
	 * @param A boolean method that checks the value of liveMusic
	 * @returns liveMusic
	 */
	public boolean isLiveMusic() {
		return liveMusic;
	}


	/**
	 * @param A method that gets the gets the Duration of the performance In Minutes 
	 * @returns language
	 */
	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	

	@Override
	public String toString() {
		return "* Performance title => " + title + "  * type => " + type + "\n" + "* description => " + description
				+ "  * language => " + language + "\n" + "* liveMusic => " + liveMusic + "  * durationInMinutes => "
				+ durationInMinutes + "\n" + "* stallPrice => " + stallPrice + "  * circlePrice => " + circlePrice;
	}

}