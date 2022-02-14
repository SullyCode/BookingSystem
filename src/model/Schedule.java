package model;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import util.DBConnector;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 
 * @author L@S Group
 * date:12-01-2022
 *
 *@param A class that models the schedule of a particular performance on a certain date and time
 */

public class Schedule {

	private int PerformanceID;//the ID number of the performance
	private LocalTime showTime;//declare a LocalTime object 
	private Date showDate;//declare a Date object 
	private int noStallTickets;//
	private int noCircleTickets;
	private int totalNoTicketsSold;//the total number of tickets sold 
	private int noStallTicketsSold;//the number of Stall Tickets Sold 
	private int noCircleTicketsSold;//the number of Circle Tickets Sold 
	private ArrayList<Performance> performances;//declare an ArrayList of type Performance
	private int maxStallTickets;//the maximum number of stall tickets 
	private int maxCircleTickets;//the maximum number of circle tickets  
	private int noCircleBasket;//the number of circle tickets in the basket 
	private int noStallBasket;//the number of stall tickets in the basket
	private static DBConnector db;//declare a DBConnector object 

	
	
	/**
	 * @constructor
	 * @throws FileNotFoundException
	 */
	public Schedule() throws FileNotFoundException {
		db = new DBConnector();//initialise the connector
	}
	
	/**
	 * @Constructor
	 * @param constructor with two parameters
	 * @throws FileNotFoundException 
	 */
	public Schedule(int noStallTickets, int noCircleTickets) throws FileNotFoundException  {
	
		this.noStallTickets = noStallTickets;//the number of stall tickets available
		this.noCircleTickets = noCircleTickets ;//the number of circle tickets available
		this.totalNoTicketsSold = 0;//we haven't sold any tickets yet
		this.noStallTicketsSold = 0;//we haven't sold any stall tickets yet
		this.noCircleTicketsSold = 0 ;//we haven't sold any circle tickets yet
		this.maxStallTickets = 120;//set the maximum Stall Tickets to 120 
		this.maxCircleTickets = 80;//set the maximum Circle Tickets to 80 
		noCircleBasket = 0;//set the noCircleBasket to 0
		noStallBasket = 0;//set the noStallBasket to 0
		performances = new ArrayList<Performance>();//initialise the list
		db = new DBConnector();//initialise the connector
	}
	
	/**
	 *@param A method that updates the number of Stall tickets for a certain schedule
	 * the method takes 2 parameters noStallTickets and ID
	 * 
	 */            
	public void updateNoStallTickets(int noStallTickets, int ID) throws FileNotFoundException {
		try {//connect to the database
			db.connect();
			//if we still have stall tickets then 
			if (noStallTickets <= getMaxStallTickets() && noStallTickets >= 0) {
				//build a query string
				String sql = "UPDATE PSchedule set NoStallTickets = "+ noStallTickets +" WHERE ID = " + ID + ";";
				//run the query
				ResultSet rs = db.runQuery(sql);
				//close the connection
				db.close();
			}else {
				System.out.println("the number of stall tickets has to be less than what was specified");
			}
		
		} catch (Exception e) {
			System.out.println("An error happened while we were updating the number of stall tickets.\n"
					+ "Please bear with us while we are fixing it.");
		}
		    this.PerformanceID = ID;//set the id to be the performance ID
			this.noStallTickets = noStallTickets;//update the number of stall tickets
			
	}

	
	
	
	/**
	 * @param A method that gets the number of stall tickets available for a specific performance at a 
	 * specific time and date(using the ID as a parameter)
	 * 
	 */
	public int getNoAvailableStallTickets(int ID)  throws FileNotFoundException{
		try {
			//connect to the database
			db.connect();
			//build the Sql query string
			String sql = "SELECT  NoStallTickets FROM PSchedule WHERE ID =" + ID + ";";
			//run the query
			ResultSet rs = db.runQuery(sql);
			//declare and initialise a ResultSetMetaData object
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			//declare and initialise a local variable columnNumber
			int columnNumber = rsmd.getColumnCount();
			//while the resultset has more rows
			while(rs.next()) {
				//go through all the columns
				for(int i =1; i <= columnNumber;i++) {
					//declare and set a local variable ColumnValue and set it 
					//to be the the value that the  name of the column holds
					int ColumnValue = rs.getInt(i);
					//set the noStallTickets to column value
					noStallTickets = ColumnValue;
					
				}
			}
			//close the connection
			db.close();
			
		} catch (Exception e) {
			
		}
		//set the performanceID to ID
		this.PerformanceID = ID;
		System.out.println("------------------------------------------");
		System.out.println("The number of stall ticket(s) available is " + noStallTickets);
		System.out.println("------------------------------------------");		
		//return the number of stall tickets
		return noStallTickets;
	}
	

	
	
	
	/**
	 * @param A method that gets the number of stall tickets available for a specific performance at a
	 * specific time and date(using the ID as a parameter)
	 */
	public int getNoAvailableCircleTickets(int ID) {
	
		try {
			
			//connect to the database
			db.connect();
			//build an SQL String
			String sql = "SELECT  NoCircleTickets FROM PSchedule WHERE ID =" + ID + ";";
			//run the query 
			ResultSet rs = db.runQuery(sql);
			//db.printResults(rs);
			//declare a ResultSetMetaData object 
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			//get the number of columns and assign it to columnNumber 
			int columnNumber = rsmd.getColumnCount();
			//while there is another row
			while(rs.next()) {
				//go through all the columns
				for(int i =1; i <= columnNumber;i++) {
					//declare and set a local variable ColumnValue and set it to be the the value that the  name of the column holds
					int ColumnValue = rs.getInt(i);
					//set noCircleTickets to be ColumnValue 
					noCircleTickets = ColumnValue;
					//print the number of circle tickets
					//System.out.println(noCircleTickets);
				}
			}
			
		} catch (Exception e) {
			System.out.println("An error happened while getting the number of available circle tickets.\n"
					+ "Please bear with us while we are working on it");
		}//set PerformanceID to be ID
		this.PerformanceID = ID;
		//print how many Circle tickets are available
		System.out.println("------------------------------------------");
		System.out.println("The number of circle ticket(s) available is "+noCircleTickets);
		System.out.println("------------------------------------------");
		//return noCircleTickets 
		return noCircleTickets;
	}
	
	
	
	
	
