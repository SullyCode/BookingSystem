package model;

import static org.junit.Assert.assertEquals;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

class ScheduleTest {

	@Test
	void constructorTest() throws FileNotFoundException {
		// fail("Not yet implemented");
		Schedule s = new Schedule();
		assertEquals(80, s.getNoAvailableCircleTickets(3));
		assertEquals(120, s.getNoAvailableStallTickets(3));
		s.sellCircleTickets(20, 10);
		assertEquals(20, s.getNoStallTicketsSold(10));

	}

	@Test
	void sellTest() throws FileNotFoundException {
		Schedule schedule = new Schedule();
		Client c = new Client();
		schedule.sellCircleTickets(10, 3);
		assertEquals(70, schedule.getNoAvailableCircleTickets(6));
		schedule.sellStallTickets(10, 2);// only 120
		c.buyCircleTicket(20, 10);
		assertEquals(20, schedule.getNoCircleTicketsSold(10));
		assertEquals(60, schedule.getNoAvailableCircleTickets(2));
		schedule.sellStallTickets(80, 2);// only 120
		assertEquals(10, schedule.getNoStallTicketsSold(2));
		assertEquals(40, schedule.getNoAvailableStallTickets(2));
		//assertEquals(100, schedule.getTotalNoTicketsSold(2));

		schedule.sellCircleTickets(60, 2);
		assertEquals(80, schedule.getNoCircleTicketsSold(2));
		assertEquals(00, schedule.getNoAvailableCircleTickets(2));
		schedule.sellStallTickets(40, 2);// reached the limit
		assertEquals(120, schedule.getNoStallTicketsSold(2));
		assertEquals(0, schedule.getNoAvailableStallTickets(2));

		schedule.sellCircleTickets(60, 2);
		assertNotNull("sorry we ran out of circle tickets.");
		schedule.sellStallTickets(21, 2);
		assertNotNull("sorry we ran out of stall tickets.");

}
}