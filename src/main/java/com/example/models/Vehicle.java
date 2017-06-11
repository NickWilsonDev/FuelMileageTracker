package com.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;

/**
 * This class models a vehicle.
 */

@Entity
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * unitNumber - Name or unit number for vehicle, should not be empty.
	 * 				ex 'w-190'
	 */
	@NotBlank(message = "Unit number cannot be empty.")
	private String unitNumber;
	
	/**
	 * description - Any additional information about vehicle that is needed. Year model
	 * 				 ect.
	 */
	private String description;

	/** Getters and Setters **/
	public long getId() {
		return id;
	}
	
	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
