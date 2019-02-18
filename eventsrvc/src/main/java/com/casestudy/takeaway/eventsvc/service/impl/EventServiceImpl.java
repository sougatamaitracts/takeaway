package com.casestudy.takeaway.eventsvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.casestudy.takeaway.eventsvc.entity.Event;
import com.casestudy.takeaway.eventsvc.repository.EventRepository;
import com.casestudy.takeaway.eventsvc.service.EventService;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;

	public void storeEvent(Event event) {
		eventRepository.save(event);
	}

	public List<Event> findEventsOnResource(String direction,String resourcename,Long id) {
		Sort sort = null;
		if (Direction.ASC.name().equals(direction)) {
			sort = new Sort(Direction.ASC, "id");
		}
		if (Direction.DESC.name().equals(direction)) {
			sort = new Sort(Direction.DESC, "id");
		}
		List<Event> eventData = eventRepository.findByResourceNameAndResourceIdentifier(resourcename,id,sort);
		return eventData;
	}

}
