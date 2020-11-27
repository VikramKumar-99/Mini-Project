package com.demo.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ques_user")
public class Mapping {
	@Id
	@Column(name = "map_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int mapid;
	
	@Column(name = "que_Id")
	private int queId;
	
	@Column(name = "emp_Id")
	private String empId;
	
	@Column(name = "accepted")
	private int accept;

	public Mapping(int queId, String empId, int accept) {
		
		this.queId = queId;
		this.empId = empId;
		this.accept = accept;
	}
	
	public Mapping() {
		
	}

	public int getMapid() {
		return mapid;
	}

	public void setMapid(int mapid) {
		this.mapid = mapid;
	}

	public int getQueId() {
		return queId;
	}

	public void setQueId(int queId) {
		this.queId = queId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public int getAccept() {
		return accept;
	}

	public void setAccept(int accept) {
		this.accept = accept;
	}
	
	
	

}
