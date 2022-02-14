package controller;

import java.awt.print.Book;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;

import model.Basket;
import model.Booking;
import model.Client;
import model.Performance;
import model.Schedule;
import model.Ticket;
import util.DBConnector;
import util.Parser;

/**
 * 
 * @author L@S Group date:
 * date :12-01-2022
 */
public class Engine {

	private static boolean isCorrect;//declare a boolean variable isCorrect
	private static boolean isRepeat;//declare a boolean variable isRepeat
	private static String input;//declare a String variable input

	/**
	 * @constructor
	 */
	public Engine() {
		isCorrect = false;//set isCorrect to false
		isRepeat = false;//set isRepeat to false
		input = "";//set input to an empty String
	}

	/**
	 * @param the main method
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		DBConnector db = new DBConnector();//declare and initialise a DBConnector object 
		Basket basket = new Basket();//declare and initialise a Basket object 
		Parser parser = new Parser();//declare and initialise a Parser object 
		Client client = new Client();//declare and initialise a Client object 
		Performance performance = new Performance();//declare and initialise a Performance object 
		Schedule s = new Schedule();//declare and initialise a Schedule object 
		Scanner scan = new Scanner(System.in);//declare and initialise a Scanner object 
		Booking booking = new Booking();//declare and initialise a Booking object 
		Ticket ticket = new Ticket();//declare and initialise a Ticket object 
		try {
			// print the welcome print
			parser.welcome();
			// prints what we are going to show the client and simulates loading
			parser.prePhrase();
			// connects to the database
			db.connect();
			// make sure that basket is empty before we start so client cannot buy something
			// in error
			basket.clearBasket();
			//set isRepeat to false
			isRepeat = true;
			while (isRepeat) {
				// this is for browsing all the performances that we have (only shows the titles
				// and the type and if the client wants to see the details
				// they select a title and get shown all the details)
				performance.getPerformances();
				// a wait simulation(same as a Website)
				Thread.sleep(1000);
				System.out.println("");
				// prompting the client to choose a title for more details
				parser.choosePerformancePrint();
				// store what they typed(a title from the selection
				input = scan.nextLine().trim().toLowerCase();
				// if the client inputs "search by date" then
				if (input.equals("search by date")) {
					// print to the client and guide them in the printing of the format
					System.out.println("Please type the date in this format YYYY-MM-DD.");
					// store their input in a variable
					String performanceDate = scan.nextLine();
					// a print simulating loading
				    parser.waitPlease();
					// run the query from what the client has chosen
					ResultSet rsDate = db.runQuery(s.dispalyPerformanceByDay(performanceDate));
					// print the resultset
					db.printResults(rsDate);
					// prompt the client and guide them
					System.out.println("Please choose a Performance for all the details");
					// store the input in a variable
					String title = scan.nextLine().trim().toLowerCase();
					// assign input to title
					input = title;
					// a print simulating loading
				    parser.waitPlease();
					// run the query
					ResultSet rsTitle = db.runQuery(s.selectPerformanceByNameAndDate(performanceDate, title));
					// print the resultset
					db.printResults(rsTitle);

				} else {
					// a print simulating loading
				    parser.waitPlease();
					// else if they chose to search by title then run the query for it
					ResultSet rst = db.runQuery(s.selectPerformancesByTitle(input));
					// print the resultset
					db.printResults(rst);
				}
				    
				    //prompt the client to input the ID that contains all the details for the performance
				    parser.idPrompt();
				    //declare a new variable and set it to whatever the client has inputted
				    int ID = scan.nextInt();
				    //eat off the line
				    scan.nextLine();
				    //a buy prompt
				    parser.buyPrompt();
				    //declare a new variable cirlceOrStall and set it to an empty String
				    String cirlceOrStall = "";
				    //while cirlceOrStall doesnt equal circle or stall then
				    while (!cirlceOrStall.equals("circle") || !cirlceOrStall.equals("stall")) {
					//declare a variable buy and store the clients input in it 
				    String buy = scan.nextLine().trim().toLowerCase();
				    //if that equals circle
					if (buy.equals("circle")) {
					//ask the client how many concessionary circle tickets they want	
					parser.howManyConcessionaryCircles();
					//set circle seat to true
					basket.setCircleSeatTrue();
					//set concessionary to true
					basket.setConcessionaryTrue();
					//check the total of what the client has pit in the basket isnt more than what is available
					int canBuy = (s.getNoAvailableCircleTickets(ID) - s.getNoCirclesBasket(ID));
					//set isCorrect to false
					isCorrect = false;
					//while correct is false
					while (!isCorrect) {
						//print how many the client can buy
						System.out.println("You can only add " + canBuy + " Circle ticket(s) for the chosen time.");
						//declare concessionaryCircles and set it to the clients input
						int concessionaryCircles = scan.nextInt();
						//if concessionaryCircles is less than what the client can buy then 
						if (concessionaryCircles >=0 &&concessionaryCircles <= canBuy) {
								//set isCorrect to true	
								isCorrect = true;
								//loop through the number of concessionaryCircles
								for (int i = 0; i < concessionaryCircles; i++) {
									//adding them to the basket  
									basket.addToBasket(ID, input, performance.getCircleConcessionaryPrice(input));
								}
							}
						}
						//ask the client how Many Adult Circle tickets they want
						parser.howManyAdultCircles();
						//set Circle Seat to true
						basket.setCircleSeatTrue();
						//set Concessionary to false
						basket.setConcessionaryFalse();
						//check the total of what the client has pit in the basket isnt more than what is available
						canBuy = (s.getNoAvailableCircleTickets(ID) - s.getNoCirclesBasket(ID));
						//set is correct to true
						isCorrect = false;
						//while isCorrect is true
						while (!isCorrect) {
							//print how many the client can buy
							System.out.println("You can only add " + canBuy + " Circle ticket(s) for the chosen time.");							//declare and set adultCircles to what the client has inputted
							int adultCircles = scan.nextInt();
							//if what they want to buy is less than or equal to what they can buy then 
							if (adultCircles >=0 && adultCircles <= canBuy) {
								//set isCorrect to true
								isCorrect = true;
								//loop through the number tof tickets they want to buy 
								for (int i = 0; i < adultCircles; i++) {
									//adding them to the basket
									basket.addToBasket(ID, input, performance.getCirclePrice(input));
								}
							}

						}
						//break out of the loop
						break;

						//else if the client wants to buy a stall ticket then 
					} else if (buy.equals("stall")) {
						//ask the client how many concessionary stall tickets they want
						parser.howManyConcessionaryStalls();
						//set CircleSeat to false
						basket.setCircleSeatFalse();
						//set Concessionary to true
						basket.setConcessionaryTrue();
						//check the total of what the client has pit in the basket isnt more than what is available
						int canBuy = (s.getNoAvailableStallTickets(ID) - s.getNoStallsBasket(ID));
						//set isCorrect to false
						isCorrect = false;
						//while isCorrect is true
						while (!isCorrect) {
							//print how many the client can buy
							System.out.println("You can only add " + canBuy + " Stall ticket(s) for the chosen time.");							//declare and set concessionaryStall to what the client has inputted
							int concessionaryStall = scan.nextInt();
							//if what they want to buy is less than or equal to what they can buy then 
							if (concessionaryStall >=0 &&concessionaryStall <= canBuy) {
								//set isCorrect to true  
								isCorrect = true;
								//loop through the number of concessionary Stall tickets they proposed to buy 
								for (int i = 0; i < concessionaryStall; i++) {
									//adding them to the basket
									basket.addToBasket(ID, input, performance.getStallConcessionaryPrice(input));
								}
							}
						}
						
						
						//ask the client how Many Adult Circles they want to buy
						parser.howManyAdultStalls();
						//set Circle Seat to False
						basket.setCircleSeatFalse();
						//set Concessionary to False
						basket.setConcessionaryFalse();
						//check the total of what the client has pit in the basket isnt more than what is available
						canBuy = (s.getNoAvailableStallTickets(ID) - s.getNoStallsBasket(ID));
						//set isCorrect to true
						isCorrect = false;
						//while isCorrect is false
						while (!isCorrect) {
							//print how many the client can buy
							System.out.println("You can only add " + canBuy + " Stall ticket(s) for the chosen time.");
							//declare and set adultStall to what the client has inputted
							int adultStall = scan.nextInt();
							//if what they want to buy is less than what they can buy then 
							if (adultStall >= 0 && adultStall <= canBuy) {
								//set isCorrect to true
								isCorrect = true;
								//loop through the proposed number of tickets the client wants to buy
								for (int i = 0; i < adultStall; i++) {
									//dding them to the basket 
									basket.addToBasket(ID, input, performance.getStallPrice(input));
								}
							}
						}
						//break out of the loop
						break;

					} else {//ask the client to either choose circle or stall
						System.out.println("Please write only 'circle' or 'stall'.");
					}
				}
				 //print to the client how much the tickets they added to the basket come up to 
				    System.out.println("The ticket(s) that you have added to your basket come(s) up to: " + booking.getTicketsPrice());  // Add the price into System.out.print
					System.out.println("---------------------------------------------------------------");
					//ask the client if they want to buy what to do or add to add more tickets 
					parser.buyOrAddPrompt();
				//declare a variable answer and set it to buyORadd
				String answer = "buyORadd";
				//while the clients response is neither buy nor add loop through 
				while (!answer.equals("buy") || !answer.equals("add")) {
					//set answer to whatever the client inptted 
					answer = scan.nextLine().trim().toLowerCase();
					//if their answer is buy then 
					if (answer.equals("buy")) {
						//isRepeat to false
						isRepeat = false;
						//break out of the loop 
						break;
						//else if they input add
					} else if (answer.equals("add")) {
						//set isRepeat to true
						isRepeat = true;
						//break out of the loop
						break;
						
					} else {//if they write anything other than buy or continue then print this
						System.out.println("Please write only 'buy' or 'add' to continue.");
					}
				}

			}

			booking.isPostage();
			// calculate the total price (includes everything)
			booking.calculateTotalPrice();
			//take customer details and insert them in the database 
			ResultSet rstClientDB = db.runQuery(parser.takeCustomerDetails());
			//set the last client'S ID
			booking.setLastClientID();
			//add the booking to the database 
			ResultSet rsAddBooking = db.runQuery(booking.addBooking());
			//get the last booking ID
			ticket.setLastBookingID();
			//insert the ticket into the database
			ResultSet rsTicketDB = db.runQuery(ticket.insertTicketInDB());
			//add the bookingID into the ticket table(for the last row)
			ResultSet rsBookingIntoTicketDB = db.runQuery(ticket.addBookingIntoTicket());
			//clear the basket
			basket.clearBasket();
			//thank the customer 
			parser.bye();

		} catch (Exception e) {//handle the exception and print to the client
			System.out.println("An error occured in the main engine of the App.\n"
					+ "Please bear with us while we are fixing it.");
			e.printStackTrace();//DELETE THIS BEFORE SUBMISSION
		}

	}

}
