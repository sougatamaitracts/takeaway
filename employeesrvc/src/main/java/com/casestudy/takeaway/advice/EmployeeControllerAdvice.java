package com.casestudy.takeaway.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.casestudy.takeaway.error.model.ApiError;
import com.casestudy.takeaway.exception.ServiceBusinessException;
import com.casestudy.takeaway.util.EmployeeServiceConstant;
import com.casestudy.takeaway.exception.ResourceNotFoundException;

/**
 * This Class is responsible for handling exceptions and preparing appropriate message. 
 * @author Admin
 * @version 1.0
 * 
 */
@ControllerAdvice
public class EmployeeControllerAdvice {
	/**
	 * This method handles ResourceNotFoundException  and send HTTP 404 error code 
	 * @param ex
	 * @return
	 */
	
	@ExceptionHandler(value = { ResourceNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ApiError handleResourceNotFoundException(ResourceNotFoundException ex) {
		ApiError res = new ApiError();
		res.setExceptionCode(ex.getErrorCode());
		res.setErrorMessage(ex.getErrorMessage());
		return res;
	}
	
	/**
	 * This method handles ServiceBusinessException  and send HTTP 400 error code 
	 * @param ex ServiceBusinessException
	 * @return ApiError
	 */
	@ResponseBody
	@ExceptionHandler(ServiceBusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleBusinessException(ServiceBusinessException ex) {
		ApiError res = new ApiError();
		res.setExceptionCode(ex.getErrorCode());
		res.setErrorMessage(ex.getErrorMessage());
		return res;
	}
	
	/**
	 * This method handles Exception  and send HTTP 500 error code 
	 * @param ex Exception
	 * @return ApiError
	 */
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError genericException(Exception ex) {
		ApiError res = new ApiError();
		res.setExceptionCode(EmployeeServiceConstant.GENERIC_ERROR_CODE);
		res.setErrorMessage(ex.getMessage());
		ex.printStackTrace();
		return res;
	}
}
