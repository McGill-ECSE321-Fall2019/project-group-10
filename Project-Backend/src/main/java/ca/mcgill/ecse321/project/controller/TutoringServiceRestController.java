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

import ca.mcgill.ecse321.project.EmailCreator;
import ca.mcgill.ecse321.project.ErrorStrings;
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

	
	//Post mapping to get both the text and rating for the review. 1) Text 2) Rating
	@PostMapping(value = { "/{coID}/{tutorUsername}", "/{coID}/{tutorUsername}/" })
	public List<ReviewDTO[]> getAllReviewsForTutorInCourseOffering(@PathVariable("coID") int coID, @PathVariable("tutorUsername") String tutorUsername) throws IllegalArgumentException {
		List<ReviewDTO[]> reviewDto = new ArrayList<>();
		List<Review[]> reviewPackages = service.getAllReviewsByCoIDForTutor(tutorUsername, coID);
		
		for(Review[] review : reviewPackages) {
			reviewDto.add(convertToDto((Text)review[0], (Rating)review[1]));			
		}
		
		return reviewDto;
	}

	//Getting session details for the user
	@PostMapping(value = {"/{sessionId}/{username}", "/{sessionId}/{username}"})
	public List<SessionDTO> getActiveSessionDetails(@PathVariable("sessionId") int sessionID, @PathVariable("username") String studentUsername) throws IllegalArgumentException{
		List<SessionDTO> sessionDto = new ArrayList<>();
		
		Student student = service.getStudent(studentUsername);
		for(Session s : student.getSession()) {
			if(s.getSessionID() == sessionID) {
				if(s.getIsActive()) {
					Room room = s.getRoom();
					//If room is not available - session ends and email user is notified.
					if(!room.isAvailable()) {
						s.setActivity(false);
						EmailCreator.notifyUserOfRoomUnavailability(studentUsername);
						return null;
					} else {
						sessionDto.add(convertToDto(s));
					}
				}
			}
		}
		return sessionDto;
	}

	
// ********************************************* Course DTO ************************************************ \\

  
  	// Convert the model course to a DTO object
	private CourseOfferingDTO convertToDto(CourseOffering co) {
		if (co == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_CourseOffering);
		}
		CourseOfferingDTO coDTO = new CourseOfferingDTO(co.getTerm(), co.getYear(), 
				co.getCourse().getCourseName(), co.getCourse().getUniversity().getName());
		return coDTO;
	}
  
	private CourseDto convertToDto(Course c) {
		if (c == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Course);
		}
		CourseDto cDTO = new CourseDto(c.getCourseName(), c.getDescription(), c.getUniversity().getName());
		return cDTO;
	}
	
	// Convert the model university to a DTO object
	private UniversityDTO convertToDto(University u) {
		if (u == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_University);
		}
		UniversityDTO uDTO = new UniversityDTO(u.getName(), u.getAddress());
		return uDTO;
	}
	
	//Convert the model text into a DTO of the text object.
	private ReviewDTO[] convertToDto(Text t, Rating r) {
		if(t == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Text);
		}
		if(r == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Rating);
		}
		TextDTO tDTO = new TextDTO(t.getIsAllowed(), t.getDescription());
		RatingDTO rDTO = new RatingDTO(r.getRatingValue());
		
		//Package
		ReviewDTO[] reviewPackage = {tDTO, rDTO};
		return reviewPackage;
	}	
	
	private SessionDTO convertToDto(Session s) {
		if(s == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Session);
		}
		SessionDTO sDTO = new SessionDTO(s.getTime(), s.getAmountPaid(), s.getDate());
		return sDTO;
	}
}

