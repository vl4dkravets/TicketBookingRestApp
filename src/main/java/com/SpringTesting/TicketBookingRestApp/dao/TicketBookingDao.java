package com.SpringTesting.TicketBookingRestApp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.SpringTesting.TicketBookingRestApp.entity.Ticket;


@Repository
public interface TicketBookingDao extends CrudRepository<Ticket, Integer> {

	Ticket findByEmail(String email);
}
