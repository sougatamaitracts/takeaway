package com.casestudy.takeaway.service;

import com.casestudy.takeaway.entity.Employee;

public interface EmployeeService {
	public Employee createEmployee(Employee employee);
	public Employee findEmployeeById(Long id);
	public void deleteEmployee(Long employee);
	public Employee updateEmployee(Employee employee,Long id);
	

}
