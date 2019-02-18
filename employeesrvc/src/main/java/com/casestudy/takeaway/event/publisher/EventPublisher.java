package com.casestudy.takeaway.event.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.casestudy.takeaway.event.model.EventModel;
import com.casestudy.takeaway.util.EventMessageBuilder;

@Component
public class EventPublisher {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private EventMessageBuilder<EventModel> messageBuilder;

	@Value("${kafka.employee.topic}")
	private String topic;

	/**
	 * 
	 * @param eventName
	 * @param eventModel
	 * @param messageKey
	 */
	public void send( EventModel eventModel, String messageKey) {
		Message<EventModel> message = messageBuilder.buildMessage(eventModel, messageKey, topic);
		kafkaTemplate.send(message);
	}

}
