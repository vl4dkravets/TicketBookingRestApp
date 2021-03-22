package com.SpringTesting.TicketBookingRestApp.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.SpringTesting.TicketBookingRestApp.entity.Ticket;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TicketBookingDaoTest {

	private TestEntityManager testEntitymanager;
	private TicketBookingDao ticketBookingDao;

	@Autowired
	public TicketBookingDaoTest(TestEntityManager testEntitymanager, TicketBookingDao ticketBookingDao) {
		super();
		this.testEntitymanager = testEntitymanager;
		this.ticketBookingDao = ticketBookingDao;
	}
	
	@Test
	public void testSaveTicket() {
		Ticket ticket1 = getRandomTicket();
		
		Ticket persistedTicket = testEntitymanager.persist(ticket1);
		Optional<Ticket> retrievingTicket = ticketBookingDao.findById(ticket1.getTicketId());
		
		assertThat(retrievingTicket.get()).isEqualTo(persistedTicket);
	}
	
	@Test
	public void testGetTicketById() {
		Ticket ticket1 = getRandomTicket();
		
		
		Ticket persistedTicket = testEntitymanager.persist(ticket1);
		Optional<Ticket> retrievingTicket = ticketBookingDao.findById(ticket1.getTicketId());
		
		assertThat(retrievingTicket.get()).isEqualTo(persistedTicket);

	}
	
	@Test
	public void testGetAllTickets() {
		Ticket ticket1 = getRandomTicket();
		
		Ticket ticket2 = new Ticket();
		ticket2.setPassengerName("Ray");
		ticket2.setSourceStation("Detroit");
		ticket2.setDestStation("Texas");
		ticket2.setBookingDate(new Date().toString());
		ticket2.setEmail("Ray@Detroit.com");
		
		testEntitymanager.persist(ticket1);
		testEntitymanager.persist(ticket2);
		
		Iterable<Ticket> allTicketsFromDb = ticketBookingDao.findAll();
		List<Ticket> ticketList = new ArrayList<>();
		
		allTicketsFromDb.forEach(ticketList::add);
		
		assertThat(ticketList.size()).isEqualTo(2);

	}
	
	@Test
	public void testFindByEmail() {
		Ticket ticket1 = getRandomTicket();
		
		testEntitymanager.persist(ticket1);
		
		Ticket ticketFromDb = ticketBookingDao.findByEmail(ticket1.getEmail());
		assertThat(ticketFromDb.getPassengerName()).isEqualTo(ticket1.getPassengerName());
	}
	
	@Test
	public void testDeleteTicketId() {
		Ticket ticket1 = getRandomTicket();
		
		Ticket ticket2 = new Ticket();
		ticket2.setPassengerName("Jay");
		ticket2.setSourceStation("San Francisco");
		ticket2.setDestStation("Mexico City");
		ticket2.setBookingDate(new Date().toString());
		ticket2.setEmail("Jay@San_Francisco.com");
		
		Ticket persistedTicket = testEntitymanager.persist(ticket1);
		testEntitymanager.persist(ticket2);
		
		testEntitymanager.remove(ticket2);
		
		Iterable<Ticket> allTicketsFromDb = ticketBookingDao.findAll();
		List<Ticket> ticketList = new ArrayList<>();
		
		allTicketsFromDb.forEach(ticketList::add);
		
		assertThat(ticketList.size()).isEqualTo(1);
		assertThat(ticketList.get(0).getPassengerName()).isEqualTo(ticket1.getPassengerName());
	}
	
	@Test
	public void testUpdateTicket() {
		Ticket ticket1 = getRandomTicket();
		
		testEntitymanager.persist(ticket1);
		
		Ticket getFromDb = ticketBookingDao.findByEmail(ticket1.getEmail());
		getFromDb.setEmail("Elton@Stockholm.com");
		testEntitymanager.persist(getFromDb);
		
		assertThat(ticketBookingDao.findByEmail("Elton@Stockholm.com").getEmail())
					.isEqualTo("Elton@Stockholm.com");
	}
	
	private Ticket getRandomTicket() {
		Ticket ticket1 = new Ticket();
		ticket1.setPassengerName("John");
		ticket1.setSourceStation("Tokyo");
		ticket1.setDestStation("Madrid");
		ticket1.setBookingDate(new Date().toString());
		ticket1.setEmail("john@tokyo.com");
		
		return ticket1;
	}
	
	
	
}
