package com.example.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author nick
 *
 *	This class models a record, it will provide the unit number of the vehicle, any miles 
 *	ran or fuel purchased, along with what state the miles or fuel were in.
 */
@Entity
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * weekending - For the reports the records are organized by week, at the end of each
	 *              week, the driver's paperwork is turned in so the appropriate data can
	 *              be entered into this program.  The most important query will be
	 *              displaying data by quarter.
	 */
	@NotNull(message = "Should have a date.")
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date weekending;
	
	/**
	 * unitNumber - This will be the vehicle that drove and or purchased fuel for this
	 *   			record.
	 */
	private String unitNumber;
	
	/**
	 * state - What state did this vehicle have activity in.
	 */
	private String state;
	
	/**
	 * miles - How many miles did this vehicle drive in the selectected state during this
	 *         week.
	 */
	private double miles;
	
	/**
	 * fuel - Was fuel purchased in the selected state during this week?
	 */
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
