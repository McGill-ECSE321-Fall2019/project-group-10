package ca.mcgill.ecse321.project.controller;

import java.sql.Date;
import java.sql.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.project.ErrorStrings;
import ca.mcgill.ecse321.project.dto.*;
import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.service.*;

@CrossOrigin(origins = "*")
@RestController
public class TutoringServiceRestController {

	@Autowired
	TutoringAppService service;

// ******************************************** GET MAPPINGS ********************************************** \\

	
	// Get all the users
			@GetMapping(value = { "/users", "/users/" })
			public List<UserDTO> getAllUsers() {
				List<UserDTO> userDtos = new ArrayList<>();
				// get all the users 
				for (TSUser user : service.getAllUsers()){
					// convert model class to a data transfer object
					userDtos.add(convertToDto(user));
				}
				return userDtos;
			}
			
//			Gets all the students
			@GetMapping(value = { "/students", "/students/" })
			public List<StudentDTO> getAllStudents() {
				List<StudentDTO> studentDtos = new ArrayList<>();
				// get all the students 
				for (Student student : service.getAllStudents()){
					// convert model class to a data transfer object
					studentDtos.add(convertToDto(student));
				}
				return studentDtos;
			}
	

	// Get all the schools offered by the application
	@GetMapping(value = {"/universities", "/universities/"})
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
	@GetMapping(value = {"/courses", "/courses/"})
	public List<CourseDto> getAllCourses() {
		List<CourseDto> cDTOs = new ArrayList<>();

		// get universities from the tutoring service
		for (Course c : service.getAllCourses()) {
			// convert model class to a data transfer object
			cDTOs.add(convertToDto(c));
		}
		return cDTOs;
	}


	@GetMapping(value= { "/sessions", "/sessions/"})
	public List<SessionDTO> getAllSessions() {

		List<SessionDTO> sessionDtos = new ArrayList<>();
		for (Session s : service.getAllSessions()) {

			sessionDtos.add(convertToDto(s));
		}

		return sessionDtos;

	}

	@GetMapping(value = {"/session", "/session/" })
	public SessionDTO getSession(@RequestParam(name = "session_id") Integer sessionId){

		return convertToDto(service.getSession(sessionId));


	}
	
	@GetMapping(value = {"/sessionsbystudent", "/sessionbystudent/"})
	public List<SessionDTO> getSessionByStudent(@RequestParam(name = "student_name") String sName) {
		
		
		List<SessionDTO> sessionDtos = new ArrayList<>();
		for (Session s : service.getSessionByStudent(sName)) {
			
			sessionDtos.add(convertToDto(s));
		}
		
		return sessionDtos;
		
	}

	@DeleteMapping(value = {"/session/delete", "/session/delete/"})
	public boolean removeSession(@RequestParam(name = "session_id") Integer sessionId) {

		return service.deleteSession(sessionId);
		//Insert notification

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

	// ******************************************* PUT AND POST MAPPINGS ********************************************* \\
	

	//Checking to see if session is cancelled.
	@GetMapping(value = {"/{sessionId}/{username}", "/{sessionId}/{username}"})
	public boolean getIfCanceledSession(@PathVariable("sessionId") int sessionID, @PathVariable("username") String studentUsername) throws IllegalArgumentException{
		Session s = service.getSession(sessionID);
		if(s == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Session);
	
		//Check to make sure the date has not passed.
		if(service.isSessionActive(s)) {
			//If room is not available - session ends and email user is notified.
			if(service.isRoomAvailable(s.getDate(), s.getTime())) {
				return false;
			}
		}
		return true;
	}
	
// ******************************************** POST MAPPINGS ********************************************** \\
//	Uses request parameter to get the Age, name, email, and phone number of the user.
	@PostMapping(value = { "/user/{name}/{userAge}/{userName}/{userEmail}/{userPhoneNumber}", "/user/{name}/{userAge}/{userName}/{userEmail}/{userPhoneNumber}" })
	public UserDTO registerUser(@PathVariable("name") String name, @PathVariable("userAge") int userAge, @PathVariable("userName") String userName, @PathVariable("userEmail") String userEmail, @PathVariable("userPhoneNumber") String userPhoneNumber) throws IllegalArgumentException {
		TSUser user = service.createUser(userName, userEmail, userAge, userPhoneNumber); //link the user to the student
		Student student = new Student();
		student.setUser(user);
		return convertToDto(user);
	}
	

	//Creates a text
	@PostMapping(value = { "/text", "/text/" })
	public TextDTO createText(@RequestParam("reviewId") int reviewId, 
			@RequestParam("description") String description, 
			@RequestParam("isAllowed") boolean isAllowed,
			@RequestParam("revieweeUsername") String revieweeUsername)
			throws IllegalArgumentException{
			
		Text text = service.createText(description, isAllowed, revieweeUsername, reviewId);
	
		if(text == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Text);
		}
		return convertToDto(text);
	}
	
