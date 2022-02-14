package model;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;

import util.DBConnector;
import util.Parser;


/**
 * 
 * @author L@S Group
 * date:12-01-2022
 *
 *A class that models a client and handles everything to do with it
 */
public class Client {
	private String name;// the client's name 
	private String lastName;//the client's last  name
	private String email;//the client's email
	private LocalDate dob;// the client's date of birth
	private String houseNo;//the client's house number
	private String street;//the street where the customer lives 
	private String city;//the city where the customer lives 
	private String country;//the country where the customer lives 
	private String cardNumber;// the client's card number
	private Schedule schedule;//declare a Schedule object
	private Performance performance;//declare a Performance object
	private int numStallTicketsBought;//the number of Stall tickets bought
	private int numCircleTicketsBought;//the number of Circle tickets bought
	private double bill;
	private static DBConnector db;//declare a DBConnector object

	
	/**
	 * @Constructor
	 * @throws FileNotFoundException
	 */
	public Client() throws FileNotFoundException {
		schedule = null;//set Schedule to null
		this.numStallTicketsBought = 0;//set numStallTicketsBought to 0
		this.numCircleTicketsBought = 0;// set numCircleTicketsBought to 0
		performance = null;//set performance to null
		this.bill = 0;//set bill to 0
		db = new DBConnector();//initialise the DBConnector object
		
	}
	


	/**
	 * @Constructor 2
	 * @param name
	 * @param lastName
	 * @param email
	 * @param dob
	 * @param houseNo
	 * @param street
	 * @param city
	 * @param country
	 * @param cardNumber
	 */
	public Client(String name, String lastName, String email, LocalDate dob, String houseNo, String street, String city,
			String country, String cardNumber) {
		this.name = name;//initialise name 
		this.lastName = lastName;//initialise lastName
		this.email = email;//initialise email
		this.dob = dob;//initialise dob
		this.houseNo = houseNo;//initialise houseNo
		this.street = street;//initialise street 
		this.city = city;//initialise city
		this.country = country;//initialise country
		this.cardNumber = cardNumber;//initialise cardNumber
	}


	
	/**
	 * @param a method that unnulls the Performance object
	 * (if initialised properly in the constructor it causes method overloading and the customer cant buy)
	 * takes one parameter which is a Performance object
	 */
	public void navigatePerformance(Performance per) {
		//if the performance is null then 
		if(performance == null ) {
			//set it to per(a performance object)
			performance = per;
		}
	}
	

	/**
	 * @param a method that unnulls the schedule object
	 * (if initialised properly in the constructor it causes method overloading and the customer cant buy)
	 * takes one parameter which is a Schedule object
	 */
	public void navigateSchedule(Schedule sh) {
		//if the schedule is null then 
		if (schedule == null) {
			//set it to sh(a schedule object)
			schedule = sh;
		}
	}
	
	

	/**
	 * @param A buy stall method that works with the schedule class and the the database 
	 * the method takes 2 parameters the number of tickets the customer wants to buy and the ID of the schedule 
	 * which represents the time and date of the performance
	 * @throws FileNotFoundException
	 */
	public void buyStallTicket(int noStallTickets, int ID) throws FileNotFoundException {
		//if the number of circle tickets in the database isn't 0 then 
		if(schedule.getNoAvailableStallTickets(ID) == 0) {
			//print the customer that we ran out of stall tickets
			System.out.println("sorry we ran out of Stall tickets for schedule "+ ID);
		}//if the schedule exists and there is still stall tickets available for the particular performance 
		//at a particular time and date	then 
		else if (schedule.getNoAvailableStallTickets(ID) > 0 && schedule.getNoAvailableStallTickets(ID) <= 120 && schedule != null) {
			//buy (schedule sells and the client buys)
			schedule.sellStallTickets(noStallTickets,ID);
			//update the number of stall tickets bought to what the customer just bought
			numStallTicketsBought += noStallTickets;

		}
	}
	
	
	/**
	 * @param A buy circle method that works with the schedule class and the the database 
	 * the method takes 2 parameters the number of tickets the customer wants to buy and the ID of the schedule 
	 * which represents the time and date of the performance
	 * @throws FileNotFoundException 
	 */
	public void buyCircleTicket(int noCircleTickets,int ID) throws FileNotFoundException {
		//if the number of circle tickets in the database isn't 0 then 
		if(schedule.getNoAvailableCircleTickets(ID) == 0) {
			//print the customer that we ran out that we run out of circle tickets
			System.out.println("sorry we ran out of Circle tickets for schedule "+ ID);
		}//if the schedule exists and there is still circle tickets available for the particular performance 
		//at a particular time and date	then 
		else if (schedule.getNoAvailableCircleTickets(ID) > 0 && schedule.getNoAvailableCircleTickets(ID) <= 80 && schedule != null) {
			//buy (schedule sells and the client buys)
			schedule.sellCircleTickets(noCircleTickets,ID);
			//update the number of circle tickets bought to what the customer just bought
			numCircleTicketsBought = numCircleTicketsBought + noCircleTickets;

		}
	}
	


	/**
	 * @param a method that gets the number of stall tickets bought
	 * @returns numStallTicketsBought
	 */
	public int getNumStallTicketsBought() {
		//return numStallTicketsBought
		return numStallTicketsBought;
	}

	
	/**
	 * @param a method that gets the number of circle tickets bought
	 * @returns numCircleTicketsBought
	 */
	public int getNumCircleTicketsBought() { 
		//return numCircleTicketsBought
		return numCircleTicketsBought;
	}
	
	
	/**
	 * @param a method that calculates the number of stall tickets bought and number of circle tickets bought
	 * and assigns them to the variable total
	 * @returns total
	 */
	public int getTotalTicketsBought() {
		//declare a variable total and assigns it to the result of numCircleTicketsBought + numStallTicketsBought
		int total = numCircleTicketsBought + numStallTicketsBought;
		//return total
		return total;
	}


	
	
	/**
	 * @param A method that sets the customers card number and takes one parameter
	 *  cardNumber
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}



	/**
	 * @param a method that gets the client's name
	 * @returns the name of the client
	 */
	public String getName() {
		//return total
		return name;
	}

	/**
	 * @param a method that gets the client's surname 
	 * @returns the customer's lastName
	 */
	public String getSurname() {
		//return total
		return lastName;
	}


	/**
	 * @param A method that gets the customer's email
	 * @returns the customer's email 
	 */
	public String getEmail() {
		//return email
		return email;
	}


	/**
	 * @param A method that gets the customer's date of birth
	 * @returns the clients date of birth
	 */
	public LocalDate getDob() {
		//return dob
		return dob;
	}


	/**
	 * @param a method that gets the client's house number
	 * @returns the client's house number
	 */
	public String getHouseNo() {
		//return the clients houseNo
		return houseNo;
	}

	/**
	 * @param A method that gets the client's street
	 * @returns street
	 */
	public String getStreet() {
		//return street
		return street;
	}

 
	/**
	 * @param A method that gets the city where the client lives 
	 * @returns city 
	 */
	public String getCity() {
		//return city 
		return city;
	}


	/**
	 * @param A method that gets the country where the client lives 
	 * @return
	 */
	public String getCountry() {
		//return country
		return country;
	}


	
	/**
	 * @param A method that gets the client's card number
	 * @return
	 */
	public String getCardNumber() {
		//return card number
		return cardNumber;
	}

	
	/**
	 * @param A method that gets the performance's schedule
	 * @return
	 */
	public Schedule getSchedule() {
		//return schedule 
		return schedule;
	}

}
