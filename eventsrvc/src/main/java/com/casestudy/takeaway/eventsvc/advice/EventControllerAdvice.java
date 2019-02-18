package com.casestudy.takeaway.eventsvc.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.casestudy.takeaway.eventsvc.error.model.ApiError;
import com.casestudy.takeaway.eventsvc.exception.InvalidInputException;
import com.casestudy.takeaway.eventsvc.util.EventServiceConstant;


@ControllerAdvice
public class EventControllerAdvice {

	
	@ResponseBody
	@ExceptionHandler(InvalidInputException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleBusinessException(InvalidInputException ex) {
		ApiError res = new ApiError();
		res.setExceptionCode(ex.getErrorCode());
		res.setErrorMessage(ex.getErrorMessage());
		ex.printStackTrace();
		return res;
	}
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError genericException(Exception ex) {
		ApiError res = new ApiError();
		res.setExceptionCode(EventServiceConstant.GENERIC_ERROR_CODE);
		res.setErrorMessage(ex.getMessage());
		ex.printStackTrace();
		return res;
	}

}
