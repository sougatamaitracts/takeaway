package com.casestudy.takeaway.employeeService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.casestudy.takeaway.ServiceRunner;
import com.casestudy.takeaway.entity.Employee;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * This class contains Integration Test Cases for Employee Service
 * This Requires authorization server and kafka and in memory db server server communication
 * It uses application-test.properties for required resource information
 * @author Admin
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceRunner.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerITest {

	@LocalServerPort
	private int port;

	@Value("${security.oauth2.client.client-id}")
	private String clientId;

	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

	@Value("${security.token}")
	private String tokenUri;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void getEmployee_negative() {
		HttpEntity<Employee> entity = new HttpEntity<Employee>(headers);
		ResponseEntity<Employee> response = restTemplate.exchange(createURLWithPort("/employee/1000000"),
				HttpMethod.GET, entity, Employee.class);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
	}

	
	@Test
	public void createEmployee_success() throws IOException {
		Employee e = new Employee();
		e.setEmail("integrationTest@sample.com");
		e.setFirstName("FName");
		e.setFirstName("FName");
		HttpHeaders tokenGetHeader = new HttpHeaders();
		tokenGetHeader.add("Authorization", "Basic " + base64Encoding());
		RestTemplate rt = new RestTemplate();
		HttpEntity entityToken = new HttpEntity(tokenGetHeader);
		ResponseEntity<String> responseToken = rt.exchange(tokenUri, HttpMethod.POST, entityToken, String.class);
		String token = null;
		if (responseToken.getStatusCode().value() == (HttpStatus.OK.value())) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode actualObj = mapper.readTree(responseToken.getBody());
			token = actualObj.get("access_token").textValue();

		}
		headers.add("Authorization", "Bearer " + token);
		HttpEntity<Employee> entity = new HttpEntity<Employee>(e, headers);
		ResponseEntity<Employee> response = restTemplate.exchange(createURLWithPort("/secured/employee"),
				HttpMethod.POST, entity, Employee.class);
		Employee addedEmployee = response.getBody();
		boolean expression = false;
		if(addedEmployee.getUuid() !=null) {
			expression = true;
		}
		assertTrue(expression);
		
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	private String base64Encoding() {
		return Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
	}

}
