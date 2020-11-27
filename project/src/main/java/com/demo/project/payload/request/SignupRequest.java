package com.demo.project.payload.request;

import java.util.Random;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String empID;
    
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    
    public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return generatePassword(6);
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }
    
    
	private static String generatePassword(int length) {
	      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	      String specialCharacters = "!@#$";
	      String numbers = "1234567890";
	      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	      Random random = new Random();
	      char[] value = new char[length];
	      String password = "";

	      
	      value[0] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
	      value[1] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
	      value[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
	      value[3] = numbers.charAt(random.nextInt(numbers.length()));
	   
	      for(int i = 4; i< length ; i++) {
	         value[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
	      }
	      for(int i = 0; i< length ; i++) {
	    	  password = password+value[i]; 
	      }
	      System.out.println("user password is :=======" + password);
	      return password;
	   }
}
