package com.SpringTesting.TicketBookingRestApp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import com.SpringTesting.TicketBookingRestApp.entity.Ticket;
import com.SpringTesting.TicketBookingRestApp.service.TicketBookingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value=TocketBookingController.class)
public class TocketBookingControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TicketBookingService ticketBookingService;
	
	@Test
	public void testCreateTicket() throws Exception {
		Ticket ticket1 = getRandomTicket();
		
		String inputInJson = mapToJson(ticket1);
		String URI = "/api/tickets/create";
		
		Mockito.when(ticketBookingService.createTicket(Mockito.any(Ticket.class))).thenReturn(ticket1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.post(URI)
											.accept(MediaType.APPLICATION_JSON)
											.content(inputInJson)
											.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	
	@Test
	public void testGetTicketById() throws Exception {
		Ticket ticket1 = getRandomTicket();
		
		Mockito.when(ticketBookingService.getTicketById(Mockito.anyInt())).thenReturn(ticket1);
		
		String URI = "/api/tickets/ticketId/1";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI)
															.accept(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedJson = mapToJson(ticket1);
		String outputInJson = mvcResult.getResponse().getContentAsString();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
													
	}
	
	
	@Test
	public void testGetAllTickets() throws Exception {
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
		
		Mockito.when(ticketBookingService.getAllTickets()).thenReturn(ticketList);
		
		String URI = "/api/tickets/allTickets";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI)
															.accept(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedJson = mapToJson(ticketList);
		String outputInJson = mvcResult.getResponse().getContentAsString();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	
	@Test
	public void testGetTicketByEmail() throws Exception {
		Ticket ticket1 = getRandomTicket();
		
		String expectedJson = mapToJson(ticket1);
		
		Mockito.when(ticketBookingService.getTicketByEmail(Mockito.anyString())).thenReturn(ticket1);
		 
		String URI = "/api/tickets/email/"+ticket1.getEmail();
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		String outputInJson = mvcResult.getResponse().getContentAsString();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	
	private String mapToJson(Object object) throws JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		
		return objectMapper.writeValueAsString(object);
		
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
