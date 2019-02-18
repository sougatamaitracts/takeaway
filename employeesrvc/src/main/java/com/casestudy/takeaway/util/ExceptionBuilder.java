package com.casestudy.takeaway.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.casestudy.takeaway.exception.ResourceNotFoundException;
import com.casestudy.takeaway.exception.ServiceBusinessException;
/**
 * This class is responsible for building exception message.
 * @author Admin
 * @version 1.0
 */
@Component
public class ExceptionBuilder {
	@Autowired
	ErrorMessagesReader messageReader;
    /**
     * This method is responsible for building Exception which has to be thrown when there is no resource found for an identifier.
     * @param id Id of the Resource
     * @return
     */
	public ResourceNotFoundException noResourceFoundExceptionBuilder(String id) {
		ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException();
		String args[] = new String[1];
		args[0] = id;
		String errorMessage = messageReader.get(EmployeeServiceConstant.EMP_NOT_FOUND_ERR_MSG_KEY, args);
		String errorCode = messageReader.get(EmployeeServiceConstant.EMP_NOT_FOUND_ERR_CODE_KEY, null);
		resourceNotFoundException.setErrorCode(errorCode);
		resourceNotFoundException.setErrorMessage(errorMessage);
		return resourceNotFoundException;

	}
	/**
	 * This method is responsible for building Exception which has to be thrown when given email id already exists in the system.
	 * @param id Id of the Resource
	 * @return
	 * 
	 */

	public ServiceBusinessException duplicateEmailExceptionBuilder(String emailId) {
		ServiceBusinessException businessException = new ServiceBusinessException();
		String args[] = new String[1];
		args[0] = emailId;
		String errorMessage = messageReader.get(EmployeeServiceConstant.DUPLICATE_EMAIL_ERR_MSG_KEY, args);
		String errorCode = messageReader.get(EmployeeServiceConstant.DUPLICATE_EMAIL_ERR_CODE_KEY, null);
		businessException.setErrorMessage(errorMessage);
		businessException.setErrorCode(errorCode);
		return businessException;
	}

}
