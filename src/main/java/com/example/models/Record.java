package com.example.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull(message = "Should have a date.")
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date weekending;
	
	private String unitNumber;
	
	private String state;
	
	private double miles;
	
	private double fuel;
	
	public long getId() {
		return id;
	}
	
	public Date getWeekending() {
		return weekending;
	}
	
	public void setWeekending(Date date) {
		weekending = date;
	}
	
	public String getUnitNumber() {
		return unitNumber;
	}
	
	public void setUnitNumber(String unit) {
		unitNumber = unit;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public double getMiles() {
		return miles;
	}
	
	public void setMiles(double miles) {
		this.miles = miles;
	}
	
	public double getFuel() {
		return fuel;
	}
	
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}
}
