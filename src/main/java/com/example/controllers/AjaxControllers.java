package com.example.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.models.Record;
import com.example.models.RecordRepository;
import com.google.gson.Gson;


@Controller
public class AjaxControllers {

	@Autowired
	RecordRepository recordRepository;
	
	
	
	@RequestMapping(value = "/ajaxAllRecordsByUnitNumber", method = RequestMethod.GET)
	public @ResponseBody String getRecordsByUnitNumber(@RequestParam("unitNumber") String unitNumber) {
		List<Record> recs = recordRepository.findRecordsByUnitNumber(unitNumber);
		System.out.println(recs.get(0).getUnitNumber()); 
		// create a new Gson instance
		Gson gson = new Gson();
		// convert your list to json
		String jsonRecsList = gson.toJson(recs);
		// print your generated json
		System.out.println("jsonCartList: " + jsonRecsList);
		 
		return jsonRecsList;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/ajaxAllRecordsBetweenTwoDates", method = RequestMethod.GET)
	public @ResponseBody String getRecordsBetweenTwoDates(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date start = null;
		Date end   = null;
		try {
			start = (Date)formatter.parse(startDate);
			end   = (Date)formatter.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Date date = new Date();
			start = new Date(date.toString());
			end   = new Date(date.toString());
		}
		
		List<Record> recs = recordRepository.findRecordsBetweenTwoDates(start, end);
		// create a new Gson instance
		Gson gson = new Gson();
		// convert your list to json
		String jsonRecsList = gson.toJson(recs);
		// print your generated json
		System.out.println("jsonCartList: " + jsonRecsList);
		 
		return jsonRecsList;
	}
	
	
	/** simple test to see ajax in action
	 *
	 * @return
	 */
	@RequestMapping(value = "/ajaxtest", method = RequestMethod.GET)
   public @ResponseBody
   String getTime() {

       Random rand = new Random();
       float r = rand.nextFloat() * 100;
       String result = "Next Random # is <b>" + r + "</b>. Generated on <b>" + new Date().toString() + "</b>";
       System.out.println("Debug Message from CrunchifySpringAjaxJQuery Controller.." + new Date().toString());
       return result;
   }
	
}
