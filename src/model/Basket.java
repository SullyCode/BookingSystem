package model;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DBConnector;


/**
 * 
 * @author L@S Group
 * date:1201-2022
 * A class that models a Basket class and handles everything to do with it
 */
public class Basket {
	private ArrayList<Ticket> tickets;//declare an ArrayList of type Ticket 
	private int numStallTicketsAdded;//the number of stall tickets added 
	private int numCircleTicketsAdded;//the number of circle tickets added 
	private Client client;//declare a Client object 
	private double concessionary;//concessionary that holds the concessionary price 
	private Performance p;//declare a Performance object 
	private Ticket t;//declare a Ticket object 
	private Boolean circleSeat ;//declare a boolean variable circleSeat
	private Boolean boolConcessionary ;//declare a boolean variable boolConcessionary
	private static DBConnector db;//declare a DBConnector object 

	
	/**
	 * @Constructor
	 * @throws FileNotFoundException
	 */
	public Basket() throws FileNotFoundException {
		tickets = new ArrayList<Ticket>();//initialise the tickets ArrayList
		numStallTicketsAdded = 0;//set numStallTicketsAdded to 0
		numCircleTicketsAdded = 0;//set numCircleTicketsAdded to 0
		client = new Client();//initialise the client object 
		concessionary = 0;//set concessionary to 0
		p = new Performance();//initialise the Performance object
		t = new Ticket();//initialise the Ticket object
		boolean circleSeat = true;//set circleSeatTF to true 
		boolean boolConcessionary = false;//set concessionaryTF to false
		db = new DBConnector();//initialise the DBConnector object
	}


	/**
	 * @param A method that sets circleSeat to true (useful when we want 
	 * to retrieve the true value of circleSeat)
	 * 
	 */
	// to set if the ticket is in Circle or Stall and retrieve the True or False 
	public void setCircleSeatTrue () {
		//set circleSeat to true
		this.circleSeat = true;
		}
	
	/**
	 * @param A method that sets circleSeat to false
	 */
	public void setCircleSeatFalse () {
		//set circleSeat to false
		this.circleSeat = false;
		}
	
	
	/**
	 * @param A method that gets the value of circleSeat 
	 * @returns
	 */
	public boolean getCircleSeatTF() {
		//return circleSeat 
		return this.circleSeat;
		}
	
	/**
	 * @param A method that sets Concessionary to True 
	 */
	// to set if the ticket is concessionary and retrieve the True or False 
	public void setConcessionaryTrue () {
		//set boolConcessionary to true 
		this.boolConcessionary = true;
	}
	
	
	/**
	 * @param A method that sets Concessionary to False
	 */
	public void setConcessionaryFalse () {
		//set boolConcessionary to false 
		this.boolConcessionary = false;
	}
	
	
	/**
	 * @param A method that gets the value of boolConcessionary 
	 * @returns boolConcessionary 
	 */
	public boolean concessionaryTF() {
		return this.boolConcessionary;
	}
	
	
	////////////////////////////////////////////////getCircleSeatTF(), ////////////getConcessionaryTF()
	
	/**
	 * A method that adds to the basket whenever the client wants to add a ticket to the basket
	 * the method takes 3 parameters ID
	 * @param performanceTitle
	 * @param price
	 */
	public void addToBasket(int ID, String performanceTitle, double price) {
	try {
		//connect to the databse
		db.connect();
		//build the sql string
		String sql = "INSERT INTO Basket (scheduleID, performanceTitle, "
				+ "circleSeat, price, concessionary)" + 
				"VALUES(" +ID+", '" + performanceTitle +"', " + getCircleSeatTF()+", "+ price +", "+concessionaryTF()+ ");";
		//run the sql string in the database
		ResultSet rs = db.runQuery(sql);
		
		
	} catch (Exception e) {//handle the exception and print to the client that an error happened
		System.out.println("an error occured while adding to the basket. please bear with us while we fix it");
		e.printStackTrace();//DELETE THIS BEFORE SUBMISSION
	}	

	}
	
	/**
	 * @param A method that clears the basket after the client purchases the ticket/s
	 * 
	 */
	public void clearBasket() {
		try {
			//connect to the database
			db.connect();
			//build up the sql string
			String sql = "DELETE FROM Basket WHERE ID > 0;";
			//run the query
			ResultSet rs = db.runQuery(sql);
			
		} catch (Exception e) {//handle the exception and print to the clientr that an error happened
			System.out.println("An error occured while clearing the basket");
			e.printStackTrace();//DELETE THIS BEFORE SUBMISSION
		}
	}

	/**
	 * a method that removes tickets and takes a ticket object as a parameter
	 * @param ticket
	 */
	public void removeTicket(Ticket ticket) {
		//loop through the tickets
		for (Ticket t : tickets) {
			//if a ticket/s isnt there then 
			if (t == null) {
				//print to the client
				System.out.println("No ticket with those details");
			} else {//if the ticket taht we want to remove is there then 
				if (t.equals(ticket)) {
					// remove the particular ticket from the list
					tickets.remove(ticket);
					
				}

			}

		}

	}


	/**
	 * a method that gets the tickets in the list 
	 * @returns the tickets
	 */
	public ArrayList<Ticket> getTickets() {
		//return the tickets
		return tickets;
	}

	
	/**
	 * A method that calculates the concessionary price for a ticket
	 * this is calculating how much is 25%(of the total price and gives you the amount 
	 * the customer should pay)
	 * @param number
	 * @returns the price the customer should pay
	 */
	public double calculateConcessionary(double number) {
		// this calculates how much a customer should pay if their
		// ticket is a concessionary ticket
		// this is calculating how much is 25%(of the total price
		// and gives you the amount the customer should pay
		//declare a local variable amount and set it to 0
		double amount = 0;
		//set the amount to be the full price /4
		amount = number / 4;
		//concessionary is the result of the previous - the full price
		concessionary = number - amount;
		//return the value of concessionary
		return concessionary;
	}
	

	/**
	 * A method that gets the value of concessionary
	 * @returns concessionary
	 */
	public double getConcessionary() {
		//return concessionary
		return concessionary;
	}

	
	
	/**
	 * A methos that gets the number of tickets added
	 * @return the size of the list(the number of tickets)
	 */
	public int  getNumTicketsAdded() {
		//set total to be the number of circle tickets added and the stall tickets added 
		int total = numStallTicketsAdded + numCircleTicketsAdded ;
		//return the total
		return total;
	}



}
