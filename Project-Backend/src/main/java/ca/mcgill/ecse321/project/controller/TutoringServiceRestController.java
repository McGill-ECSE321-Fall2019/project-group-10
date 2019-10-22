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

import org.springframework.web.bind.annotation.RequestBody;
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
	

// ******************************************** GET MAPPINGS ********************************************** \\
	
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
	
	@PostMapping(value = { "/{universityname}", "/{universityname}/" })
	public List<CourseDto> getCoursesforUni(@PathVariable("universityname") String name) throws IllegalArgumentException {
		// @formatter:on
		List<CourseDto> cDTOs = new ArrayList<>();

		// get courses by university from the tutoring service
		List<Course> courses = service.getAllCoursesByUniversity(name);
				
		for (Course c : courses) {
			// convert model class to a data transfer object
			cDTOs.add(convertToDto(c));
		}
		return cDTOs;
	}
	
	@PostMapping(value = { "/{universityname}/{coursename}", "/{universityname}/{coursename}/" })
	public List<CourseOfferingDTO> getCOforCourseforUni(@PathVariable("universityname") String name, 
			@PathVariable("coursename") String cname) throws IllegalArgumentException {
		// @formatter:on
		List<CourseOfferingDTO> coDTOs = new ArrayList<>();

		// get course offerings by course by university from the tutoring service
		List<CourseOffering> courseOs = service.getAllCourseOfferingsByCourse(cname, name);
				
		for (CourseOffering c : courseOs) {
			// convert model class to a data transfer object
			coDTOs.add(convertToDto(c));
		}
		return coDTOs;
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
	
//	// Get all the tutors signed up for a course offering
//	@PostMapping(value = { "/{universityname}/{coursename}/{courseOffering}", "/{universityname}/{coursename}/{courseOffering}/" })
//	public List<TutorDTO> getTutorsByCO() {
//		List<TutorDTO> tutorDTOs = new ArrayList<>();
//		
//		// get universities from the tutoring service
//		for (Tutor t : service.getAllUniversities()) {
//			// convert model class to a data transfer object
//			tutorDTOs.add(convertToDto(t));
//		}
//		return tutorDTOs;
//	}

	
// ******************************************** POST MAPPINGS ********************************************** \\
	
	
	//Uses request parameter to get the username and courseoffering id. RequestBody sends the descprion.
	@PostMapping(value = { "/text", "/text/" })
	public TextDTO createReview(@PathVariable("text") String name, @RequestParam String tutorUsername, @RequestParam int coID, @RequestBody String description, @RequestBody boolean isAllowed) throws IllegalArgumentException {
		Text text = service.createText(description, isAllowed, tutorUsername, coID);
		return convertToDto(text);
	}

	
// ********************************************* Course DTO ************************************************ \\

  
  	// Convert the model course to a DTO object
	private CourseOfferingDTO convertToDto(CourseOffering co) {
		if (co == null) {
			throw new IllegalArgumentException("There is no such CourseOffering!");
		}
		CourseOfferingDTO coDTO = new CourseOfferingDTO(co.getTerm(), co.getYear(), 
				co.getCourse().getCourseName(), co.getCourse().getUniversity().getName());
		return coDTO;
	}
  
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
	
	//Convert the model text into a DTO of the text object.
	private TextDTO convertToDto(Text t) {
		if(t == null){
			throw new IllegalArgumentException("There is no such Text!");
		}
		TextDTO tDTO = new TextDTO(t.getIsAllowed(), t.getDescription());
		return tDTO;
	}	
}

