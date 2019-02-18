package com.casestudy.takeaway.eventsvc.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="EVENT_TAB")
public class Event {

	@ApiModelProperty(notes = "The database generated Event ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="EVENT_NAME")
	@ApiModelProperty(notes = "Name of the Event")
	private String eventName;
	@Column(name="EVENT_SOURCE")
	private String eventSourceServiceName;
	@Column(name="EVENT_TIME")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:SSS")
	private Date eventOccuranceTime;
	@Column(name="RESOURCE_NAME")
	private String resourceName;
	@Column(name="RESOURCE_ID")
	private Long resourceIdentifier;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
	        name="EVENT_ATTRIBUTES_TAB",
	        joinColumns=@JoinColumn(name="EVENT_ID")
	  )
	
	
	List<EventAttributes> eventAttributes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventSourceServiceName() {
		return eventSourceServiceName;
	}
	public void setEventSourceServiceName(String eventSourceServiceName) {
		this.eventSourceServiceName = eventSourceServiceName;
	}
	public Date getEventOccuranceTime() {
		return eventOccuranceTime;
	}
	public void setEventOccuranceTime(Date eventOccuranceTime) {
		this.eventOccuranceTime = eventOccuranceTime;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Long getResourceIdentifier() {
		return resourceIdentifier;
	}
	public void setResourceIdentifier(Long resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}
	public List<EventAttributes> getEventAttributes() {
		return eventAttributes;
	}
	public void setEventAttributes(List<EventAttributes> eventAttributes) {
		this.eventAttributes = eventAttributes;
	}
	
	
}
