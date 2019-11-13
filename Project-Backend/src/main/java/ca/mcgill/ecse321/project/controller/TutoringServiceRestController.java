package ca.mcgill.ecse321.project.controller;

import java.sql.Date;
import java.sql.Time;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

	// get all users
	@GetMapping(value = {"/users", "/users/"})
	public List<UserDTO> getAllUsers(){
		// get all users from the business service
		List<TSUser> listOfUsers = service.getAllUsers();
		List<UserDTO> userListDto = new ArrayList<>();
		for(TSUser user : listOfUsers) {
			userListDto.add(convertToDto(user));
		}
		return userListDto;
	}
	
	// get student by username
	@GetMapping(value = {"/students/{username}", "/students/{username}/"})
	public UserDTO getStudentByUsername(@PathVariable("username") String name) throws IllegalArgumentException{
		TSUser u;
		try {
			Student s = service.getStudent(name);
			u = s.getUser();
		}
		catch(IllegalArgumentException e){throw new IllegalArgumentException(e.getMessage());};
		
		return convertToDto(u);
	}
	
	// get all tutors registered on the application
	@GetMapping(value = {"/tutors", "/tutors/"})
	public List<TutorDTO> getAllTutors(){
		// get all the tutors from the business service
		List<Tutor> listOfTutors = service.getAllTutors();
		List<TutorDTO> tutorList = new ArrayList<>();
		// convert to DTO objects
		for(Tutor tutor : listOfTutors) {
			tutorList.add(convertToDtoSetup(tutor));
		}
		return tutorList;
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
	
	//Get all the appropriate texts.
	@GetMapping(value = {"/textA", "/textA/"})
	public List<TextDTO> getAllAppropriateText() {
		List<TextDTO> textDtos = new ArrayList<>();

		// get universities from the tutoring service
		for (Text text : service.getAllTextsThatAreAllowed()) {
			// convert model class to a data transfer object
			textDtos.add(convertToDto(text));
		}
		return textDtos;
	}

	//Get all the texts.
		@GetMapping(value = {"/texts", "/texts/"})
		public List<TextDTO> getAllText() {
			List<TextDTO> textDtos = new ArrayList<>();

			// get universities from the tutoring service
			for (Text text : service.getAllTexts()) {
				// convert model class to a data transfer object
				textDtos.add(convertToDto(text));
			}
			return textDtos;
		}

	//Get all the appropriate texts.
	@GetMapping(value = {"/ratings", "/ratings/"})
	public List<RatingDTO> getAllRatings() {
		List<RatingDTO> ratingDto = new ArrayList<>();

		// get universities from the tutoring service
		for (Rating rating : service.getAllRatings()) {
			// convert model class to a data transfer object
			ratingDto.add(convertToDto(rating));
		}
		return ratingDto;
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

//	gets all the sessions
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
	@GetMapping(value = { "/tutor/{tutorname}", "/tutor/{tutorname}/" })
	public TutorDTO getTutorByUsername(@PathVariable("tutorname") String username) throws IllegalArgumentException {
		// @formatter:on

		Tutor tutor = service.findTutorByUsername(username);
		TutorDTO tDTO = convertToDto(tutor);

		return tDTO;
	}

	// Check room availability
	@GetMapping(value = {"/checkavailability", "/checkavailability/"})
	public boolean checkRoomAvailability(@RequestParam(name = "date") Date date,
			@RequestParam(name = "time") Time startTime) throws IllegalArgumentException {
		return service.isRoomAvailable(date, startTime);
}

	@GetMapping(value = {"/allavailabilities/{tutorname}", "/allavailabilities/{tutorname}/"})
	public List<AvailabilityDTO> getAllAvailabilitiesByTutor(@PathVariable("tutorname") String username) throws IllegalArgumentException {
		
		if (username == null) {
			throw new IllegalArgumentException("Tutor name invalid");
		}
		
		List<AvailabilityDTO> aDtos = new ArrayList<>();
		
		Tutor t = service.findTutorByUsername(username);
		
		Iterator<Availability> aIt = t.getAvailability().iterator();
		
		while(aIt.hasNext()) {
			Availability a = aIt.next();
			
			// convert model class to a data transfer object
			aDtos.add(convertToDto(a));
		}
		return aDtos;
	}
	
	@GetMapping(value = {"/allavailabilities/", "/allavailabilities/"})
	public List<AvailabilityDTO> getAllAvailabilities() throws IllegalArgumentException {

		List<AvailabilityDTO> aDtos = new ArrayList<>();
		
		List<Availability> aIt = service.getAllAvailabilities();
		
		for(Availability a : aIt) {
			
			// convert model class to a data transfer object
			aDtos.add(convertToDto(a));
			
		}
		return aDtos;
	}

	//Get mapping to get both the text and rating for the review. 1) Text 2) Rating
	@GetMapping(value = { "/tutor/{tutorUsername}/reviews", "/tutor/{tutorUsername}/reviews/" })
	public List<ReviewDTO> getAllReviewsForTutor(@PathVariable("tutorUsername") String tutorUsername) throws IllegalArgumentException {
		List<Review> reviewList = Arrays.asList(service.getAllReviewsByTutor(tutorUsername).toArray(new Review[0]));
		List<ReviewDTO> reviewListDto = new ArrayList<>();
		for(Review r : reviewList) {			
			reviewListDto.add(convertToDto(r));		
		}
		return reviewListDto;
	}
	
	@GetMapping(value = {"/courseoffering/{courseOId}/reviews", "/courseoffering/{courseOId}/reviews/"})
	public List<ReviewDTO> getAllReviewsForCO(@PathVariable("courseOId") int courseOId) throws IllegalArgumentException{
		List<Review> reviewList = Arrays.asList(service.getAllReviewsByCO(courseOId).toArray(new Review[0]));
		List<ReviewDTO> reviewListDto = new ArrayList<>();
		for(Review r : reviewList) {
			reviewListDto.add(convertToDto(r));
		}
		return reviewListDto;
	}
	

// ***************************************** POST CREATE ******************************************* \\

//	Create room.
	@PostMapping(value = {"/createroom", "/createroom/"})
	public RoomDTO createRoom(@RequestParam("roomNumber") int roomNumber) throws IllegalArgumentException{
		
		Room r = service.createRoom(roomNumber);
		return convertToDto(r);
	}
	
//	Create availability for tutor.
	@PostMapping(value = {"/createavailability", "/createavailability/"})
	public AvailabilityDTO bookSession(@RequestParam(name = "tutor_name") String tName,  
			@RequestParam(name = "booking_date") Date bookingDate, 
			@RequestParam(name = "booking_time") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime bookingTime) throws IllegalArgumentException {
		Availability s = service.createAvailability(bookingDate, Time.valueOf(bookingTime), tName);
		
		return convertToDto(s);
	}
	
	
//	Uses request parameter to get the name,age,email,phone number of the user.
	@PostMapping(value = { "/createuser", "/createuser/" })
	public UserDTO registerUser(@RequestParam("name") String name, 
			@RequestParam("age") int userAge, 
			@RequestParam("userEmail") String userEmail, 
			@RequestParam("phoneNum") String userPhoneNumber) throws IllegalArgumentException {
	
		if(service.findUserByEmail(userEmail)!=null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Service_User);
		TSUser u = service.createUser(name, userEmail, userAge, userPhoneNumber);
		return convertToDto(u);
	}
	
	@PostMapping(value = { "/createuser2", "/createuser2/" })
	public StudentDTO registerUserandStudent(@RequestParam("name") String name, 
			@RequestParam("age") int userAge, 
			@RequestParam("userEmail") String userEmail, 
			@RequestParam("phoneNum") String userPhoneNumber, @RequestParam("userName") String username,
			@RequestParam("userPassword") String userpassword) throws IllegalArgumentException {
	
		if(service.findUserByEmail(userEmail)!=null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Service_User);
		TSUser u = service.createUser(name, userEmail, userAge, userPhoneNumber);
		Student s = service.createStudent(username, userpassword, userEmail);
		return convertToDto(s);
	}


//	Uses request parameter to get the user-name,password,email of the student.
	@PostMapping(value= {"/createstudent", "/createstudent/"})
	public StudentDTO registerStudent(
			@RequestParam("userName") String username,
			@RequestParam("userPassword") String userpassword,
			@RequestParam("userEmail") String useremail)
			throws IllegalArgumentException {

		if(service.findStudentByUsername(username)!=null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Service_Student);

		Student s = service.createStudent(username, userpassword, useremail);
		return convertToDto(s);
	}

	//Create tutor
	@PostMapping(value = {"/createtutor", "/createtutor/"})
	public TutorDTO setupCreateRole(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("useremail") String email,
			@RequestParam("amountPaid") double amountPaid,
			@RequestParam("hourlyRate") int hourlyRate,
			@RequestParam("experience") int experience) throws IllegalArgumentException{
			
		TSUser user = service.getUser(email);
		if(user == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_User);
		}
		Tutor tutor = service.createTutor(username, password, user.getEmail(), hourlyRate, experience, Education.bachelor);
		tutor.setUser(user);
		return convertToDtoSetup(tutor);
	}
	
	//Create the given university.
	@PostMapping(value = {"/createuniversity", "/createuniversity/"})
	public UniversityDTO setupCreateUniversity(@RequestParam("name") String name,
			@RequestParam("address") String address) throws IllegalArgumentException {
		
		University university = service.createUniversity(name, address);
		return convertToDto(university);
	}
	
	//Create the given course.
	@PostMapping(value = {"/createcourse", "/createcourse/"})
	public CourseDto setupCreateCourse(@RequestParam("courseDescription") String description,
			@RequestParam("courseName") String courseName,
			@RequestParam("uniName") String uniName) throws IllegalArgumentException {			
		Course course = service.createCourse(description, courseName, service.getUniversityByName(uniName).getUniversityID());
		return convertToDto(course);
	}
	
	//Create the given course offering.
	@PostMapping(value = {"/createcourseoffering", "/createcourseoffering"})
	public CourseOfferingDTO setupCreateCourseOffering(@RequestParam("year") int year,
			@RequestParam("courseName") String courseName) throws IllegalArgumentException {			
		CourseOffering courseO = service.createCourseOffering(Term.Winter, year, service.getCourseByName(courseName).getCourseID());
		return convertToDto(courseO);
	}
		
	//Create the given reviews - text and rating
	@PostMapping(value = {"/createrating", "/createrating/"})
	public RatingDTO setupCreateReviewRating(@RequestParam("rating") int rating,
			@RequestParam("username") String username,
			@RequestParam("courseId") int courseId) throws IllegalArgumentException {
		
		Tutor tutor = service.getTutor(username);
		if(tutor == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Tutor);
		}
		Rating reviewRating = service.createRating(rating, tutor.getUsername(), courseId);
		
		return convertToDto(reviewRating);
	}
	
	//Create the given reviews - text and rating
	@PostMapping(value = {"/createtext", "/createtext/"})
	public TextDTO setupCreateReviewText(@RequestParam("description") String description,
			@RequestParam("isAllowed") boolean isAllowed,
			@RequestParam("username") String username,
			@RequestParam("courseOfferingId") int courseOfferingId) throws IllegalArgumentException {
		
		Text reviewText = service.createText(description, isAllowed, username, courseOfferingId);
		
		return convertToDto(reviewText);
	}

  	//Creates a review
	@PostMapping(value = {"/session", "/session/"})
	public SessionDTO bookSession(@RequestParam(name = "tutor_name") String tName, 
			@RequestParam(name = "student_name") String sName, 
			@RequestParam(name = "booking_date") Date bookingDate, 
			@RequestParam(name = "booking_time") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime bookingTime, 
			@RequestParam(name = "course_offering_id") Integer courseOfferingId, 
			@RequestParam(name = "amount_paid") Double amountPaid) {

		Session s = service.createSession(courseOfferingId, bookingDate, Time.valueOf(bookingTime), amountPaid, sName, tName);

		return convertToDto(s);
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

	//Upon tutor response, update session information or delete session.
	@PostMapping(value = {"/sessionresponse/{response}", "/sessionresponse/{response}/}"})
	public void sessionCreationUponTutorResponse(@PathVariable("response") boolean response,
			@RequestParam("sessionId")int sessionId) throws IllegalArgumentException {
		
		Session session = service.getSession(sessionId);
		if(session == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Session);
		}
		
		//Check if tutor response was successful or not.
		if(response) {
			//true
			//check for room availability.
			if(service.isRoomAvailable(session.getDate(), session.getTime())) {
				//Set the first room available to session.
				session.setRoom(service.getFirstAvailableRoom(session.getDate(), session.getTime()));
				//email user of the creation.
			}
		} else {
			//false
			service.deleteSession(sessionId);
			//Email the user of rejection.
		}
	}


	//Creates a text
	@PostMapping(value = { "/text", "/text/" })
	public TextDTO createTextForTutor(@RequestParam("reviewId") int reviewId,
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

	@PutMapping(value = {"/addstudent", "/addstudent/"})
	public StudentDTO addStudentToSession(@RequestParam(name = "student_name") String studentName, @RequestParam(name = "session_id") Integer sessionId) throws IllegalArgumentException {
		
		Student s = service.addStudentToSession(sessionId, studentName);
		
		return convertToDto(s);
	}



	@PutMapping(value = {"/availability/update", "/availability/update/"})
	public AvailabilityDTO updateAvailability(@RequestParam(name = "id") Integer aId, @RequestParam(name = "date") Date aD, @RequestParam(name = "startTime") Time aT, @RequestParam(name = "tutorUsername") String tutorUsername) throws IllegalArgumentException{
		//Checks
		if(service.getAvailability(aId) == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Availability);
		}
		Availability availability = service.updateAvailability(aId, aD, aT, tutorUsername);
		return convertToDto(availability);
	}


	// if session doesn't have a room assigned, this method will randomly assign a
	// room. Unless there are no rooms available.
	@PutMapping(value = { "/session/room/{sessionid}", "/session/room/{sessionid}/" })
	public SessionDTO updateRoom(@PathVariable("sessionid") int sId, @RequestParam(name = "date") Date aD,
			@RequestParam(name = "startTime") Time aT) throws IllegalArgumentException {

		List<RoomDTO> roomDtos = new ArrayList<>();
		Session s = service.getSession(sId);
		Room r = service.getFirstAvailableRoom(aD, aT);
		if(r==null)
			throw new IllegalArgumentException("There are no rooms available!");
		r.addSession(s);
		roomDtos.add(convertToDto(r));
		return convertToDto(s);
	}
	

	@PutMapping(value = {"/session", "/session/"})
	public SessionDTO updateSession(@RequestParam(name = "sessionId") int sId, 
			@RequestParam(name = "date") Date sD, 
			@RequestParam(name = "time") Time sT, 
			@RequestParam(name = "amountPaid") double amountPaid,
			@RequestParam(name = "studentUser") String studentUser, 
			@RequestParam(name = "tutorUser") String tutorUser, 
			@RequestParam(name = "coId") Integer coId) throws IllegalArgumentException{

		//Checks
		if(service.getSession(sId) == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Session);
		}
		Session session = service.updateSession(sId, coId, sD, sT, amountPaid, studentUser, tutorUser);
		return convertToDto(session);
	}
	

	//Adds a user into the database.
	@PutMapping(value = {"/user/{usermail}/", "/user/{usermail}"})
	public UserDTO updateUser(@PathVariable("usermail") String userEmail, 
			@RequestParam(name = "name") String name,
			@RequestParam(name = "age") int age,
			@RequestParam(name = "phonenumber") String phonenumber) throws IllegalArgumentException {
		TSUser user = service.getUser(userEmail);
		if(user == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_User);
		}
		
		TSUser newUser = service.updateUser(name, user.getEmail(), user.getEmail(), age, phonenumber);

		return convertToDto(newUser);
	}
	
	@PutMapping(value = {"/student/{username}", "/student/{username}/"})
	public StudentDTO updateStudent(@PathVariable("username") String username,
			@RequestParam(name = "newUsername") String nusername,
			@RequestParam(name = "newPassword") String npassword) throws IllegalArgumentException {
		
		
		Student student = service.getStudent(username);
		
		if(student == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Student);
		}

		Student studentUpdate = service.createStudent(nusername, npassword, student.getUser().getEmail());

		return convertToDto(studentUpdate);
	}

	//Update text or rating or both for the review.
	@PutMapping(value = {"/review/text/{reviewId}/", "/review/text/{reviewId}"})
	public ReviewDTO updateText(@PathVariable("reviewId") int reviewId,
			@RequestParam(name = "description") String description) throws IllegalArgumentException{

		Text text = service.getText(reviewId);
		if(text == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Rating);
		text.setDescription(description);
		//set to false and wait for manager response.
		text.setIsAllowed(false);
		return convertToDto(text);
					
	}
	
	//Update text or rating or both for the review.
	@PutMapping(value = {"/review/rating/{reviewId}/", "/review/rating/{reviewId}"})
	public ReviewDTO updateRating(@PathVariable("reviewId") int reviewId,
			@RequestParam(name = "rating") int ratingValue) throws IllegalArgumentException{
		
		Rating ratingT = service.getRating(reviewId);
		if(ratingT == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Rating);
		ratingT.setRatingValue(ratingValue);
		
		return convertToDto(ratingT);
						
		}
	
	
//========================================= DTO =======================================\\
  
	//Setup
	private TutorDTO convertToDtoSetup(Tutor t) {
		
		if(t == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Tutor);
		}
		TutorDTO tDTO = new TutorDTO(t.getUsername(), t.getEducation(), t.getHourlyRate(), t.getExperience());
		tDTO.setUser(convertToDto(t.getUser()));
		return tDTO;
	}	
	
	// Convert the model user to a DTO object
	private UserDTO convertToDto(TSUser u) {
		if (u == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_User);
		}
		UserDTO uDTO = new UserDTO(u.getAge(),u.getName(), u.getEmail(), u.getPhoneNumber());

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
		for(Availability a: service.getAvailabilityByTutorName(t.getUsername())) {
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

	//convert room model to the room DTO
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
		sDTO.setSessionId(s.getSessionID());
		
		if(s.getRoom() != null) {
			sDTO.setRoomDTO(convertToDto(s.getRoom()));
		}
		
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
		//check if rating actually exists.
		if (r == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Rating);
		}
		//Copy all rating attributes into the new dto.
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
