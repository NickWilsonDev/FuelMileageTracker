package com.example;

import com.example.models.Vehicle;

/**
 * @author nick
 * 
 * This class will allow me to use the Builder pattern, which seems to just make new objects easier to create, may save me some
 * typing in my future test cases.
 * 
 * Creating a new object should be like this.
 * Vehicle vehicle = new VehicleBuilder().unitNumber("w70").description("simple description").build();
 */

public class VehicleBuilder {
	private Vehicle vehicle = new Vehicle();
		  
	public VehicleBuilder unitNumber(String unit) {
		vehicle.setUnitNumber(unit);
		return this;
	}
		  
	public VehicleBuilder description(String description) {
		vehicle.setDescription(description);
		return this;
	}
		  	  
	public Vehicle build() {
		return vehicle;
	}
	
}
