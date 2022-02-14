package controller;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

import model.Basket;
import model.Booking;
import model.Client;
import model.Performance;
import model.Schedule;

import java.io.FileNotFoundException;
import util.DBConnector;
import util.Parser;

/**
 * 
 * @author L@S Group
 * date:12-01-2022
 * a class for the second application that is for the theatre employees
 */
public class Admin {
	private static DBConnector conn ;//declare a connection object
	private Performance performance;//declare a Performance object
	private static Scanner scanner;//Declare a scanner object

	
	
	/**
	 * @Constructor for Admin
	 * @throws FileNotFoundException
	 */
	public Admin() throws FileNotFoundException {
	
		conn = new DBConnector();//initialise thye connection
		scanner = new Scanner(System.in);//initialise the scanner object
	}

	/**
	 * @param Insert Performance Method for the Theatre employees
	 * @throws ParseException
	 * @throws FileNotFoundException
	 */
	public void insertPerformance()throws ParseException, FileNotFoundException {
                                             
		//a try and catch block to handle exceptions should they happen
		try {
			conn.connect();//connect to the database
			
			//input title prompt
		System.out.println("Input the Title");
		//store the input in a variable
		String title = scanner.next();
		scanner.nextLine();
		
		//prompt the admin to input the performance type
		System.out.println("Input the Performance type(Musical, Theatre, Opera or Concert)");
		//declare a new variable and set it to an empty String
		String performanceType = "";
		//while there is still input from the admin 
		while(scanner.hasNextLine()) {
			//set performanceType to be whatever the admin inputs
			performanceType = scanner.next();
			//if they input any of the Performance types then move on
			if(performanceType.equals("Musical") || performanceType.equals("Theatre") || performanceType.equals("Opera")
					|| performanceType.equals("Concert")) {
				
				break;
		}else {//if not remind them that they need to input one of the 4 types
			System.out.println("Performances can only be of type Musical, Theatre, Opera or Concert");
		}
	
		}
		
		
		//prompt the admin to input the language of the performance
		System.out.println("Input the Language of the Performance. concerts dont have a language");
		//store that in a variable
		String language = scanner.next();
		//eat off the space
		scanner.nextLine();
		
		//prompt the admin to input whether the performance has live music or not
		System.out.println("Does it have live music (Concerts have live music but Musicals and Operas might or might not)");
		//store the input in a variable
		String input = "";
		boolean liveMusic = false;
		while(scanner.hasNextLine()) {
			input = scanner.nextLine();
			if(input.equals("yes")) {
				liveMusic = true;
				System.out.println("Please add a performer");
				String performer = scanner.nextLine();
				//ADD A PERFORMER
				//break out of the loop
				break;
				//else if the input is no then 
			}else if(input.equals("no")){
				//set live music to false 
				liveMusic = false;
				//break out of the loop
				break;
			}else {//ask the Admin  to only input  yes or no 
				System.out.println("Please input yes or no");
			}
			
		}
		scanner.nextLine();
		
		//prompt the admin to input the description for the performance
		System.out.println("Input the decription of the Performance ");
		//store that in a variable
		String description = scanner.next();
		scanner.nextLine();
		
		//prompt the admin to input the duration in minutes
		System.out.println("Input the duration in minutes");
		//store that in a variable
		int durationInMinutes = scanner.nextInt();
		scanner.nextLine();
		
		//prompt the admin to input the stall ticket price
		System.out.println("Input the Stall ticket price");
		//store that in a variable
		double stallPrice = scanner.nextDouble();
		scanner.nextLine();
		
		//prompt the admin to input the circle ticket price
		System.out.println("Input the Circle ticket price");
		//store that in a variable
		double circlePrice = scanner.nextDouble();
		scanner.nextLine();
		
	
		//build up an SQL insert Query from what the Admin has inputted
		String sql = "INSERT INTO Performance (title, performancetype, "
				+ "performanceLanguage, liveMusic, performanceDescription, "
				+ "duration_in_minutes, stallPrice, circlePrice)"
				+ "VALUES (";
		
		sql = sql +"'" + title +"',";
		sql = sql +"'"+ performanceType +"',";
		sql = sql +"'"+ language+"',";
		sql = sql + liveMusic + ",";
		sql = sql +"'"+ description+"',";
		sql = sql + durationInMinutes+ ",";
		sql = sql + stallPrice + ",";
		sql = sql + circlePrice+ ")";
		//return the built up String
		ResultSet rst = conn.runQuery(sql);
		//print what the Admin has added for them to double check they inserted everything correctly 
		conn.printResults(rst);
		//close the scanner
		scanner.close();
		//close the connection
		conn.close();
		
		}//handle the exception if any
		catch (Exception e) {//let the admin know that an error occured and print what the error is 
			System.out.println("An error happened while inserting the performance into the databse");
			e.printStackTrace();//DELETE THIS BEFORE SUBMISSION
		}
		
		 
	}
	
	
	/**
	 * The Admin main method 
	 * @param args
	 * @throws ParseException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws ParseException, SQLException, FileNotFoundException {
	
			Admin admin = new Admin();
			Client c = new Client();
		    //conn.adminConnect();
			Scanner scan = new Scanner(System.in);
			
			Performance performance = new Performance();
			Booking b =new Booking();
			Schedule s =new Schedule();
			conn.connect();
			//c.navigatePerformance(performance);
			//c.navigateSchedule(s);
			//c.buyStallTicket(10, 1);
			//s.sellStallTickets(10,2);
			//performance.changeStallPrice("Idomeneo",100);
			
			//performance.changeCirclePrice("Back to the future",1);
			
			//prompt the client to choose an option from what they have been offered 
			System.out.println("Type in 'insert' to insert an event, 'delete' to delete an event,\n"
					+ "'update stall' to update the stall price for a schedule or 'update circle' \n"
					+ "to update the circle price for a schedule");
				//declare a new variable input	 
			String input = "";
			//while the input isnt any of these 
			while(!input.equals("insert") ||!input.equals("delete") ||!input.equals("update stall")
					||!input.equals("update circle")) {
				//assign input to whatever the admin inputs 
				input = scanner.nextLine();
				//if they input insert then 
				if(input.equals("insert")) {
					//insert the performance 
					admin.insertPerformance();
					//break
					break;
					//if the input is delete then 
				}else if(input.equals("delete")) {
					//ask them what performance they want to delete 
					System.out.println("please input the title of the performance you want to delete");
					//declare a new variable 
					String title = "";
					//assign it whatever the admin inputs 
					title = scanner.nextLine();
					//delete the performance 
					performance.deletePerformance(title);
					//break 
					break;
					//if the input is update stall
				}else if(input.equals("update stall")) {
					//ask what title they want to change the price for  
					System.out.println("What title do you want to update the stall price for");
					//declare a new variable 
					String updateTitleStall = "";
					//assign it whatever the admin inputs 
					updateTitleStall = scanner.nextLine();
					//ask the admin to input the price 
					System.out.println("please input the new price");
					//declare a new variable 
					double newPrice ;
					//assign it whatever the admin inputs 
					newPrice = scanner.nextDouble(); 
					//change the stall price
					performance.changeStallPrice(updateTitleStall,newPrice);
					//break 
					break;
					//if they input update circle
				}else if(input.equals("update circle")) {
					//ask what title they want to change the Circle price for
					System.out.println("What title you want to update the Circle price for");
					//declare a local variable updateTitleCircle and set it to an empty String
					String updateTitleCircle = "";
					//set updateTitleCircle to be what the Admin typed
					updateTitleCircle = scanner.nextLine();
					//prompt the Admin to insert the new price 
					System.out.println("please input the new price");
					//declare a local variable newCirclePrice 
					double newCirclePrice ;
					//set newCirclePrice to be what the Admin typed
					newCirclePrice = scanner.nextDouble();
					//run the method based on what they want 
					performance.changeCirclePrice(updateTitleCircle,newCirclePrice);
					//break out of the loop
					break;
					
					//System.out.println("Type in the title of the performance you want to delete");
			
			//String title = "";
				}
					//if the admin types something other than insert or delete then tell them they can only type either
					System.out.println("Please type in 'insert' or 'delete' ");
				}
			scanner.close();
				
			}
			
	
	
	
	
	
			
	}		


