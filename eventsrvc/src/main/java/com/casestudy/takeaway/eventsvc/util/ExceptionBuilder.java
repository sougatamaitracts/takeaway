package com.casestudy.takeaway.eventsvc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.casestudy.takeaway.eventsvc.exception.InvalidInputException;



@Component
public class ExceptionBuilder {
	@Autowired
	ErrorMessagesReader messageReader;
	
	public InvalidInputException invalidSearchDirectionExceptionBuilder() {
		InvalidInputException invalidInputException = new InvalidInputException();
		String errorMessage = messageReader.get(EventServiceConstant.INVALID_SEARCH_DIRECTION_ERR_MSG_KEY, null);
		String errorCode = messageReader.get(EventServiceConstant.INVALID_SEARCH_DIRECTION_ERR_CODE_KEY, null);
		invalidInputException.setErrorCode(errorCode);
		invalidInputException.setErrorMessage(errorMessage);
		return invalidInputException;

	}

}