	//Creates a text
	@PostMapping(value = { "/rating", "/rating/" })
	public RatingDTO createRating(@RequestParam("reviewId") int reviewId, 
			@RequestParam("rating") int ratingValue,
			@RequestParam("revieweeUsername") String revieweeUsername)
			throws IllegalArgumentException{
			
		Rating rating = service.createRating(ratingValue, revieweeUsername, reviewId);
	
		if(rating == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Rating);
		}
		return convertToDto(rating);
	}
	

	@PostMapping(value = {"/session/create", "/session/create/"})
	public SessionDTO bookSession(@RequestParam(name = "tutor_name") String tName, @RequestParam(name = "student_name") String sName, @RequestParam(name = "booking_date") @DateTimeFormat(pattern = "MMddyyyy") LocalDate bookingDate,
			@RequestParam(name = "booking_time") @DateTimeFormat(pattern = "HH:mm") LocalTime bookingTime, @RequestParam(name = "course_offering_id") Integer courseOfferingId, @RequestParam(name = "amount_paid") Double amountPaid) {

		Session s = service.createSession(courseOfferingId, Date.valueOf(bookingDate), Time.valueOf(bookingTime), amountPaid, sName, tName);

		return convertToDto(s);
	}
	
	@PutMapping(value = {"/addstudent", "/addstudent/"})
	public StudentDTO addStudentToSession(@RequestParam(name = "student_name") String studentName, @RequestParam(name = "session_id") Integer sessionId) throws IllegalArgumentException {
		
		Student s = service.addStudentToSession(sessionId, studentName);
		
		return convertToDto(s);
		
	}


	// Check room availability

	@PostMapping(value = {"/checkavailability", "/checkavailability/"})
	public boolean checkRoomAvailability(@RequestParam(name = "date") Date date,
			@RequestParam(name = "time") Time startTime) throws IllegalArgumentException {
		return service.isRoomAvailable(date, startTime);
	}


	@PostMapping(value = {"/login", "/login/"})
	public boolean login(@RequestParam String username, @RequestParam String password) {
		Role role = service.getRoleByUsername(username);
		if (role.isPassword(password) && !role.isLoggedIn()) {
			role.logIn();
			return true;
		}
		return false;
	}

	@PostMapping(value = {"/logout", "/logout/"})
	public void logout(@RequestParam String username) {
		Role role = service.getRoleByUsername(username);
		role.logOut();
	}
	
	
	/**Method that updates an availability.
	 * 
	 * @param a - created availability
	 * @param tutor - tutor the availability is attached to
	 * @return updated availability Dto
	 * @throws IllegalArgumentException
	 */
	
	@PutMapping(value = {"/availability/update", "/availability/update/"})
	public AvailabilityDTO updateAvailability(@RequestParam(name = "id") Integer aId, @RequestParam(name = "date") Date aD, @RequestParam(name = "time") Time aT, @RequestParam(name = "tutorUsername") String tutorUsername) throws IllegalArgumentException{
		//Checks
		if(service.getAvailability(aId) == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Availability);
		}
		Availability availability = service.updateAvailability(aId, aD, aT, tutorUsername);
		return convertToDto(availability);
	}
	
	@PutMapping(value = {"/session/update", "/session/update/"})
	public SessionDTO update(@RequestParam(name = "sessionId") Integer sId, @RequestParam(name = "date") Date sD, @RequestParam(name = "time") Time sT, @RequestParam(name = "amountPaid") double amountPaid, @RequestParam(name = "studentUser") String studentUser, @RequestParam(name = "tutorUser") String tutorUser, @RequestParam(name = "coId") Integer coId) throws IllegalArgumentException{
		//Checks
		if(service.getSession(sId) == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Session);
		}
		Session session = service.updateSession(sId, coId, sD, sT, amountPaid, studentUser, tutorUser);
		return convertToDto(session);
	}

	

