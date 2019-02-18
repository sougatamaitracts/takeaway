package com.casestudy.takeaway.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.takeaway.entity.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long>{
	public List<Employee> getByEmail(String email );

}
