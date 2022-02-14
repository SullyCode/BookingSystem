package util;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;

import model.Basket;
import model.Client;
import model.Schedule;


/**
 * 
 * @author L@S Group
 * date:
 */
public class Parser  {
	Scanner scanner ;//declare a scanner object
	Schedule schedule;//declare a schedule object
	Client client;//declare a client object
	Basket basket;//declare a basket object

	/**
	 * @constructor
	 * @throws FileNotFoundException
	 */
	public Parser() throws FileNotFoundException {
		
		scanner = new Scanner(System.in);//initialise the scanner
		schedule = new Schedule();//initialise the Schedule object
		client = new Client();//initialise the Client object
		basket = new Basket();//initialise the Basket object
	}

	/**
	 * @param welcome print method with a wait
	 * @throws InterruptedException
	 */
	public void welcome() throws InterruptedException {
		System.out.println("Hello and welcome to the Theatre Royal text based application.");
		//a wait
		Thread.sleep(300);

	}
	/**
	 * @param prephrase print method that simulates loading 
	 * @throws InterruptedException
	 */
	public void prePhrase() throws InterruptedException {
		//a wait 
		Thread.sleep(300);
		//a print 
		System.out.print("Here are all the performances that are avilable at the Theatre Royal");
		Thread.sleep(300);
		System.out.print(" >");
		Thread.sleep(300);
		System.out.print(">");
		Thread.sleep(300);
		System.out.print(">");
		Thread.sleep(300);
		System.out.println("\n");
	}

	/**
	 * @param Bye print method
	 */
	public void bye() {
		System.out.println("Thank you for your purchase \n"
				+ "We are looking forward to meeting you at the Theatre Royal");
	}



	/**
	 * @param Choose performance print method with a wait
	 * @throws InterruptedException
	 */
	public void choosePerformancePrint() throws InterruptedException {
		System.out.println("Please choose a title  for all the details OR write 'search by date' if you want a specific performance by date");
		Thread.sleep(300);
		}

	/**
	 * @param loading simulation method with a wait
	 * @throws InterruptedException
	 */
	public void waitPlease() throws InterruptedException {
		Thread.sleep(300);
		System.out.print("L");
		Thread.sleep(300);
		System.out.print("o");
		Thread.sleep(300);
		System.out.print("a");
		Thread.sleep(300);
		System.out.print("d");
		Thread.sleep(300);
		System.out.print("i");
		Thread.sleep(300);
		System.out.print("n");
		Thread.sleep(300);
		System.out.print("g");
		Thread.sleep(300);
		System.out.println("\n");
	}

	
	/**
	 * @param buy prompt method with a wait
	 * @throws InterruptedException
	 */
	public void buyPrompt() throws InterruptedException {
		Thread.sleep(300);
		System.out.println("");
		System.out.println(
				"Write 'circle' if you want a circle ticket OR 'stall' if you want a stall ticket ");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("** Concessionary tickets are offered at a 25% discount if you are under 16 years old or a student **");
	
			
		}
		
	public void buyOrAddPrompt(){
		System.out.println("Write 'buy' if you want to buy these tickets or 'add' to return to main menu to add more ");
		
	}
	
	

	/**
	 * @param A method that prompts the customer to input the ID for the performance's schedule
	 * they are interested in
	 */
	public void idPrompt() {
		System.out.println("Enter the ID number of the performance with your preferred time and date");
		
	}


	/**
	 * @param how many concessionary circles prompt method
	 * 
	 */
	public void howManyConcessionaryCircles() {
		System.out.println("*How many concessionary circle ticket(s) would you like to add to your basket?");
	}
	
	/**
	 * @param how many Adult Circle tickets prompt method
	 * 
	 */
	public void howManyAdultCircles() {
		System.out.println("*How many Non-concessionary circle ticket(s) would you like to add to your basket?");
	}
	
	/**
	 * @param how many concessionary  stall tickets prompt method
	 * 
	 */
	public void howManyConcessionaryStalls() {
		System.out.println("*How many concessionary stall ticket(s) would you like to add to your basket?");
    }
	
	/**
	 * @param how many Adult Stall tickets prompt method
	 * 
	 */
	public void howManyAdultStalls() {
		System.out.println("*How many Non-concessionary stall ticket(s) would you like to add to your basket?");
	}
	
