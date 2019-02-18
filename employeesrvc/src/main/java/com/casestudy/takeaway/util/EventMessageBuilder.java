package com.casestudy.takeaway.util;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import com.casestudy.takeaway.event.model.EventModel;
import org.springframework.messaging.support.MessageBuilder;

/**This class is responsible for creating message which has to be send to a event logging system
 * 
 * @author Admin
 * @version 1.0
 * @param <T>
 */
@Component
public class EventMessageBuilder <T>{
	/**
	 * This method build an Message for an event. This message contains , the paylod to be logged into the system.
	 * @param eventModel Holds event data and optional parameter and its values to be logged
	 * @param messageKey This is the Key of the message to be pushed into event logging system
	 * @param topicName Name of the Topic of Destination where event has to be logged.
	 * @return
	 */
	public Message<EventModel> buildMessage(EventModel eventModel,String messageKey,String topicName) {
		Message<EventModel> message =MessageBuilder
                .withPayload(eventModel)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.MESSAGE_KEY, messageKey)
                .build();
		return message;
	}

}
