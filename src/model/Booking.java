package model;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

import util.DBConnector;


/**
 * 
 * @author L@S Group
 * date:12-01-2022
 *
 * A class that models a booking and handles everything to do with thw booking part of the 
 * program
 */
public class Booking {
	private static DBConnector db;//declare a DBConnector
	private String lastClientID;//last client(will be converted to an int type)
	private boolean postage;//yes or no
	private int postagePrice;// total price postage
	private double totalPrice; // total price postage + price for ticket
	private double ticketsPrice; // total price of all tickets
	private Scanner scanner;//scanner variable
	
	
	/**
	 * @Constructor 
	 * @throws FileNotFoundException
	 */
	public Booking() throws FileNotFoundException {
		db = new DBConnector();//initialise the DBConnector object
		this.lastClientID = null;//set the client ID to null (will be parsed later in the program)
		this.postage = false;// by default the postage is set to false unless specified by the customer
		this.postagePrice = 0;// postage price
		this.totalPrice = 0; // total price include the postage
		this.ticketsPrice = 0; // total price for all ticket no postage included
		scanner = new Scanner(System.in);//initialise a Scanner object
	
	}
	
	
	
	/**
	 * @param A method that calculates the postage price
	 * 
	 * 
	 */
	public void calculatePostagePrice() {
		try {
			//connect to the database
			db.connect();	
			//declare and initialise adultTickets
			String adultTickets = "select count(concessionary) from basket where concessionary = '0';";
			//declare and initialise concessionaryTickets
			String concessionaryTickets = "select count(concessionary) from basket where concessionary ='1';";
			//
			String noConTic= "";
			//
			String conTic ="";
			//run the adultTickets query
			ResultSet rsNCT = db.runQuery(adultTickets);
			//if there is another row
			if (rsNCT.next()) {
				//get the value and assign it to noConTic
				noConTic = rsNCT.getString(1);
			}
			//run the concessionary tickets query
			ResultSet rsCT = db.runQuery(concessionaryTickets);
			//if there is another row in thye resiultset then 
			if (rsCT.next()) {
				//get the value and assign it to conTic
				conTic = rsCT.getString(1);
		
			}
					
			//if conTic is 1 and noConTic is 0 then 
			if (!conTic.equals("0") && noConTic.equals("0")) {
				//set postage to 0 
				this.postagePrice = 0;
				//if conTic is 1  and noConTic is 1 then 
			}else if (!conTic.equals("0") && !noConTic.equals("0")) {
				//set postage to 1
				this.postagePrice = 1;
				//if conTic is 0 and noConTic is 1 then 
			}else if (conTic.equals("0") && !noConTic.equals("0")) {
				//
				this.postagePrice = (int) Double.parseDouble(noConTic);
				//close the connection
				db.close();
			}
		}
		catch (Exception e) {//handle the exception and print to the client
			System.out.println("An error happened while calculating the postage price \n"
					+ "please bear with us while we are working on it");
           
		}
		
	}
	
	
	
	/**
	 * A method that gets the postage price 
	 * @returns the postage price
	 */
	public int getPostagePrice(){
		//call the calculatePostagePrice method
		calculatePostagePrice(); 
		//return the postage price
		return this.postagePrice;
	}
	
	
	/**
	 * @param A method that calculates the total price of the tickets added 
	 * 
	 */
	public void totalTicketsPrice() {
		//declare a new variable ticketValue and set it to an empty String
		String ticketValue = "";
		try {//connect to the database
		db.connect();
		//build up the sql query string that calculates the total value of whats
		//inside the price column
		String sql = "select sum(price) from basket";
		//run it
		ResultSet rs = db.runQuery(sql);
		//if the ResultSet has another row
		if (rs.next()) {
		//set ticketValue to be the value thats inside the price column
		ticketValue = rs.getString(1);
		//if ticketValue is null then 
		if(ticketValue == null) {
			//set ticketPrice to 0
		this.ticketsPrice = 0.0;
		}else {//else parse the value of ticketsPrice from String to double
		this.ticketsPrice = Double.parseDouble(ticketValue);
		//print the value ticketsPrice  
		//System.out.println(this.ticketsPrice);
		}

		}
		} catch (Exception e) {// handle the exception and print to the client
		System.out.println("An error happened while trying to get the total price of \n"
		+ "the tickets you have chosen. please bear with us we are fixing it");
		}
		}
	
	
	
