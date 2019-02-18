package com.casestudy.takeaway.eventsvc.consumer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.casestudy.takeaway.eventsvc.entity.Event;
//import com.casestudy.takeaway.eventsvc.entity.Event;
import com.casestudy.takeaway.eventsvc.service.EventService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventReceiver {

	@Autowired
	EventService eventService;

	@KafkaListener(topics = "${kafka.employee.topic}")
	public void receive(String event) {
		ObjectMapper mapper = new ObjectMapper();
		Event eventObj = null;
		try {
			eventObj = mapper.readValue(event, Event.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		eventService.storeEvent(eventObj);
		

	}

}
