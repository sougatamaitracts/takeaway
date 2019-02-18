package com.casestudy.takeaway.event.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventModel {
	
	private String eventName;
	private String eventSourceServiceName;
	private Date eventOccuranceTime;
	private String resourceName;
	private Long resourceIdentifier;
	List<EventAttributes> eventAttributes;
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Date getEventOccuranceTime() {
		return eventOccuranceTime;
	}
	public void setEventOccuranceTime(Date eventOccuranceTime) {
		this.eventOccuranceTime = eventOccuranceTime;
	}
	public List<EventAttributes> getEventAttributes() {
		return eventAttributes;
	}
	public void setEventAttributes(List<EventAttributes> eventAttributes) {
		this.eventAttributes = eventAttributes;
	} 
	
	public EventModel buildEventAttribute(String attributeName,String attributeValue) {
		if(this.getEventAttributes()==null) {
			this.setEventAttributes(new ArrayList<EventAttributes>());
		}
		EventAttributes eventAttribute = new EventAttributes();
		eventAttribute.setAttributeName(attributeName);
		eventAttribute.setAttributeValue(attributeValue);
		this.getEventAttributes().add(eventAttribute);
		return this;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getEventSourceServiceName() {
		return eventSourceServiceName;
	}
	public void setEventSourceServiceName(String eventSourceServiceName) {
		this.eventSourceServiceName = eventSourceServiceName;
	}
	public Long getResourceIdentifier() {
		return resourceIdentifier;
	}
	public void setResourceIdentifier(Long resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}
	

}
