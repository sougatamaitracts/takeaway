package com.casestudy.takeaway.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.casestudy.takeaway.entity.Employee;
import com.casestudy.takeaway.event.model.EventModel;
import com.casestudy.takeaway.event.publisher.EventPublisher;
import com.casestudy.takeaway.repository.EmployeeRepository;
import com.casestudy.takeaway.service.EmployeeService;
import com.casestudy.takeaway.util.EmployeeServiceConstant;
import com.casestudy.takeaway.util.EventBuilder;
import com.casestudy.takeaway.util.ExceptionBuilder;
/**
 * This service implementation is responsible for 
 * creating , modifying , deleting and viewing employee
 * After each event this also send a event notification to a Kafka Server
 * @author Admin
 * @version 1.0
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EventBuilder eventBuilder;
	
	@Autowired
	EventPublisher publisher;
	
	@Autowired
	ExceptionBuilder exceptionBuilder;
	
	/** This method is responsible or validating employee email address and is employee email address is unique 
	 * then it creates a new employee. If new employee is successfully created then a notification is send to event 
	 * logging system
	 * @see com.casestudy.takeaway.service.EmployeeService#createEmployee(com.casestudy.takeaway.entity.Employee)
	 * @param Employee details
	 * @see com.casestudy.takeaway.entity.Employee
	 */
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Employee createEmployee(Employee employee) {
		List<Employee> employeeList = employeeRepository.getByEmail(employee.getEmail());
		if(employeeList!=null && employeeList.size()>0) {
			throw exceptionBuilder.duplicateEmailExceptionBuilder(employee.getEmail());
		}
		employee= employeeRepository.save(employee);
		EventModel eventModel =  eventBuilder.employeeEventBuilder(EmployeeServiceConstant.
										EVENT_EMPLOYEE_CREATE,EmployeeServiceConstant.RESOURCE_EMPLOYEE ,employee.getUuid());
		publishEvent(eventModel);
		return employee;
	}
	/**
	 * @see com.casestudy.takeaway.service.EmployeeService#findEmployeeById(java.lang.Long)
	 * This method checks existence of an employee based on id . If employee found then it returns details of the employee
	 * else throws ResourceNotFoundException
	 */
	@Transactional
	public Employee findEmployeeById(Long id) {
		Optional<Employee> optionalemployee= employeeRepository.findById(id);
		Employee employee = null;
		if(optionalemployee !=null && !optionalemployee.isPresent()) {
			throw exceptionBuilder.noResourceFoundExceptionBuilder(String.valueOf(id));
		}else {
			employee = optionalemployee.get();
			if(employee.getHobbies()!=null && employee.getHobbies().size()==0) {
				employee.setHobbies(null); 
			}
				
		}
		EventModel eventModel =  eventBuilder.employeeEventBuilder(EmployeeServiceConstant.
										EVENT_EMPLOYEE_VIEW,EmployeeServiceConstant.RESOURCE_EMPLOYEE ,employee.getUuid());
		publishEvent(eventModel);
		return employee;
	}
	/**
	 * This method is responsible for deleting an Employee  based on id. 
	 * If no employee exists with this id then it throws an ResourceNotFoundException 
	 * 
	 * @see com.casestudy.takeaway.service.EmployeeService#deleteEmployee(java.lang.Long)
	 */ 
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteEmployee(Long id) {
		Optional<Employee> optionalemployee= employeeRepository.findById(id);
		Employee employee = null;
		if(!optionalemployee.isPresent()) {
			throw exceptionBuilder.noResourceFoundExceptionBuilder(String.valueOf(id));
			
		}else {
			employee = optionalemployee.get();
		}
		employeeRepository.delete(employee);
		EventModel eventModel =  eventBuilder.employeeEventBuilder(EmployeeServiceConstant.
										EVENT_EMPLOYEE_DELETE,EmployeeServiceConstant.RESOURCE_EMPLOYEE ,employee.getUuid());
		publishEvent(eventModel);
	}
	/**
	 * 
	 * @see com.casestudy.takeaway.service.EmployeeService#updateEmployee(com.casestudy.takeaway.entity.Employee, java.lang.Long)
	 * This method is responsible for updation of an Employee. If no employee id is found with a given employee id , then instead of creating 
	 * a new employee it throws an exception just to keep the responsibility of the method defined.
	 * It also check the uniqueness of the employee email id . If duplicate email id i found the it throws an exception. 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class )
	public Employee updateEmployee(Employee employee,Long id) {
		Optional<Employee> optionalemployee= employeeRepository.findById(id);
		Employee existingEmployee = null;
		// Strictly Restricting Creation of Employee Using Put Request
		// 
		if(!optionalemployee.isPresent()) {
			throw exceptionBuilder.noResourceFoundExceptionBuilder(String.valueOf(id));
			
		}else {
			existingEmployee = optionalemployee.get();
		}
		
		List<Employee> employeeList = employeeRepository.getByEmail(employee.getEmail());
		if(employeeList!=null && employeeList.size()>0) {
			throw exceptionBuilder.duplicateEmailExceptionBuilder(String.valueOf(employee.getEmail()));
		}
		existingEmployee.setDateOfBirth(employee.getDateOfBirth());
		existingEmployee.setEmail(employee.getEmail());
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setHobbies(employee.getHobbies());
		employeeRepository.save(existingEmployee);
		EventModel eventModel =  eventBuilder.employeeEventBuilder(EmployeeServiceConstant.EVENT_EMPLOYEE_UPDATE
										,EmployeeServiceConstant.RESOURCE_EMPLOYEE ,id);
		employee.setUuid(id);
		publishEvent(eventModel);
		return employee;
	}
	 
	private void publishEvent(EventModel eventModel) {
		publisher.send(eventModel,String.valueOf(eventModel.getResourceIdentifier()));
		
	}
	
	

}
