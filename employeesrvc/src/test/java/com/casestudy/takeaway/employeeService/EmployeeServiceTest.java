package com.casestudy.takeaway.employeeService;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.casestudy.takeaway.entity.Employee;
import com.casestudy.takeaway.event.model.EventModel;
import com.casestudy.takeaway.event.publisher.EventPublisher;
import com.casestudy.takeaway.exception.ResourceNotFoundException;
import com.casestudy.takeaway.exception.ServiceBusinessException;
import com.casestudy.takeaway.repository.EmployeeRepository;
import com.casestudy.takeaway.service.impl.EmployeeServiceImpl;
import com.casestudy.takeaway.util.EventBuilder;
import com.casestudy.takeaway.util.ExceptionBuilder;

/**
 * This contains unit test cases for Employee Service
 * @author Admin
 *
 */
 
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
	
	@InjectMocks
	private EmployeeServiceImpl targetService;

	@Mock
	private EmployeeRepository repository;
	
	@Mock
	private EventBuilder mockEventBuilder;
	
	@Mock
	EventPublisher mockPublisher;
	
	@Mock
	ExceptionBuilder mockExceptionBuilder;
	
	@Test
	public void testCreateEmployee_positive() {
		Employee employee = new Employee();
		employee.setDateOfBirth(new Date());
		employee.setUuid(1l);
		employee.setFirstName("SampleName");
		employee.setLastName("lastname");
		employee.setEmail("sample@sample.com");
		EventModel eventModel = new EventModel();
		when(repository.save(employee)).thenReturn((employee));
		when(repository.getByEmail(employee.getEmail())).thenReturn(null);
		when(mockEventBuilder.employeeEventBuilder("employeeCreated", "employee", 1l)).thenReturn(eventModel);
		targetService.createEmployee(employee);
		assertEquals(1, employee.getUuid().longValue());
		
	}


	@Test(expected=Exception.class)
	public void testCreateEmployee_exception() {
		Employee employee = new Employee();
		employee.setDateOfBirth(new Date());
		employee.setUuid(1l);
		employee.setFirstName("SampleName");
		employee.setLastName("lastname");
		employee.setEmail("sample@sample.com");
		List <Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee);
		when(repository.getByEmail(employee.getEmail())).thenReturn(employeeList);
		ServiceBusinessException buisnessExeption = new ServiceBusinessException();
		when(mockExceptionBuilder.duplicateEmailExceptionBuilder(employee.getEmail())).thenReturn(buisnessExeption);
		targetService.createEmployee(employee);
		
	}
	
	@Test
	public void testFindEmployeeById_positive() {
		EventModel eventModel = new EventModel();
		Employee employee = new Employee();
		employee.setDateOfBirth(new Date());
		employee.setUuid(1l);
		employee.setFirstName("SampleName");
		employee.setLastName("lastname");
		employee.setEmail("sample@sample.com");
		Optional<Employee> employeeOptional = Optional.of(employee);
		
		when(repository.findById(1l)).thenReturn(employeeOptional);
		when(mockEventBuilder.employeeEventBuilder("employeeViewed", "employee", 1l)).thenReturn(eventModel);
		targetService.findEmployeeById(1l);
		assertEquals(1l, employee.getUuid().longValue());
		
		
	}
	
	@Test(expected=ResourceNotFoundException.class)
	public void testFindEmployeeById_negetive() {
		Optional<Employee> employeeOptional = Optional.empty();
		when(repository.findById(1l)).thenReturn(employeeOptional);
		ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException();
		when(mockExceptionBuilder.noResourceFoundExceptionBuilder("1")).thenReturn(resourceNotFoundException);
		targetService.findEmployeeById(1l);
		
	}
	
	@Test(expected=ResourceNotFoundException.class)
	public void testDeleteEmployee_exception() {
		Optional<Employee> employeeOptional = Optional.empty();
		when(repository.findById(1l)).thenReturn(employeeOptional);
		ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException();
		when(mockExceptionBuilder.noResourceFoundExceptionBuilder("1")).thenReturn(resourceNotFoundException);
		targetService.deleteEmployee(1l);
		
	}
	
	@Test
	public void testDeleteEmployee_positive() {
		Employee employee = new Employee();
		employee.setUuid(1l);
		Optional<Employee> employeeOptional = Optional.of(employee);
		when(repository.findById(1l)).thenReturn(employeeOptional);
		EventModel eventModel = new EventModel();
		eventModel.setResourceIdentifier(1l);
		when(mockEventBuilder.employeeEventBuilder("employeeDeleted", "employee", 1l)).thenReturn(eventModel);
		targetService.deleteEmployee(1l);
		verify(mockPublisher).send(eventModel, String.valueOf(eventModel.getResourceIdentifier()));
	}
 
	@Test
	public void testUpdate_positive() {
		Employee employee = new Employee();
		employee.setUuid(1l);
		employee.setEmail("sample@sample.com");
		employee.setFirstName("sampleName");
		employee.setLastName("lname");
		
		Optional<Employee> employeeOptional = Optional.of(employee);
		when(repository.findById(1l)).thenReturn(employeeOptional);
		when(repository.getByEmail(employee.getEmail())).thenReturn(null);
		when(repository.save(employee)).thenReturn(employee);
		EventModel eventModel = new EventModel(); 
		eventModel.setResourceIdentifier(1l);
		when(mockEventBuilder.employeeEventBuilder("employeeUpdated", "employee", 1l)).thenReturn(eventModel);
		targetService.updateEmployee(employee, employee.getUuid());
		assertEquals(1l, employee.getUuid().longValue());
		
	}
 


}
