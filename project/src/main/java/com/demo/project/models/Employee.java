package com.demo.project.models;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



@Entity
@Table(	name = "employee", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "empID"),
			@UniqueConstraint(columnNames = "email") 
		})
public class Employee {


	@Id	
	@NotBlank
	@Size(max = 20)
	private String empID;

	@NotBlank
	@Size(max = 20)
	private String name;
	
	

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "employee_role", 
				joinColumns = @JoinColumn(name = "employee_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	
	public Employee() {
	}

	public Employee(String empID, String name, String email, String password) {
		this.empID = empID;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}
	public String getUsername() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	

	
	public static String generatePassword() {
	      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	      String specialCharacters = "!@#$";
	      String numbers = "1234567890";
	      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	      Random random = new Random();
	      char[] value = new char[6];
	      String password = "";

	      
	      value[0] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
	      value[1] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
	      value[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
	      value[3] = numbers.charAt(random.nextInt(numbers.length()));
	   
	      for(int i = 4; i< value.length ; i++) {
	         value[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
	      }
	      for(int i = 0; i< value.length ; i++) {
	    	  password = password+value[i]; 
	      }
	      System.out.println("user password is :=======" + password);
	      return password;
	   }

}
