package com.example.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * 
 * @author nick
 * This interface will help provide a more specific version of the CrudRepository in order
 * to deal with Record objects that this application works with.
 */
public interface RecordRepository extends CrudRepository<Record, Long>{

	/**
	 * Query returns all records from a particular Unit Number
	 * @param unitNumber - vehicle that we want all records that are associated with
	 * @return           - list of all records that are associated with the provided vehicle
	 */
	@Query("SELECT r FROM Record r WHERE LOWER(r.unitNumber) = LOWER(:unitNumber)")
    public List<Record> findRecordsByUnitNumber(@Param("unitNumber") String unitNumber);
	
	/**
	 * 
	 * @param startDate - begining date
	 * @param endDate   - ending date
	 * @return          - list of all records that have dates within the provided parameters
	 */
	@Query("SELECT p FROM Record p WHERE p.weekending >= :startDate AND p.weekending <= :endDate")
	public List<Record> findRecordsBetweenTwoDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
