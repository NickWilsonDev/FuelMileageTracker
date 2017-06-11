package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.models.Record;

/**
 * @author nick
 * 
 * This class will allow me to use the Builder pattern, which seems to just make new objects easier to create, may save me some
 * typing in my future test cases.
 * 
 * Creating a new object should be like this.
 * Record record = new RecordBuilder().unitNumber("w70").description("simple description").build();
 */

public class RecordBuilder {
	private Record record = new Record();
		  
	public RecordBuilder unitNumber(String unit) {
		record.setUnitNumber(unit);
		return this;
	}
		  
	public RecordBuilder state(String description) {
		record.setState(description);
		return this;
	}
	
	public RecordBuilder miles(double miles) {
		record.setMiles(miles);
		return this;
	}
	
	public RecordBuilder fuel(double fuel) {
		record.setFuel(fuel);
		return this;
	}
	
	/**
	 * Method is part of the builder class for creating Record objects.
	 * 
	 * @param date - String that should be of the form "MM/dd/yyyy"
	 * @return a modified RecordBuilder that will be used to create a Record object eventually
	 */
	public RecordBuilder weekending(String date) {
		Date properDate = null;
		try {
			properDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (properDate == null) {
			System.out.println("incorrect form of String parameter");
		}
		record.setWeekending(properDate);
		return this;
	}
	public Record build() {
		return record;
	}
	
}