// ******************************************* Conversion to  DTO ********************************************* \\

  	// Convert the model user to a DTO object
	private UserDTO convertToDto(TSUser user) {
		if (user == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_User);
		}
		UserDTO uDTO = new UserDTO(user.getAge(),user.getName(),user.getEmail(),user.getPhoneNumber());
		return uDTO;
	}

  	// Convert the model tutor to a DTO object
	private TutorDTO convertToDto(Tutor t) {
		if (t == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Tutor);
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
			// check whether its a rating or a text
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
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_CourseOffering);
		}
		CourseOfferingDTO coDTO = new CourseOfferingDTO(co.getTerm(), co.getYear(), co.getCourseOfferingID());
		return coDTO;
	}

	// convert the model course to DTO object
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

	//Convert the model availability into a DTO of the availability object.
	private AvailabilityDTO convertToDto(Availability a) {
		if(a == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Availability);
		}
		AvailabilityDTO aDTO = new AvailabilityDTO(a.getDate(), a.getTime());
		return aDTO;
	}

	private RoomDTO convertToDto(Room room) {

		if (room == null) {
			throw new IllegalArgumentException("There is no such room");
		}

		RoomDTO r = new RoomDTO();
		r.setRoomNumber(room.getRoomNumber());
		r.setRoomType(r.getRoomType());

		return r;

	}

	private SessionDTO convertToDto(Session s) {

		if (s == null) {

			throw new IllegalArgumentException("There is no such session");

		}

		SessionDTO sDTO = new SessionDTO();
		sDTO.setTime(s.getTime());
		sDTO.setAmountPaid(s.getAmountPaid());
		sDTO.setCourseOfferingDTO(convertToDto(s.getCourseOffering()));
		sDTO.setDate(s.getDate());
		sDTO.setRoomDTO(convertToDto(s.getRoom()));
		sDTO.setTutorDTO(convertToDto(s.getTutor()));

		ArrayList<StudentDTO> students = new ArrayList<>();
		for (Student stu : s.getStudent()) {
			students.add(convertToDto(stu));
		}
		sDTO.setStudentsDTO(students);
		sDTO.setConfirmed(s.isConfirmed());
		return sDTO;

	}

	
	private TextDTO convertToDto(Text t) {
		if (t == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Text);
		}
		TextDTO tDTO = new TextDTO();
		tDTO.setDescription(t.getDescription());
		tDTO.setIsAllowed(t.getIsAllowed());
		tDTO.setReviewID(t.getReviewID());
		tDTO.setCourseOffering(convertToDto(t.getCourseOffering()));
		tDTO.setRole(convertToDto(t.getWrittenAbout()));
		return tDTO;
	}
	
	private RatingDTO convertToDto(Rating r) {
		if (r == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Rating);
		}
		RatingDTO rDTO = new RatingDTO();
		rDTO.setRatingValue(r.getRatingValue());
		rDTO.setReviewID(r.getReviewID());
		rDTO.setCourseOfferingDTO(convertToDto(r.getCourseOffering()));
		rDTO.setRoleDTO(convertToDto(r.getWrittenAbout()));
		return rDTO;
	}
	

	private StudentDTO convertToDto(Student stu) {

		StudentDTO sDTO = new StudentDTO();
		sDTO.setPassword(stu.getPassword());
		sDTO.setUsername(stu.getUsername());

		return sDTO;


	}
	
	// Check room availability
	@PostMapping(value = { "/checkavailability", "/checkavailability/"})
	public boolean checkRoomAvailability(@RequestParam Date date, @RequestParam Time startTime,	@RequestParam Time endTime)	throws IllegalArgumentException {
		return service.isRoomAvailable(date, startTime);
	}
	
	private ReviewDTO convertToDto(Review r) {
		if(r == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Review);
		}
		ReviewDTO rDTO = new ReviewDTO(r.getCourseOffering(), r.getReviewID(), r.getWrittenAbout());
		return rDTO;
		
	}
	
	private RoleDTO convertToDto(Role r) {
		if(r == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Role);
		}
		RoleDTO rDTO = new RoleDTO(r.getUsername(), r.getPassword());
		return rDTO;
	}
}

