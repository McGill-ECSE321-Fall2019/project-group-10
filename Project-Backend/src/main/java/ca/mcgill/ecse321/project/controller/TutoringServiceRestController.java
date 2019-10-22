package ca.mcgill.ecse321.project.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
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
	
	@Autowired
	TutoringAppService service;

// ******************************************** GET MAPPINGS ********************************************** \\
	
	
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
	
	// Get all the courses for a chosen university
	@GetMapping(value = { "/universities/{universityname}", "/universities/{universityname}/" })
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
	
	// Get all the course offerings for a chosen course
	@GetMapping(value = { "courses/{universityname}/{coursename}", "courses/{universityname}/{coursename}/" })
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
	
	// Get all the tutors signed up for a course offering
	@GetMapping(value = { "/courseoffering/{id}", "/courseoffering/{id}/" })
	public List<TutorDTO> getTutorsByCO(@PathVariable("id") String id) {
		List<TutorDTO> tutorDTOs = new ArrayList<>();
		
		// get universities from the tutoring service
		for (Tutor t : service.getAllTutorsByCourseOffering(Integer.parseInt(id))) {
			// convert model class to a data transfer object
			tutorDTOs.add(convertToDto(t));
		}
		return tutorDTOs;
	}
	
	// Get all the courses for a chosen university
	@GetMapping(value = { "/tutors/{tutorname}", "/tutors/{tutorname}/" })
	public TutorDTO getTutorByUsername(@PathVariable("tutorname") String username) throws IllegalArgumentException {
		// @formatter:on
		
		Tutor tutor = service.findTutorByUsername(username);
		TutorDTO tDTO = convertToDto(tutor);

		return tDTO;
	}

	
// ******************************************** POST MAPPINGS ********************************************** \\
	
	
	//Uses request parameter to get the username and courseoffering id. RequestBody sends the description.
	@PostMapping(value = { "/text", "/text/" })
	public TextDTO createReview(@PathVariable("text") String name, @RequestParam String tutorUsername, @RequestParam int coID, @RequestBody String description, @RequestBody boolean isAllowed) throws IllegalArgumentException {
		Text text = service.createText(description, isAllowed, tutorUsername, coID);
		return convertToDto(text);
	}
	
	// Check room availability
	@PostMapping(value = { "/checkavailability", "/checkavailability/" })
	public boolean checkRoomAvailability(@RequestParam Date date,
	@RequestParam Time startTime,
	@RequestParam Time endTime)
	throws IllegalArgumentException {
		return service.isRoomAvailable(date, startTime, endTime);
	}
	
// ******************************************* Conversion to  DTO ********************************************* \\

  	// Convert the model room to a DTO object
	private RoomDTO convertToDto(Room r) {
		if (r == null) {
			throw new IllegalArgumentException("There is no such Room!");
		}
		RoomDTO rDTO = new RoomDTO();
		return rDTO;
	}
	
  	// Convert the model rating to a DTO object
	private RatingDTO convertToDto(Rating r) {
		if (r == null) {
			throw new IllegalArgumentException("There is no such Rating!");
		}
		RatingDTO rDTO = new RatingDTO();
		return rDTO;
	}
	
  	// Convert the model user to a DTO object
	private UserDTO convertToDto(User u) {
		if (u == null) {
			throw new IllegalArgumentException("There is no such User!");
		}
		UserDTO uDTO = new UserDTO();
		return uDTO;
	}
	
  	// Convert the model tutor to a DTO object
	private TutorDTO convertToDto(Tutor t) {
		if (t == null) {
			throw new IllegalArgumentException("There is no such Tutor!");
		}
		TutorDTO tDTO = new TutorDTO(t.getUsername(), t.getEducation(), t.getHourlyRate(), t.getExperience());
		
		// get the availabilities
		List<AvailabilityDTO> avails = new ArrayList<AvailabilityDTO>();
		for(Availability a: new ArrayList<Availability>(t.getAvailability())) {
			// convert to a DTO
			avails.add(convertToDto(a));
		}
		tDTO.setAvails(avails);
		
		List<RatingDTO> ratings = new ArrayList<RatingDTO>();
		List<TextDTO> texts = new ArrayList<TextDTO>();
		// get all reviews
		for(Review r: new ArrayList<Review>(t.getReview())) {
			// check wether its a rating or a text
			if(r instanceof Rating)
				ratings.add(convertToDto((Rating)r));
			if(r instanceof Text)
				texts.add(convertToDto((Text)r));
		}
		
		tDTO.setRatings(ratings);
		tDTO.setTexts(texts);
	
		return tDTO;
	}
  
  	// Convert the model course offering to a DTO object
	private CourseOfferingDTO convertToDto(CourseOffering co) {
		if (co == null) {
			throw new IllegalArgumentException("There is no such CourseOffering!");
		}
		CourseOfferingDTO coDTO = new CourseOfferingDTO(co.getTerm(), co.getYear(), co.getCourseOfferingID());
		return coDTO;
	}
  
	// convert the model course to DTO object
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
	
	//Convert the model availability into a DTO of the availability object.
	private AvailabilityDTO convertToDto(Availability a) {
		if(a == null){
			throw new IllegalArgumentException("There is no such Availability!");
		}
		AvailabilityDTO aDTO = new AvailabilityDTO(a.getDate(), a.getTime());
		return aDTO;
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

