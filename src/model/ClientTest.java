package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class ClientTest {

	
	@Test
	void buyTest() throws FileNotFoundException {
		//fail("Not yet implemented");
		
		Client c = new Client();
        Schedule s = new Schedule();
        c.navigateSchedule(s);
        assertEquals(80,s.getNoAvailableCircleTickets(5));
        c.buyStallTicket(10,5);
        assertEquals(100,s.getNoAvailableStallTickets(5));
        assertEquals(0,s.getNoCircleTicketsSold(1));
        c.buyStallTicket(10,7);
        assertEquals(110, s.getNoStallTicketsSold(7));
        assertEquals(10, c.getNumStallTicketsBought());
        assertEquals(10, s.getNoStallTicketsSold(7));
        assertEquals(110, s.getNoStallTicketsSold(7));
        c.buyCircleTicket(10,2);
        assertEquals(13, c.getNumCircleTicketsBought());
        assertEquals(23,c.getNumCircleTicketsBought());
		
	}
 
	void getBillTest() throws FileNotFoundException{
		Client c = new Client();
		Schedule s = new Schedule();
		Performance p = new Performance();
		c.navigateSchedule(s);
		c.navigatePerformance(p);
		//c.buyCircleTicket(5,2);
		//assertEquals(50, c.getBill());
		
	}


}