	/**
	 * @param A method that gets the price of a specific individual ticket 
	 * @returns ticketPrice
	 */
	public double getTicketsPrice() {
		totalTicketsPrice();
		return this.ticketsPrice;
	}
	
	
//	/**
//	 * @param A method that calculates the total price(including postage)
//	 */
//	public void calculateTotalPrice() {
//		this.totalPrice = getTicketsPrice() + getPostagePrice();
//		
//	}
	

	/**
	 * @param A method that gets the total price 
	 * @returns totalPrice
	 */
	public double calculateTotalPrice() {
		this.totalPrice = getTicketsPrice() + getPostagePrice();
		return this.totalPrice ;
	}
	
	
	
	/**
	 * @param A boolean method that gets input from the client and sets the 
	 * postage to true or false accordingly
	 * @returns the final value of postage depending on the customer's choice
	 */
	public boolean isPostage() {
		//print to the client 
		System.out.println("Do you want these tickets to be posted? answer with 'yes' or 'no'");
		//declare a local variable yesORno and set it to be an equal string
		String yesORno ="";
		//while yesORno isnt yes or no 
		while (!yesORno.equals("yes")||!yesORno.equals("no")){
			//take the clients input and set it to be pstg
			String pstg = scanner.nextLine();
			//if pstg their input is yes then 
	
			if (pstg.equals("yes")) {
				System.out.println("Total amount of your ticket(s) plus postage is: "+calculateTotalPrice());
	            System.out.println("------------------------------------------------");
				//set postage to true
				this.postage = true;
				//return the value of postage and come out of the method
				return postage;
			}
			//else if they input no then
			else if (pstg.equals("no")){
			    System.out.println("Total amount of your ticket(s) is: "+getTicketsPrice());



                System.out.println("--------------------------------");
				//set postage to be false 
				this.postage = false;
				//return the value of postage and come out of the method
				return postage;
			}else {//print to the client that they can only input yes or no
				System.out.println("Please type in yes or no");
			}
		}
		//set postage to be true or false depending on the client's input and come out
		return postage;
	}
	

	/**
	 * 
	 * @returns the value of postage
	 */
	public boolean isPostageTrue() {//DO WE NEED THIS??? WE ALREADY 
		return this.postage;//HAVE THE METHOD THAT SETS THE BOOLEAN??? 
		//we can just use postage in the next method
	}
	
	
	/**
	 * A method that inserts a booking into the database
	 * @returns
	 * @throws FileNotFoundException
	 */
	public String addBooking() throws FileNotFoundException {
		if(postage==false) {  
			this.postagePrice = 0;
		//build the sql string using the addNewBooking procedure 
			String sql = "Call addNewBooking(";
			sql = sql + isPostageTrue() + ","; // true = there is a postage // false = there is not postage
			sql = sql + this.postagePrice + ","; // postage price
			sql = sql + (getTicketsPrice() +this.postagePrice )+","; // price tickets + postage
			sql = sql + "current_timestamp,";//the current time
			sql = sql + getLastClientID() + ",";//the last clients ID number
			sql = sql + "@bookingID);";//the booking id number that gets generated automatically
			//return the built up string and come out
			return sql;
		}else{
			String sql = "Call addNewBooking(";
			sql = sql + isPostageTrue() + ","; // true = there is a postage // false = there is not postage
			sql = sql + getPostagePrice() + ","; // postage price
			sql = sql + calculateTotalPrice() +","; // price tickets + postage
			sql = sql + "current_timestamp,";//the current time
			sql = sql + getLastClientID() + ",";//the last clients ID number
			sql = sql + "@bookingID);";//the booking id number that gets generated automatically
			//return the built up string and come out
			return sql;
			
		}
		
		

	}

	/**
	 * @param a method that gets the last client's ID
	 * @returns lastClientID
	 */
	public String getLastClientID() {
		//return lastClientID
		return this.lastClientID;
	}

	
	/**
	 * A method to set the last client's ID
	 * @throws FileNotFoundException
	 */
	public void setLastClientID() throws FileNotFoundException {
		try {//connect to the database 
			db.connect();
			//build the sql query string
			String sql = "SELECT MAX(ID) FROM Client"; // this is the last clientID - as we will created clientID,
			//run the query
			ResultSet rs = db.runQuery(sql);
			//if the resultset has another row then
			if (rs.next()) {
				//set the lastClientID to be the value at the last column
				this.lastClientID = rs.getString(1);
				//close the connection
				db.close();
			}

		} catch (Exception e) {//print to the client that an error happened 
			System.out.println("An error occured while setting your ID number in the system.\n"
					+ "please bear woth us while we are working on it");
			
		}
	}

}
