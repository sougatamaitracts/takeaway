package com.casestudy.takeaway.exception;

public class ServiceBusinessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2481417269638480232L;
	String errorMessage; 
	String errorCode;
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
