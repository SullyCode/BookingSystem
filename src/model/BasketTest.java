package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class BasketTest {

	@Test
	void test() throws FileNotFoundException {
	
		Client c = new Client();
		Schedule s = new Schedule();
		Basket b = new Basket();
		c.navigateSchedule(s);
		//c.navigatePerformance(null);
		c.buyCircleTicket(10,39);
		assertEquals(10,s.getNoCircleTicketsSold(39));
		assertEquals(70,s.getNoAvailableCircleTickets(2));
		assertEquals(70, s.getNoAvailableCircleTickets(2));
		assertEquals(10, c.getNumCircleTicketsBought());
		assertEquals(0, c.getNumStallTicketsBought());
		assertEquals(10, c.getTotalTicketsBought());
		b.calculateConcessionary(250);//the full price is 100
		assertEquals(187.5, b.getConcessionary());//we applied the 25 discount
		//so they pay 75

	}
	
	
	
}
