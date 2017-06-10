package com.example.models;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author nick
 *  Interface provides a more specific form of the CrudRepository for dealing with the 
 *  Vehicle objects modeled in this application.
 */
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

}
