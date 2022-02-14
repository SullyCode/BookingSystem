package model;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import util.DBConnector;


/**
 * 
 * @author L@S Group
 * date:12-01-2022
 *
 *This class models a ticket object that gets generated when a client chooses the details of the event they are 
 *interested in and adds then to the basket(in the form of a ticket) and when they buy the actual ticket
 */
public class Ticket {


	private int scheduleID;//the ID number of the performance
	private String PerformanceTitle;//the title of the performance
	private boolean circleSeast;//if the client chooses a circle seat this evaluates to true and if they choose a stall ticket,false
	private double price;//the price of the single ticket added to the basket/bought
	private boolean concessionary;//evaluates to true if the client chooses a concessionary ticket and false otherwise
	private Scanner scanner;//a scanner object
	//private Booking booking;//a booking object
	private Performance p;//a performance object
	private Basket basket;// a basket object
	private String lastBookingID;//the last booking's ID number. gets parsed into an int
	private static DBConnector db;//a DBConnector object

	/**
	 * @constructor with no parameters
	 * @throws FileNotFoundException
	 */
	public Ticket() throws FileNotFoundException  {
		scanner = new Scanner(System.in);//initialise the scanner object
		p = new Performance();//initialise the Performance object
		db = new DBConnector();//initialise the DBConnector object
	}

	
	/**
	 * 
	 * @constructor with three parameters
	 * @throws FileNotFoundException
	 */
	public Ticket(String performanceTitle, boolean circleSeast, boolean concessionary) throws FileNotFoundException {
		lastBookingID = null;//set default value of lastBookingID to null  
		PerformanceTitle = performanceTitle;//initialise PerformanceTitle
		this.circleSeast = false;//set default value of circleSeast to false
		this.concessionary = false;////set default value of concessionary to false
		

	}
	
	/**
	 * @param A method that sets the last booking ID
	 * @throws FileNotFoundException
	 */
	public void setLastBookingID() throws FileNotFoundException {//we dont set the last booking ID we get it because 
		//it gets generated for us
		try {//connect to the database
			db.connect();
			//build an SQL query                                                     //we dont create it it gets generated for us
			String sql = "SELECT MAX(ID) FROM Booking"; // this is the last BookinID - as we will created BookingID,
			//runn thye query and call it rs
			ResultSet rs = db.runQuery(sql);
			//if there is another row
			if (rs.next()) {
				//set the id for that row to 
				this.lastBookingID = rs.getString(1);
			}
			
		} catch (Exception e) {
			System.out.println("An error happened while getting the booking ID\n"
					+ "We are working very hard to fix the problem please bear with us");
            e.printStackTrace();//THIS SHOULD BE COMMENTED OUT BEFORE SUBMISSION AS THE CLIENTS
            //SHOULDNT SEE THIS
		}
		//close the connection
		db.close();
		
	
		
	}
	
	/**
	 * @param a method that gets the last generated booking ID
	 * @returns the last generated booking ID
	 */
	public String getLastBookingID() {
		//return the last booking ID
		return this.lastBookingID;
	}
	
	
	/**
	 * 
	 * @return the built up SQL String
	 */
	public String insertTicketInDB() {//DONT THINK WE NEED ALL THIS ITS JUST AN INSERT STATEMENT 
		String sql = "INSERT INTO ticket (ScheduleID, performanceTitle, circleSeat,price,concessionary)\n"
				+ "SELECT ScheduleID, performanceTitle, circleSeat,price,concessionary\n"
				+ "FROM basket\n"
				+ "WHERE ID > 1;";
		return sql;
	}
	
	
	
	public String addBookingIntoTicket() throws FileNotFoundException {
		String sql = "UPDATE Ticket\n"
				+ "SET bookingID = \n";
			sql += "'"+getLastBookingID()+"'";
			sql	+= "WHERE bookingID is null;";
		
		return sql;
	}
	
	
	
	
	
	
	/**
	 * A method that gets the schedule ID
	 * @returns scheduleID
	 */
	public int getScheduleID() {
		//return scheduleID
		return scheduleID;
	}

	/**
	 * A method that gets the performance title
	 * @returns PerformanceTitle
	 */
	public String getPerformanceTitle() {
		//return performanceTitle
		return PerformanceTitle;
	}

	
	/**
	 * A boolean method that establishes whether its a circle seat that customer opted for 
	 * or a stall seat Circle Seat if it evaluates to true and stall if false 
	 * @returns circleSeast
	 */
	public boolean isCircleSeat() {
		//return circleSeat 
		return circleSeast;
	}

	
	/**
	 * A method that gets the price of a particular ticket
	 * @returns the price
	 */
	public double getPrice() {
		//return price 
		return price;
	}

	
	/**
	 * A boolean method that establishes whether the seat the client opted for is concessionary 
	 * or not
	 * @returns concessionary (a boolean variable
	 */
	public boolean isConcessionary() {
		//System.out.println("Is any of the tickets you are interested in gonna be a Concessionary ?" +" answer with 'yes' or 'no'");
		//declare a new variable answer and set it to whatever the client inputs
		String answer = scanner.nextLine();
		//if what they input is yes then 
		if (answer.equals("yes")) {
			//set concessionary to true
			concessionary = true;
			// double postagePrice = booking.getPostagePrice() ;
			// postagePrice = 1;
			//if they input  no then 
		} else if (answer.equals("no")) {
			//set concessionary to false
			concessionary = false;
		}
		//return whatever the value of concessionary is (true or false)
		return concessionary;
	}

}