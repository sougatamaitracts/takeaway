package com.casestudy.takeaway.eventsvc.service;

import java.util.List;

import com.casestudy.takeaway.eventsvc.entity.Event;

public interface EventService {
	
	public void storeEvent(Event event);
	public List<Event> findEventsOnResource(String direction,String resourcename,Long id);

}
