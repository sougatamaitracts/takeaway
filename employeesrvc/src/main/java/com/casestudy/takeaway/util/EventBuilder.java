package com.casestudy.takeaway.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.casestudy.takeaway.event.model.EventModel;
/**
 * This class is responsible for build Event data for a each event.
 * @author Admin
 * @version 1.0
 */
@Configuration
public class EventBuilder {
	
	
	@Value("${spring.application.name}")
	private String sourceApplicationName;
	
	public EventModel employeeEventBuilder(String eventName, String resourceName, Long id) {
		EventModel eventModel = new EventModel();
		eventModel.setEventName(eventName);
		eventModel.setResourceName(resourceName);
		eventModel.setResourceIdentifier(id);
		eventModel.setEventSourceServiceName(sourceApplicationName);
		eventModel.setEventOccuranceTime(new Date());
		return eventModel;
	}
	

}
