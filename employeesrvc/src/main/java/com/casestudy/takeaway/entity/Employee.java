package com.casestudy.takeaway.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="EMPLOYEE_TAB")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(notes = "The database generated UUID ID")
	@Column(name="EMP_ID")
	private Long uuid;
	
	@ApiModelProperty(notes = "Email Id of Employee. It is  unique for each Employee ")
	@Column(name="EMAIL_ID",unique=true)
	private String email;
	
	@ApiModelProperty(notes = "First Name of an Employee")
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@ApiModelProperty(notes = "Last Name of an Employee")
	@Column(name="LAST_NAME")
	private String lastName;
	
	@ApiModelProperty(notes = "Date of Birth of an Employee")
	@Column(name="DATE_OF_BIRTH")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateOfBirth;
	
	
	@ApiModelProperty(notes = "List of associated hobbies of an Employee")
	@ManyToMany
	  @JoinTable
	  (
	      name="EMP_HOBBIES",
	      joinColumns={ @JoinColumn(name="EMP_ID", referencedColumnName="EMP_ID") },
	      inverseJoinColumns={ @JoinColumn(name="HOBBY_ID", referencedColumnName="ID") }
	  )
	private List<Hobby> hobbies;
	
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public List<Hobby> getHobbies() {
		return hobbies;
	}
	public void setHobbies(List<Hobby> hobbies) {
		this.hobbies = hobbies;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
//	public List<Hobby> getHobbies() {
//		return hobbies;
//	}
//	public void setHobbies(List<Hobby> hobbies) {
//		this.hobbies = hobbies;
//	}

}
