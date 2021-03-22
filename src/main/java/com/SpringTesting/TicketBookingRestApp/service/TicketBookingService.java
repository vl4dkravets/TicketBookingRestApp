package com.SpringTesting.TicketBookingRestApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringTesting.TicketBookingRestApp.dao.TicketBookingDao;
import com.SpringTesting.TicketBookingRestApp.entity.Ticket;

@Service
public class TicketBookingService {
	
	private TicketBookingDao ticketBookingDao;

	@Autowired
	public TicketBookingService(TicketBookingDao ticketBookingDao) {
		super();
		this.ticketBookingDao = ticketBookingDao;
	};
	
	public Ticket createTicket(Ticket ticket1) {
		return ticketBookingDao.save(ticket1);
	}
	
	public Ticket getTicketById(Integer id) {
		Optional<Ticket> ticketFromDb = ticketBookingDao.findById(id);
		Ticket ticket1 = null;
		if(ticketFromDb.isPresent()) {
			ticket1 = ticketFromDb.get();
		}
		else {
			return null;
		}
		return ticket1;
	}
	
	public Iterable<Ticket> getAllTickets() {
		return ticketBookingDao.findAll();
	}
	
	public void deleteTicket(Integer id) {
		ticketBookingDao.deleteById(id);
	}
	
	public Ticket updateTicket(Integer ticketId, String newEmail) {
		Optional<Ticket> ticketFromDb = ticketBookingDao.findById(ticketId);
		Ticket ticket1 = null;
		if(ticketFromDb.isPresent()) {
			ticket1 = ticketFromDb.get();
		}
		else {
			return null;
		}
		
		ticket1.setEmail(newEmail);
		
		return ticketBookingDao.save(ticket1);
	}

	public Ticket getTicketByEmail(String email) {
		return ticketBookingDao.findByEmail(email);
	}
}
