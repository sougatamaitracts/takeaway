package com.casestudy.takeaway.util;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
/**
 * This class is responsible for reading messages from a properties file 
 * @author Admin
 * @version 1.0
 */
@Component
public class ErrorMessagesReader {
	@Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
    }

    public String get(String code,Object[] args) {
    	String message =  accessor.getMessage(code, args);
    	return message;
    }
}
