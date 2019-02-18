package com.casestudy.takeaway.eventsvc.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EventAttributes {
	@Column(name="ATTRIBUTE_NAME")
	private String attributeName;
	@Column(name="ATTRIBUTE_VALUE")
	private String attributeValue;
	
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

}
