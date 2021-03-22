package com.SpringTesting.TicketBookingRestApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringTesting.TicketBookingRestApp.entity.Ticket;
import com.SpringTesting.TicketBookingRestApp.service.TicketBookingService;

@RestController
@RequestMapping(value="api/tickets")
public class TocketBookingController {

	private TicketBookingService ticketBookingService;
	
	@Autowired
	public TocketBookingController(TicketBookingService ticketBookingService) {
		super();
		this.ticketBookingService = ticketBookingService;
	}

	@CrossOrigin
	@PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Ticket createTicket(@RequestBody Ticket ticket1) {
		return ticketBookingService.createTicket(ticket1);
	}
	
	@CrossOrigin
	@GetMapping(value="/ticketId/{ticketId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Ticket getTicketById(@PathVariable("ticketId") Integer ticketId) {
		return ticketBookingService.getTicketById(ticketId);
	}
	
	@CrossOrigin
	@GetMapping(value="/allTickets", produces=MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Ticket> getAllTickets() {
		return ticketBookingService.getAllTickets();
	}
	
	// http://localhost:8080/api/tickets/email/newEmail@adam.com
	@CrossOrigin
	@GetMapping(value="/email/{email:.+}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Ticket getTicketByEmail(@PathVariable("email") String email) {
		return ticketBookingService.getTicketByEmail(email);
	}
	
	
	// http://localhost:8080/api/tickets//ticketId/3
	@CrossOrigin
	@DeleteMapping(value="/ticketId/{ticketId}")
	public void deleteTicket(@PathVariable("ticketId") Integer ticketId) {
		ticketBookingService.deleteTicket(ticketId);
	}
	
	
	// http://localhost:8080/api/tickets//ticketId/2/email/newEmail@adam.com
	@CrossOrigin
	@PutMapping(value="/ticketId/{ticketId}/email/{newEmail:.+}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Ticket updateTicket(@PathVariable("ticketId") Integer ticketId, @PathVariable("newEmail") String newEmail) {
		return ticketBookingService.updateTicket(ticketId, newEmail);
	}
	
}