	/**
	 * 
	 * @param a method that takes the details of the customer
	 * @returns the built query String
	 * @throws FileNotFoundException
	 * @throws InterruptedException 
	 */
	public String takeCustomerDetails() throws FileNotFoundException, InterruptedException {
		
		Thread.sleep(300);
		System.out.print("You will now be asked for details to proceed to payment");
		
		Thread.sleep(300);
		System.out.print(" >");
		Thread.sleep(300);
		System.out.print(">");
		Thread.sleep(300);
		System.out.print(">");
		Thread.sleep(300);
		System.out.println("\n");
		
	    //prompt the customer to enter their name
		System.out.println("Please enter your first name");
		//store their input in a variable
		String fName = scanner.next();
		scanner.nextLine();
		
		//prompt the customer
		System.out.println("Please enter your last name");
		//store their input in a variable
		String lName = scanner.next();
		scanner.nextLine();
		
		//prompt the customer
		System.out.println("Please enter your email");
		//store their input in a variable
		String email = "";
		//while there is input still
		 while(scanner.hasNextLine()) {
			 //assign email to whatever the client inputs
			 email = scanner.next();
			 //if what they inputed contains a @ then 
			  if (email.contains("@")) {
				  //break
				  break;
				}//otherwise ask them to provide a proper email address
			  System.out.println("Please input a valid e-mail address");  
		 }
		
		
		//prompt the customer to input the year they were born 
			System.out.println("Please enter the year you were born in the format YYYY.");
			//set isyear to false 
			boolean isyear = false;
			//set year to 0
			int year = 0;
			//while isyear is true 
			while (!isyear) {
				//store their input in a variable
			year = scanner.nextInt();	
			//if year is between 1940 and 200 then 
			if (year>=1940 && year<=2010) {
				//set isyear to true 
				isyear = true;
			}else {
				//else 
				System.out.println("Please input a valid year");
			}	
			}
			
			
		
		//prompt the customer to enter the month they were born 
			System.out.println("Please enter the month you were born in the format MM.");
			//declare ismonth and set it to false 
			boolean ismonth = false;
			//declare and set month to 0
			int month = 0;
			//while ismonth is true 
			while (!ismonth) {
				//store their input in a variable
			month = scanner.nextInt();	
			//if month is between 1 and 12
			if (month>=1 && month<=12) {
				//set is month to true 
				ismonth = true;
			}else {//else
				System.out.println("Please input a valid month between 1 to 12");
			}	
			}
			
		
		//prompt the customer
			System.out.println("Please enter the day you were born in the format DD.");
			boolean isday = false;
			int day = 0;
			while (!isday) {
				//store their input in a variable
			day = scanner.nextInt();	
			if (day>=1 && day<=31) {
				isday = true;
			}else {
				System.out.println("Please input a valid day between 1 to 31");
			}	
			}
		LocalDate dob = LocalDate.of(year, month, day);
		
		//prompt the customer
		System.out.println("Please type in your house number");
		//store their input in a variable
		int houseNo = scanner.nextInt();
		scanner.nextLine();
		
		//prompt the customer
		System.out.println("Please type in your street");
		//store their input in a variable
		String street = scanner.next();
		scanner.nextLine();
		
		//prompt the customer
		System.out.println("Please type in your city");
		//store their input in a variable
		String city = scanner.next();
		scanner.nextLine();
		
		//prompt the customer
		System.out.println("Please type in your country");
		//store their input in a variable
		String country = scanner.next();
		scanner.nextLine();
		
		//prompt the customer
		System.out.println("Please type in your card number.This should be a 16 digit number");
		//declare a new variable
		Long cardNumber = 0l;
		//store their input in a variable
		boolean correctCardNo = false;
		while (!correctCardNo) {
			//store their input in a variable
			cardNumber = scanner.nextLong();
		//if the customer enters a number that is less than 16 numbers then
		if (cardNumber >=1000000000000000l && cardNumber <= 9999999999999999l) {
			correctCardNo = true;
			//come out 
			//return;
		}else {//ask the client to enter 16 digit card number
			System.out.println("please input a 16 digit number");
			//store it in a variable
			cardNumber = scanner.nextLong();
		}
		}
		//declare and initialise card16digits
		String card16digits = ""+cardNumber;
		//set the client's card number to card16digits
		client.setCardNumber(card16digits);
		//take the last 4 digits of the number provided (to be put in the database)
		String last4digits = client.getCardNumber().substring(client.getCardNumber().length() - 4);
		//create an insert query for the database using the details given by the customer
		String sql = "INSERT INTO Client (firstName, lastName, dob, email, "
				+ "houseNo, street, city, country, cardNumber)"
				+ "Values (";
		//sql = sql +"'" +2 +"'";
		sql = sql +"'" +fName +"'";
		sql = sql + ",";
		sql = sql +"'"+ lName +"'";
		sql = sql + ",";
		sql = sql +"'"+ dob +"'";
		sql = sql + ",";
		sql = sql +"'"+ email+"'";
		sql = sql + ",";
		sql = sql + houseNo;
		sql = sql + ",";
		sql = sql +"'" +street+"'";
		sql = sql + ",";
		sql = sql +"'"+ city+"'";
		sql = sql + ",";
		sql = sql +"'"+ country+"'";
		sql = sql + ",";
		sql = sql + last4digits;
		sql = sql + ")";
       //return the built query string
		return sql;
		
	}
	
	



	}
	
	
	
	


