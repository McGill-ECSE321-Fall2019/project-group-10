package ca.mcgill.ecse321.project.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.project.dto.*;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.service.*;

@CrossOrigin(origins = "*")
@RestController
public class TutoringServiceRestController {

	@Autowired
	TutoringAppService service;
	
	// Get all the schools offered by the application
	@GetMapping(value = { "/universities", "/universities/" })
	public List<UniversityDTO> getAllUniversities() {
		List<UniversityDTO> universityDtos = new ArrayList<>();
		
		// get universities from the tutoring service
		for (University university : service.getAllUniversities()) {
			// convert model class to a data transfer object
			universityDtos.add(convertToDto(university));
		}
		return universityDtos;
	}
	
	// Get all the schools offered by the application
	@GetMapping(value = { "/courses", "/courses/" })
	public List<CourseDto> getAllCourses() {
		List<CourseDto> cDTOs = new ArrayList<>();
		
		// get universities from the tutoring service
		for (Course c : service.getAllCourses()) {
			// convert model class to a data transfer object
			cDTOs.add(convertToDto(c));
		}
		return cDTOs;
	}
	
	
	// ********** Convert Model to DTO Class ********** //
	
	// Convert the model university to a DTO object
	private CourseDto convertToDto(Course c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such Course!");
		}
		CourseDto cDTO = new CourseDto(c.getCourseName(), c.getDescription(), c.getUniversity().getName());
		return cDTO;
	}
	
	// Convert the model university to a DTO object
	private UniversityDTO convertToDto(University u) {
		if (u == null) {
			throw new IllegalArgumentException("There is no such University!");
		}
		UniversityDTO uDTO = new UniversityDTO(u.getName(), u.getAddress());
		return uDTO;
	}
}
