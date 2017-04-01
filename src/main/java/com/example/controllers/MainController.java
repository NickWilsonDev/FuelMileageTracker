package com.example.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.example.models.Record;
import com.example.models.RecordRepository;
import com.example.models.Vehicle;
import com.example.models.VehicleRepository;

@Controller
public class MainController {

	@Autowired
    VehicleRepository vehicleRepository;
	
	@Autowired
	RecordRepository recordRepository;
	
	@RequestMapping("/")
	public String index() {
		return "frontpage";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/main")
	public String main() {
		return "main";
	}
	
	@RequestMapping("/vehicles")
	public String vehicles(Model model) {
		model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("vehicleList", vehicleRepository.findAll());
		return "vehicles";
	}
	
	@RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
	public String addVehicle(@Valid @ModelAttribute("vehicle") Vehicle vehicle, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "vehicles";
		}
		vehicleRepository.save(vehicle);
	    return "redirect:vehicles";
	}
	
	@RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
	public String getVehicle(@PathVariable long id, Model model) {
		Vehicle vehicle = vehicleRepository.findOne(id);
		model.addAttribute("vehicle", vehicle);
		return "vehicleDetails";
	}
	
	@RequestMapping(value = "/updateVehicle/{id}", method = RequestMethod.POST)
	public RedirectView updateVehicle(@PathVariable long id, @ModelAttribute Vehicle veh) {
		Vehicle vehicle = vehicleRepository.findOne(id);
		vehicle.setUnitNumber(veh.getUnitNumber());
		vehicle.setDescription(veh.getDescription());
		vehicleRepository.save(vehicle);
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl("/vehicles");
	    return redirectView;
	}
	
	/**
	 * This Method acts as a controller to delete a resource (vehicle), specified in the path.
	 * It then redirects back to the main "/vehicles" url essentially reloading the page with the updated
	 * data.
	 * @param id
	 * @return  redirectView - a RedirectView object
	 */
	@RequestMapping(value = "/deleteVehicle/{id}")
	public RedirectView deleteVehicle(@PathVariable long id) {
		vehicleRepository.delete(id);
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl("/vehicles");
	    return redirectView;
	}
	
	@RequestMapping("/records")
	public String records(Model model) {
		model.addAttribute("record", new Record());
		model.addAttribute("vehicleList", vehicleRepository.findAll());
		model.addAttribute("recordList", recordRepository.findAll());
		
		return "records";
	}
	
	@RequestMapping(value = "/addRecord", method = RequestMethod.POST)
	public String addRecord(@Valid @ModelAttribute("record") Record record, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("vehicleList", vehicleRepository.findAll());
			return "records";
		}
		recordRepository.save(record);
	    return "redirect:records";
	}
	
	/**
	 * This Method acts as a controller to delete a resource (record), specified in the path.
	 * It then redirects back to the main "/records" url essentially reloading the page with the updated
	 * data.
	 * @param id
	 * @return  redirectView - a RedirectView object
	 */
	@RequestMapping(value = "/deleteRecord/{id}")
	public RedirectView deleteRecord(@PathVariable long id) {
		recordRepository.delete(id);
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl("/records");
	    return redirectView;
	}
	
	@RequestMapping(value = "/getRecord/{id}", method = RequestMethod.GET)
	public String getRecord(@PathVariable long id, Model model) {
		Record record = recordRepository.findOne(id);
		model.addAttribute("record", record);
		model.addAttribute("vehicleList", vehicleRepository.findAll());
		return "recordDetails";
	}
	
	@RequestMapping(value = "/updateRecord/{id}", method = RequestMethod.POST)
	public RedirectView updateRecord(@PathVariable long id, @ModelAttribute Record rec) {
		Record record = recordRepository.findOne(id);
		record.setUnitNumber(rec.getUnitNumber());
		record.setWeekending(rec.getWeekending());
		record.setState(rec.getState());
		record.setMiles(rec.getMiles()); // Math.round(rec.getMiles) if she wants just integers
		record.setFuel(rec.getFuel());
		recordRepository.save(record);
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl("/records");
	    return redirectView;
	}
	
	
	
	@RequestMapping("/reports")
	public String reports(Model model) {
		model.addAttribute("vehicleList", vehicleRepository.findAll());
		return "reports";
	}
	
	
	@RequestMapping("/about")
	public String about() {
		return "about";
	}
	
	@RequestMapping("/contact")
	public String contact() {
		return "contact";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public String logout() {
		return "logout";
	}
	
}
