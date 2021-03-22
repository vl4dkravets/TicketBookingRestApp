package com.SpringTesting.TicketBookingRestApp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ticket_table")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ticket_id")
	private Integer ticketId;
	
	@Column(name="passenger_name", nullable=false)
	private String passengerName;
	
	@Column(name="booking_date", nullable=false)
	private String bookingDate;
	
	@Column(name="source_station", nullable=false)
	private String sourceStation;
	
	@Column(name="destination_station", nullable=false)
	private String destStation;
	
	@Column(name="email", unique=true)
	private String email;

	public Ticket(Integer ticketId, String passengerName, String bookingDate, String sourceStation, String destStation,
			String email) {
		super();
		this.ticketId = ticketId;
		this.passengerName = passengerName;
		this.bookingDate = bookingDate;
		this.sourceStation = sourceStation;
		this.destStation = destStation;
		this.email = email;
	}
	
	public Ticket() {}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getSourceStation() {
		return sourceStation;
	}

	public void setSourceStation(String sourceStation) {
		this.sourceStation = sourceStation;
	}

	public String getDestStation() {
		return destStation;
	}

	public void setDestStation(String destStation) {
		this.destStation = destStation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", passengerName=" + passengerName + ", bookingDate=" + bookingDate
				+ ", sourceStation=" + sourceStation + ", destStation=" + destStation + ", email=" + email + "]";
	}
	
}


