package com.casestudy.takeaway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.takeaway.entity.Employee;
import com.casestudy.takeaway.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class defines all REST Apis to access resources.
 * Secured API's are create / delete / update . Application with write access can only access these API's
 * @author Admin
 * @version 1.0
 *
 */
@Api(value="Employee Maintenance")
@RestController

public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@ApiOperation(value = "Creates a New employee and associate Hobbies", response = Employee.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Created"),
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	        @ApiResponse(code = 400, message = "Bad Request"),
	        @ApiResponse(code = 401, message = "Unauthorized")
	        
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/secured/employee", produces = { MediaType.APPLICATION_JSON_VALUE },consumes={MediaType.APPLICATION_JSON_VALUE})
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeService.createEmployee(employee);
		
	}
	
	@ApiOperation(value = "Retrieved Employee by employee id", response = Employee.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "OK"),
	        @ApiResponse(code = 404, message = "Not Found"),
	        @ApiResponse(code = 500, message = "Internal Server Error")
	})
	
	@GetMapping(value="/employee/{id}",produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public Employee getEmployee (@PathVariable("id") Long id) {
		
		return employeeService.findEmployeeById(id);
	}
	 
	@ApiOperation(value = "Deletes an Employee by its ID")
	@ApiResponses(value = {
	        @ApiResponse(code = 204, message = "No Content"),
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	        @ApiResponse(code = 404, message = "Not Found"),
	        @ApiResponse(code = 401, message = "Unauthorized")
	})
	
	
	@DeleteMapping(value="/secured/employee/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEmployee(@PathVariable("id") Long id)
	{
		employeeService.deleteEmployee(id);
	}
	
	@ApiOperation(value = "Updates state of an existing employee , if Employee does not exists it returns exception", response = Employee.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Ok"),
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	        @ApiResponse(code = 404, message = "Not Found"),
	        @ApiResponse(code = 400, message = "Bad Request"),
	        @ApiResponse(code = 401, message = "Unauthorized")
	})
	@PutMapping(value="/secured/employee/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public Employee updateEmployee(@RequestBody Employee employee,@PathVariable("id") Long id) {
		return employeeService.updateEmployee(employee,id);
	}

}