	/**
	 * @param A method thats sets the number of circle tickets 
	 * The method takes 2 parameters noCircleTickets and ID
	 */
	public void setNoCircleTickets(int circleTickets, int ID) {
		try {
			
			//connect to the database
			db.connect();
			//if the proposed purchase is between 0 and the tickets we offer then 
			if (circleTickets <= getMaxCircleTickets() && circleTickets >=0) {
				//build up the query String 
				String sql = "UPDATE PSchedule set NoCircleTickets = "+ circleTickets +" WHERE ID = " + ID + ";";
				//run the query 
				ResultSet rs = db.runQuery(sql);
				//set noCircleTickets to be circleTickets
				this.noCircleTickets = circleTickets;
			}else {
				System.out.println("the number of circle tickets has to be less than specified");
			}
			//close the connection
			db.close();
			
		} catch (Exception e) {//handle the exception and print to the client what happened 
			System.out.println("An error happened while we were trying to set the number of Circle Tickets.\n"
					+ "Please bear with us while we are working on it.");
		}
		
	}

	
	
	
	/**
	 * 
	 * @param a method to insert a schedule into the database
	 * 
	 * @throws FileNotFoundException
	 */
	public void insertSchedule(Performance performanceTitle,LocalTime showTime, Date showDate, 
			int NoCircleTickets, int NoStallTickets) throws FileNotFoundException {
			
		
		try {
			
			//connect to the databse
			db.connect();
			//build up the query String
			String sql = "INSERT INTO PSchedule (performanceTitle, showTime, showDate, NoCircleTickets, NoStallTickets) "
					+ "VALUES ('" + performanceTitle +"'," +"'"+showTime+"',"+ "'"+
					showDate +"'," + "'"+NoCircleTickets +"'," +"'"+NoStallTickets+"');";
			//run the query
			ResultSet rs = db.runQuery(sql);
			//close the connection
			db.connect();
		} catch (Exception e) {
			System.out.println("An error happened while inserting the performance.\n"
					+ "Please bear with us while we are fixing it ");
			
		}
		
	}
	
	
	
	
	/**
	 * A method that updates the number of circle tickets as they get bought
	 * @returns (updates) the number of circle tickets left after the purchase
	 */
	
	public LocalTime getShowTime() {
		return showTime;
	}


	/**
	 * 
	 * @param A method that sets the showTime
	 */
	public void setShowTime(LocalTime showTime) {
		this.showTime = showTime;
	}


	/**
	 * @param A method that that gets the show date
	 * @returns showDate
	 */
	public Date getShowDate() {
		return showDate;
	}


	/**
	 * 
	 * @param A method that sets show Date
	 */
	public void setShowDate(Date showDate) {
		this.showDate = showDate;
	}


	


