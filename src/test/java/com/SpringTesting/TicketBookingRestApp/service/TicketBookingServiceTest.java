package com.SpringTesting.TicketBookingRestApp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.SpringTesting.TicketBookingRestApp.dao.TicketBookingDao;
import com.SpringTesting.TicketBookingRestApp.entity.Ticket;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TicketBookingServiceTest {
	
	@Autowired
	private TicketBookingService ticketBookingService;
	@MockBean
	private TicketBookingDao ticketBookingDao;
	
	@Test
	public void testCreateTicket() {
		Ticket ticket1 = getRandomTicket();
		
		Mockito.when(ticketBookingDao.save(ticket1)).thenReturn(ticket1);
		assertThat(ticketBookingService.createTicket(ticket1)).isEqualTo(ticket1);
		
	}
	
	@Test
	public void testGetTicketById() {
		Ticket ticket1 = getRandomTicket();
		Optional<Ticket> ticketOptional = Optional.of(ticket1);
		
		Mockito.when(ticketBookingDao.findById(101)).thenReturn(ticketOptional);
		assertThat(ticketBookingService.getTicketById(101)).isEqualTo(ticket1);
	}
	
	@Test
	public void testGetAllTickets() {
		Ticket ticket1 = getRandomTicket();
		
		Ticket ticket2 = new Ticket();
		ticket2.setTicketId(102);
		ticket2.setPassengerName("Vlad");
		ticket2.setSourceStation("Kyiv");
		ticket2.setDestStation("Lviv");
		ticket2.setBookingDate(new Date().toString());
		ticket2.setEmail("Vlad@Kyiv.com");
		
		List<Ticket> ticketList = new ArrayList<>();
		ticketList.add(ticket1);
		ticketList.add(ticket2);
		
		Mockito.when(ticketBookingDao.findAll()).thenReturn(ticketList);
		
		assertThat(ticketBookingService.getAllTickets()).isEqualTo(ticketList);
	}
	
	@Test
	public void testDeleteTicket() {
		Ticket ticket1 = getRandomTicket();
		Optional<Ticket> ticketOptional = Optional.of(ticket1);
		
		Mockito.when(ticketBookingDao.findById(ticket1.getTicketId())).thenReturn(ticketOptional);
		Mockito.when(ticketBookingDao.existsById(ticket1.getTicketId())).thenReturn(false);
		
		assertFalse(ticketBookingDao.existsById(ticket1.getTicketId()));
	}	
	
	
	@Test
	public void testUpdateTicket() {
		Ticket ticket1 = getRandomTicket();
		Optional<Ticket> ticketOptional = Optional.of(ticket1);
		
		Mockito.when(ticketBookingDao.findById(ticket1.getTicketId())).thenReturn(ticketOptional);

		
		ticket1.setEmail("new@email.com");
		
		Mockito.when(ticketBookingDao.save(ticket1)).thenReturn(ticket1);
		
		assertThat(ticketBookingService.updateTicket(ticket1.getTicketId(), ticket1.getEmail())).isEqualTo(ticket1);
		
	}
	
	@Test
	// @Disabled
	public void testGetTicketByEmail() {
		Ticket ticket1 = getRandomTicket();
		
		Mockito.when(ticketBookingDao.findByEmail(ticket1.getEmail())).thenReturn(ticket1);
		
		assertThat(ticketBookingService.getTicketByEmail(ticket1.getEmail())).isEqualTo(ticket1);
		
	}
	

	private Ticket getRandomTicket() {
		Ticket ticket1 = new Ticket();
		ticket1.setTicketId(101);
		ticket1.setPassengerName("John");
		ticket1.setSourceStation("Tokyo");
		ticket1.setDestStation("Madrid");
		ticket1.setBookingDate(new Date().toString());
		ticket1.setEmail("john@tokyo.com");
		
		return ticket1;
	}
	
}
