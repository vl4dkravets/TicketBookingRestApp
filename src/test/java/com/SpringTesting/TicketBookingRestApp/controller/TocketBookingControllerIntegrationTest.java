package com.SpringTesting.TicketBookingRestApp.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.SpringTesting.TicketBookingRestApp.TicketBookingRestAppApplication;
import com.SpringTesting.TicketBookingRestApp.entity.Ticket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=TicketBookingRestAppApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TocketBookingControllerIntegrationTest {

	@LocalServerPort
	private int port;
	
	private TestRestTemplate testRestTempalte;
	private HttpHeaders httpHeaders;
	
	@Autowired
	public TocketBookingControllerIntegrationTest(TestRestTemplate testRestTempalte) {
		super();
		this.testRestTempalte = testRestTempalte;
		httpHeaders = new HttpHeaders();
	}
	
	
	@Test
	public void createTicket() throws Exception{
		Ticket ticket1 = getRandomTicket();
		
		String URI = "/api/tickets/create";
		String inputInJson = mapToJson(ticket1);
		
		HttpEntity<Ticket> httpEntity = new HttpEntity<>(ticket1, httpHeaders);
		ResponseEntity<String> response = testRestTempalte.exchange(getFullUrlWithPort(URI), HttpMethod.POST,
																	httpEntity, String.class);
		
		String responseInJson = response.getBody();
		
		assertThat(responseInJson).isEqualTo(inputInJson);		
		
	}
	
	
	@Test
	public void testGetTicketById() throws Exception {
		Ticket ticket1 = getRandomTicket();
		
		String URI = "/api/tickets/ticketId/" + ticket1.getTicketId();
		String URIToCreateTicket = "/api/tickets/create";
		
		// Convert object to JSON
		String inputInJson = mapToJson(ticket1);
		
		// Store object to DB
		HttpEntity<Ticket> httpEntity = new HttpEntity<>(ticket1, httpHeaders);
		testRestTempalte.exchange(getFullUrlWithPort(URIToCreateTicket), HttpMethod.POST, 
									httpEntity, String.class);
		
		// Retrieve object from Db via REST as JSON
		String bodyJsonResponse = testRestTempalte.getForObject(getFullUrlWithPort(URI), String.class);
		
		// COmpare initial and final results in JSON format
		assertThat(bodyJsonResponse).isEqualTo(inputInJson);
	}
	
	
	@Test
	public void testGetTicketByEmail() throws Exception {
		Ticket ticket1 = getRandomTicket();
		
		String URI = "/api/tickets/email/"+ticket1.getEmail();
		String URIToCreateTicket = "/api/tickets/create";
		
		String inputAsJson = mapToJson(ticket1);
		
		HttpEntity<Ticket> httpEntity = new HttpEntity<>(ticket1, httpHeaders);
		testRestTempalte.exchange(getFullUrlWithPort(URIToCreateTicket), HttpMethod.POST, httpEntity, String.class);
		
		String bodyJsonResponse = testRestTempalte.getForObject(URI, String.class);
		
		assertThat(bodyJsonResponse).isEqualTo(inputAsJson);
		
	}
		
	
	private String getFullUrlWithPort(String URI) {
		return "http://localhost:" + port + URI;
	}
	
	
	private String mapToJson(Object object) throws JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		
		return objectMapper.writeValueAsString(object);
		
	}	
	
	private Ticket getRandomTicket() {
		Ticket ticket1 = new Ticket();
		ticket1.setTicketId(1);
		ticket1.setPassengerName("John");
		ticket1.setSourceStation("Tokyo");
		ticket1.setDestStation("Madrid");
		ticket1.setBookingDate(new Date().toString());
		ticket1.setEmail("john@tokyo.com");
		
		return ticket1;
	}
	
}