	public void sellTickets(int ticketsSold) {
		//int ticketsSold = client.getNoTicketsBought();
		int numTickets = noCircleTickets + noStallTickets;
		int ticketsLeft =  noCircleTickets + noStallTickets;
		if(ticketsSold <= noCircleTickets && ticketsSold <= noStallTickets){
			totalNoTicketsSold += ticketsSold;
			ticketsLeft =ticketsLeft- ticketsSold; 
			
		}else{
			System.out.println("Sorry we ran out of tickets"
					+ "\n there might be some Stall tickets left.");
		}
	}
	
	
	
	
	/**
	 * @param Calculates the number of stall tickets sold and how many stall tickets
	 * are left
	 * it also adds that to the number of tickets sold
	 */
	public void sellStallTickets(int stallTickets, int ID) throws FileNotFoundException {
		//int stallTicketsLeft = noStallTickets;
		//if the no of stall tickets the customer is buying is less or equal to the number of stall tickets available then
		if(stallTickets<= getNoAvailableStallTickets(ID)) {
			//subtract the no of stall tickets sold from the available stall tickets
			noStallTickets= noStallTickets - stallTickets;
			//add the number of stall tickets bought to what was sold already(stall tickets)
			noStallTicketsSold += stallTickets;
			//add the number of stall tickets sold to the total number of tickets sold
			totalNoTicketsSold+=stallTickets;
			
			try {
				db.connect();
				ResultSet result =db.runQuery("UPDATE PSchedule SET noStallTickets = noStallTickets - \n"
						+ "" + stallTickets + " where ID = " + ID + ";");
				
			}catch(Exception e) {
				System.out.println("an error happened while the selling process was taking place.\n"
						+ "please bear with us while we fix it");
				
			}

		}
		else {
			//print to the customer that stall tickets are sold out
			System.out.println("sorry we ran out of stall tickets.");
		}
	}
	
	/**
	 * @param Calculates the number of circle tickets sold and how many circle tickets
	 * are left
	 * it also adds that to the number of tickets sold
	 */
	public void sellCircleTickets(int circleTickets,int ID){
		
		try {
			db.connect();
			//int circleTicketsLeft = noCircleTickets;
			//if the number of circle tickets the customer is buying is less/equal to the number of circle tickets available then
			if(circleTickets <= getNoAvailableCircleTickets(ID)) {
			ResultSet result =db.runQuery("UPDATE PSchedule SET noCircleTickets = noCircleTickets - \n"
					+ "" + noCircleTickets + " where ID = " + ID + ";");
			
		
			//subtract the no of circle tickets sold from the available circle tickets
			noCircleTickets = noCircleTickets - circleTickets;
			//add the number of circle tickets bought to the no of circle tickets sold already
			noCircleTicketsSold = noCircleTicketsSold + circleTickets;
			//update the total number of sold tickets
			totalNoTicketsSold+=circleTickets;
			
			
		}
		else {
			//print to the customer that circle tickets are sold out
			System.out.println("Sorry we ran out of circle tickets.");
			
		}
			//close the connection
			db.close();
		}catch(Exception e) {//handle the exception and print to the Client
			System.out.println("An error happened while the selling process was taking place.\n"
					+ "please bear with us while we fix it");
		
			
		}
	}
	
	

	

	/**
	 * @param a method that gets the number of Stall tickets sold at a specific ID
	 * this method takes one parameter (ID) 
	 * @returns noStallTicketsSold
	 */
	public int getNoStallTicketsSold(int ID) throws FileNotFoundException {
		//return noStallTicketsSold
		return noStallTicketsSold;
	}


	
	/**
	 * @param a method that gets the number of Circle tickets sold at a specific ID
	 * this method takes one parameter (ID) 
	 * @returns noCircleTicketsSold
	 */
	public int getNoCircleTicketsSold(int ID) {
		//return noCircleTicketsSold
		return noCircleTicketsSold;
	}


	/**
	 * A method that gets the performance ID 
	 * @returns PerformanceID
	 */
	public int getPerformanceID() {
		//returns PerformanceID
		return PerformanceID;
	}


	
	/**
	 * @param A method that gets the maximum number of circle tickets
	 * @returns maxStallTickets
	 */
	public int getMaxCircleTickets() {
		return maxCircleTickets;
	}


	/**
	 * @param A method that gets the maximum number of stall tickets
	 * @returns maxStallTickets
	 */
	public int getMaxStallTickets() {
		return maxStallTickets;
	}


	
	/**
	 * @param select the performances by title from the database
	 * @returns the built up query
	 * 
	 */
	
