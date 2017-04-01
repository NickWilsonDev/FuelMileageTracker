package com.example.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RecordRepository extends CrudRepository<Record, Long>{

	
	/**
	 * Query returns all records from a particular Unit Number
	 * @param unitNumber
	 * @return
	 */
	@Query("SELECT r FROM Record r WHERE LOWER(r.unitNumber) = LOWER(:unitNumber)")
    public List<Record> findRecordsByUnitNumber(@Param("unitNumber") String unitNumber);
	
	
	@Query("SELECT p FROM Record p WHERE p.weekending >= :startDate AND p.weekending <= :endDate")
	public List<Record> findRecordsBetweenTwoDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
