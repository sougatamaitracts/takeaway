package com.casestudy.takeaway.eventsvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.takeaway.eventsvc.entity.Event;
import com.casestudy.takeaway.eventsvc.exception.InvalidInputException;
import com.casestudy.takeaway.eventsvc.service.EventService;
import com.casestudy.takeaway.eventsvc.util.ExceptionBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class EventController {

	@Autowired
	ExceptionBuilder exceptionBuilder;
	
	@Autowired
	EventService eventService;
	
	@ApiOperation(value = "Fetch list of Events", response = Page.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Events successfully retrieved"),
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	        @ApiResponse(code = 400, message = "Input is not valid")
	        
	})
	@GetMapping(value = "/events", params = { "direction", "resourcename","resourceid"},produces = { MediaType.APPLICATION_JSON_VALUE },headers = "Accept=application/json")
	@ResponseBody
	public List<Event> listSortedEvent(@RequestParam("direction") String direction, @RequestParam("resourcename") String resourceName,@RequestParam("resourceid") Long id) {
		boolean isValid = isValidInput(direction);
		if(!isValid) {
			InvalidInputException invalidInputException = exceptionBuilder.invalidSearchDirectionExceptionBuilder();
			throw invalidInputException;
		}
		List<Event> list = eventService.findEventsOnResource(direction, resourceName,id);
		
		return list;
	}
	
	private boolean isValidInput(String direction) {
		boolean isValid = true;
		if(!(Direction.ASC.name().equalsIgnoreCase(direction) || Direction.DESC.name().equalsIgnoreCase(direction))){
			isValid = false;
		}
		return isValid;
	}

}