	public String selectPerformancesByTitle(String title) {
		//String sql = "SELECT * FROM Performance WHERE Title = " ;
		String sql = "SELECT title ,performanceType ,performanceLanguage ,LiveMusic,Performer \n"
				+ "performanceDescription ,Duration_in_minutes, StallPrice,\n"
				+ " CirclePrice, ID, showTime, showDate, \n"
				+ "NoCircleTickets, NoStallTickets FROM Performance \n"
				+ "LEFT JOIN PSchedule ON Performance.title = PSchedule.performanceTitle\n"
				+ " where title =" ;//202210102>>go through formatting process
		sql+= "'" +title+ "'";
		return sql;
	}
	
	
	/**
	 * @param A method that lets the client select a performance using the date and then the title
	 *  The method takes 2 parameters performanceDate and title
	 * 
	 * @returns the build up string
	 */
	public String selectPerformanceByNameAndDate(String performanceDate, String title) {
		//build the sql String
		String sql = "SELECT title ,performanceType ,performanceLanguage ,LiveMusic, Perfomer\n"
		+ "performanceDescription ,Duration_in_minutes, StallPrice, \n"
		+ "CirclePrice, ID, showtime, showDate, NoCircleTickets, NoStallTickets \n"
		+ "FROM Performance LEFT JOIN PSchedule ON Performance.title = PSchedule.performanceTitle \n"
		+ "where title = \n";
		sql+= "'"+title+"'";
		sql+="and showDate =\n";
		sql+= "'"+performanceDate+"'";
		//return the built up String
		return sql;
		}
	
	
	/**
	 * @param A method that enables the customer to see the distinct performance on a chosen day
	 * The method takes one parameter performanceDate
	 * @returns @returns the build up string
	 */
		public String dispalyPerformanceByDay(String performanceDate) {
		//build the sql String	
		String sql = "SELECT distinct(title),performanceType FROM Performance inner join \n"
		+ "pschedule on performance.title = pSchedule.performanceTitle where showdate";
		sql = sql+ "= '"+performanceDate+ "'";
		//return the built up String
		return sql;
		}
	
	
	/**
	 * @param when the user selects a performance they get shown all the details of the performance
	 * if they make a spelling mistake or use upper case they will be shown a message telling them that
	 * 
	 */
	public Performance selectPerformance(String title) {
		//the user needs to know that the app is uppercase sensitive
		//go through the list of performances 
		for(int i = 0; i <performances.size(); i++) {
			//get the performances
			Performance per = performances.get(i);
			//if the one selected by the customer matches the name of the performance
			if (per != null && per.getTitle().equals(title)  ) {
				//present the customer with the details of the performance(including the price/s)
				
				System.out.println(per);
				//return the performance 
				return per;
				
			
			}
		}
		//return null otherwise
		return null;
	}
	
	
	public int getNoCirclesBasket(int i) throws FileNotFoundException {
		try {
			//connect to the database
			db.connect();
			//build the sql Query String
			String sql = "select count(circleSeat) from basket where circleSeat = 1 and scheduleID =" +i;
			//run the query		
			ResultSet rs = db.runQuery(sql);
			//if the resultset has another row then 
			if (rs.next()) {
				//set noCircleBasket to be the value thats inside the column
				this.noCircleBasket = rs.getInt(1);
					//return the value and come out
				return noCircleBasket;
			}//close the connection
			db.close();
		} catch (Exception e) {//handle the exception and print to the client that an error occured
			System.out.println("an error happened while we were getting the number of circle tickets in your basket\n"
					+ "please bear with us while we fix it");
            
			}
		//return an impossible number(in this case)
		return 999;
	}
	
	
	/**
	 * 
	 * @param i
	 * @return
	 * @throws FileNotFoundException
	 */
	public int getNoStallsBasket(int ID) throws FileNotFoundException {
		try {
			//close the connection
			db.connect();
			//build up the query String
			String sql = "select count(circleSeat) from basket where circleSeat = 0 and scheduleID =" + ID;
			//run the query		
			ResultSet rs = db.runQuery(sql);
			//if there is another row then 
			if (rs.next()) {
				//assign the value at the column to noStallBasket
				this.noStallBasket = rs.getInt(1);
				//System.out.println(stallBasketNo);
				//return noStallBasket
				return noStallBasket;
			}//close the connection
			db.close();
		} catch (Exception e) {//handle the exception and print to the client
			System.out.println("An error occured while we were getting the number of stall tickets in the basket.\n"
					+ "Please bear with us while we fix it.");
			}
		//return an impossible number
		return 999;
	}

	
	
	
	
	/**
	 * @param A method that gets the number EOFException performances (when we use an 
	 * ArrayList rather than the database)
	 * @returns the number of performances in the ArrayList
	 */
	public int getNoPerformances() {
		//return the size of the ArrayList that represents the number of Performances
		return performances.size();
	}
	

	
	

}