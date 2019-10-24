package ca.mcgill.ecse321.project.service;


import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.project.ErrorStrings;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.model.*;

@Service
public class TutoringAppService {
	
	@Autowired
	AvailabilityRepository availabilityRepository;
	@Autowired
	CourseOfferingRepository courseOfferingRepository;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	RatingRepository ratingRepository;
	@Autowired
	TextRepository textRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	TutorRepository tutorRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	SessionRepository sessionRepository;
	@Autowired
	UniversityRepository universityRepository;
	@Autowired
	UserRepository userRepository;
	

	//Checking to make sure we can create an availability instance.
	@Transactional
	public Availability createAvailability(Date date, Time time, String tName) {

		if(tName == null || tName.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Availability_Name);
		}
		if(date == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Availability_Date);
		}
		if(time == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Availability_Time);
		}
		
		
		// set the 
		Availability availability = new Availability();
		availability.setTime(time);
		availability.setDate(date);
		Tutor t = tutorRepository.findTutorByUsername(tName);
		checkAvailabilityUniqueForTutor(t, availability);
		
		availability.setTutor(t);
		
		if (availability.getTutor() == null){
			
			throw new IllegalArgumentException(ErrorStrings.Invalid_Availability_Tutor);
			
		}
		
		availabilityRepository.save(availability);
		return availability;
	}

	//Checking to make sure we can update an availability intances.
	@Transactional
	public Availability updateAvailability(int oldID, Date date, Time time,String tName) {
		if(tName == null || tName.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Availability_Name);
		}
		if(date == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Availability_Date);
		}
		if(time == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Availability_Time);
		}
		

		Availability availability = availabilityRepository.findAvailabilityById(oldID);
		
		if (availability == null) {
			
			throw new IllegalArgumentException(ErrorStrings.Invalid_Availability_Null);
		}
		availability.setTime(time);
		availability.setDate(date);
		Tutor t = tutorRepository.findTutorByUsername(tName);
		if( t == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Availability_Tutor);
//		availability.setTutor(t);
		availabilityRepository.save(availability);
		return availability;
	}
	
	//Checking to make sure we can get the list of all the availability intances.
	@Transactional
	public List<Availability> getAllAvailabilities() {
		return toList(availabilityRepository.findAll());
	}
	
	//Checking to make sure we can get all the availability intances.
	@Transactional
	public Availability getAvailability(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Availability_ID_Get);
		}
				
		Availability a = availabilityRepository.findAvailabilityById(new Integer(id));
		return a;
	}
	
	//Checking to make sure we can get all the availabilities for the given tutor.
//	@Transactional
//	public List<Availability> getAvailabilityByTutor(String username) {
//		Tutor tutor = tutorRepository.findTutorByUsername(username);
////		return toList(availabilityRepository.findAvailabilityByTutor(tutor));
//	}
//	
//	//Checking to make sure we can delete the availabilities.
	@Transactional
	public boolean deleteAvailability(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Availability_ID_Delete);
		}

		boolean done = false;
		Availability a = getAvailability(id);
		if (a != null) {
			availabilityRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	//Checking to make sure we can delete the availabilities given the tutor.
//	@Transactional
//	public boolean deleteAvailabilityByTutor(String username) {
//		if(username == null || username.equals("")){
//			throw new IllegalArgumentException("You are searching for an invalid username...");
//		}
//
//		boolean done = false;
	@Transactional
	public List<Availability>getAvailabilityByTutor(String tName) {
		
		List<Availability> availabilitylList = new ArrayList<>();
		Tutor t = tutorRepository.findTutorByUsername(tName);
		availabilitylList = (Arrays.asList((t.getAvailability().toArray(new Availability[0]))));
		
		return availabilitylList;
	}
	
	//Checking to make sure we can create a course offering.
	@Transactional
	public CourseOffering createCourseOffering(Term term, int year, int courseID) {
		if(year < 1900){
			throw new IllegalArgumentException(ErrorStrings.Invalid_CourseOffering_Year);
		}
		if(courseID < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_CourseOffering_CourseID);
		}
		if(term == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_CourseOffering_Term);
		}
		
		CourseOffering courseOffering = new CourseOffering();
		courseOffering.setYear(year);
		courseOffering.setTerm(term);
		Course c = courseRepository.findCourseByCourseID(courseID);
		if( c == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_CourseOffering_CantFindCourseOffering);
		courseOffering.setCourse(c);
		courseOfferingRepository.save(courseOffering);
		return courseOffering;
	}
	
	//Checking to make sure we can update the course offering.
	@Transactional
	public CourseOffering updateCourseOffering(int oldID, Term term, int year, int courseID) {
		if(year < 1900){
			throw new IllegalArgumentException(ErrorStrings.Invalid_CourseOffering_Year);
		}
		if(courseID < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_CourseOffering_CourseID);
		}
		if(term == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_CourseOffering_Term);
		}
		CourseOffering courseOffering = courseOfferingRepository.findCourseOfferingByCourseOfferingID(oldID);
		if(courseOffering== null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_CourseOffering_CantFindCourseOffering);
		courseOffering.setYear(year);
		courseOffering.setTerm(term);
		Course c = courseRepository.findCourseByCourseID(courseID);
		if( c == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_CourseOffering_CantFindCourseOffering);
		courseOffering.setCourse(c);
		courseOfferingRepository.save(courseOffering);
		return courseOffering;
	}
	
	//Checking to make sure we can get the list of all the course offerings.
	@Transactional
	public List<CourseOffering> getAllCourseOfferings() {
		return toList(courseOfferingRepository.findAll());
	}
	
	//Checking to make sure we can get all the course offerings.
	@Transactional
	public CourseOffering getCourseOffering(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_CourseOffering_ID);
		}
		CourseOffering a = courseOfferingRepository.findCourseOfferingByCourseOfferingID(id);
		return a;
	}
	
	//Checking to make sure we can get delete the course offerings.
	@Transactional
	public boolean deleteCourseOffering(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_CourseOffering_ID);
		}
		boolean done = false;
		CourseOffering a = getCourseOffering(id);
		if (a != null) {
			courseOfferingRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	//Checking to make sure we can get create a course.
	@Transactional
	public Course createCourse(String description, String courseName, int uniID) {
		if(courseName == null || courseName.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Course_CourseName);
		}
		if(description == null || description.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Course_Description);
		}
		University u = universityRepository.findUniversityByUniversityID(uniID);
		if( u == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Course_FindUniversityWithID);
	
		Course course = new Course();
		course.setDescription(description);
		course.setCourseName(courseName);
		course.setUniversity(u);
		courseRepository.save(course);
		return course;
	}
	
	//Checking to make sure we can get update a course.
	@Transactional
	public Course updateCourse(int oldID, String description, String courseName, int uniID) {
		if(courseName == null || courseName.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Course_CourseName);
		}
		if(description == null || description.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Course_Description);
		}
		Course course = courseRepository.findCourseByCourseID(oldID);
		if( course == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Course_FindCourseWithID);
		University u = universityRepository.findUniversityByUniversityID(uniID);
		if( u == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Course_FindUniversityWithID);
		course.setDescription(description);
		course.setCourseName(courseName);
		course.setUniversity(u);
		courseRepository.save(course);
		return course;
	}
	
	//Checking to make sure we can get a course.
	@Transactional
	public Course getCourse(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Course_ID);
		}
		Course a = courseRepository.findCourseByCourseID(new Integer(id));
		return a;
	}

	//Checking to make sure we can delete a course.
	@Transactional
	public boolean deleteCourse(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Course_ID);
		}
		boolean done = false;
		Course a = getCourse(id);
		if (a != null) {
			courseRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	//Checking to make sure we can get all course.
	@Transactional
	public List<Course> getAllCourses() {
		return toList(courseRepository.findAll());
	}
	
	//Checking to make sure we can create a text.
	@Transactional
	public Text createText(String description, boolean isAllowed, String revieweeUsername, int coID) {
		if(revieweeUsername == null || revieweeUsername.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Text_RevieweeUsername);
		}
		if(description == null || description.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Text_Description);
		}
		CourseOffering c = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID));
		
		if(c == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Text_FindCourseOffering);
		
		Text text = new Text();
		if(tutorRepository.findTutorByUsername(revieweeUsername) != null)
			text.setWrittenAbout(tutorRepository.findTutorByUsername(revieweeUsername));
		else if (studentRepository.findStudentByUsername(revieweeUsername) != null)
			text.setWrittenAbout(studentRepository.findStudentByUsername(revieweeUsername));
		else 
			throw new IllegalArgumentException(ErrorStrings.Invalid_Text_Reviewee);
		text.setDescription(description);
		text.setIsAllowed(isAllowed);
		text.setCourseOffering(c);
		textRepository.save(text);
		return (Text)text;
	}

	//Checking to make sure we can update a text.
	@Transactional
	public Text updateText(int oldID, String description, boolean isAllowed, String revieweeUsername, int coID) {

		if(revieweeUsername == null || revieweeUsername.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Text_RevieweeUsername);
		}
		if(description == null || description.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Text_Description);
		}
		CourseOffering c = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID));
		if(c == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Text_FindCourseOffering);
		
		Text text = textRepository.findTextByReviewID(oldID);
		if(text == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Text_FindTextReview);
		text.setDescription(description);
		text.setIsAllowed(isAllowed);
		if(tutorRepository.findTutorByUsername(revieweeUsername) != null)
			text.setWrittenAbout(tutorRepository.findTutorByUsername(revieweeUsername));
		else if (studentRepository.findStudentByUsername(revieweeUsername) != null)
			text.setWrittenAbout(studentRepository.findStudentByUsername(revieweeUsername));
		else 
			throw new IllegalArgumentException(ErrorStrings.Invalid_Text_Reviewee);
		text.setCourseOffering(c);
		textRepository.save(text);
		return (Text)text;
	}
	
	//Checking to make sure we can get a list of text instances.
	@Transactional
	public List<Text> getAllTexts() {
		return toList(textRepository.findAll());
	}
	
	//Checking to make sure we can get text.
	@Transactional
	public Text getText(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Text_ID);
		}

		Text a = textRepository.findTextByReviewID(id);
		return a;
	}
	
	//Checking to make sure we can delete a text.
	@Transactional
	public boolean deleteText(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Text_ID);
		}

		boolean done = false;
		Text a = getText(id);
		if (a != null) {
			textRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	//Checking to make sure we can create a rating.
	@Transactional
	public Rating createRating(int ratingValue, String revieweeUsername, int coID) {

		if(revieweeUsername == null || revieweeUsername.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Rating_RevieweeUsername);
		}
		
		Rating rating = new Rating();
		
		if(tutorRepository.findTutorByUsername(revieweeUsername) != null)
			rating.setWrittenAbout(tutorRepository.findTutorByUsername(revieweeUsername));
		else if (studentRepository.findStudentByUsername(revieweeUsername) != null)
			rating.setWrittenAbout(studentRepository.findStudentByUsername(revieweeUsername));
		else 
			throw new IllegalArgumentException(ErrorStrings.Invalid_Rating_Reviewee);

		CourseOffering c = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID)); 
		
		if(c == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Rating_FindCourseOffering);
		
		try {
			rating.setRatingValue(ratingValue);
		} catch(RuntimeException e) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_Rating_NegativeRatingValue);
		}
		
		rating.setCourseOffering(c);
		ratingRepository.save(rating);
		return rating;
	}
	
	//Checking to make sure we can create a text.
	@Transactional
	public Rating updateRating(int oldID, int ratingValue, String revieweeUsername, int coID) {
		if(revieweeUsername == null || revieweeUsername.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Rating_RevieweeUsername);
		}
		
		Rating rating = ratingRepository.findRatingByReviewID(oldID);
		if(rating == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Rating_FindRatingByReview);
		CourseOffering c;
		try {
			c = getCourseOffering(coID);
		} catch(RuntimeException e) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_Rating_CourseOfferingID);
		}
		
		try {
		rating.setRatingValue(ratingValue);
		} catch(RuntimeException e) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_Rating_IncorrectRatingValue);
		}
		if(tutorRepository.findTutorByUsername(revieweeUsername) != null)
			rating.setWrittenAbout(tutorRepository.findTutorByUsername(revieweeUsername));
		else if (studentRepository.findStudentByUsername(revieweeUsername) != null)
			rating.setWrittenAbout(studentRepository.findStudentByUsername(revieweeUsername));
		else 
			throw new IllegalArgumentException(ErrorStrings.Invalid_Rating_RevieweeUsername);
		rating.setCourseOffering(c);
		ratingRepository.save(rating);
		return rating;
	}
	
	//Checking to make sure we can get all ratings.
	@Transactional
	public List<Rating> getAllRatings() {
		return toList(ratingRepository.findAll());
	}
	
	//Checking to make sure we can get a rating.
	@Transactional
	public Rating getRating(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Rating_ID);
		}
		
		Rating a = ratingRepository.findRatingByReviewID(id);
		return a;
	}
	
	//Checking to make sure we can delete a rating.
	@Transactional
	public boolean deleteRating(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Rating_ID);
		}
		
		boolean done = false;
		Rating a = getRating(id);
		if (a != null) {
			ratingRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	//Checking to make sure we can create a tutor.
	@Transactional
	public Tutor createTutor(String username, String password, String userEmail, double hourlyRate, int exp, Education level) {
		if(username == null || username.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_Username);
		}
		if(password == null  || password.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_Password);
		}
		//Regex check for email
		if(!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_EmailCheck);
		}
		if(hourlyRate < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_NegativeHourlyRate);
		}
		if(exp < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_NegativeExperience);
		}
		if(level == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_NullLevel);
		}
		Tutor tutor = new Tutor();
		TSUser u = userRepository.findTSuserByEmail(userEmail);
		if (u == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_FindUserError);
		tutor.setUsername(username);
		tutor.setPassword(password);
		tutor.setUser(u);
		tutor.setHourlyRate(hourlyRate);
		tutor.setExperience(exp);
		tutor.setEducation(level);
		tutorRepository.save(tutor);
		return tutor;
	}
	
	//Checking to make sure we can update a tutor.
	@Transactional
	public Tutor updateTutor(String oldUsername, String username, String password, String userEmail, double hourlyRate, int exp, Education level) {
		if(username == null || username.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_Username);
		}
		if(oldUsername == null || oldUsername.equals("")) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_OldUsername);
		}
		if(password == null  || password.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_Password);
		}
		if(!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_EmailCheck);
		}
		if(hourlyRate < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_NegativeHourlyRate);
		}
		if(exp < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_NegativeExperience);
		}
		if(level == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_NullLevel);
		}
		Tutor tutor = tutorRepository.findTutorByUsername(oldUsername);
		if(tutor == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_FindByTutorError);
		TSUser u = userRepository.findTSuserByEmail(userEmail);
		if (u == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_FindUserError);
		tutor.setUsername(username);
		tutor.setPassword(password);
		tutor.setUser(u);
		tutor.setHourlyRate(hourlyRate);
		tutor.setExperience(exp);
		tutor.setEducation(level);
		tutorRepository.save(tutor);
		return tutor;
	}
	
	//Checking to make sure we can get a tutor.
	@Transactional
	public Tutor getTutor(String username) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_Username);
		}
		Tutor a = tutorRepository.findTutorByUsername(username);
		return a;
	}
	
	//Checking to make sure we can delete a tutor.
	@Transactional
	public boolean deleteTutor(String username) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Tutor_Username);
		}
		boolean done = false;
		Tutor a = getTutor(username);
		if (a != null) {
			tutorRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	//Checking to make sure we can get all students.
	@Transactional
	public List<Student> getAllStudents() {
		return toList(studentRepository.findAll());
	}
	
	//Checking to make sure we can get all tutors.
	@Transactional
	public List<Tutor> getAllTutors() {
		return toList(tutorRepository.findAll());
	}
	
	//Checking to make sure we can create a student.
	@Transactional
	public Student createStudent(String username, String password, String userEmail) {
		if(username == null || username.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_Username);
		}
		if(password == null  || password.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_Password);
		}
		if(userEmail == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_UserEmail);
		}
		if(!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_UserEmail);
		}
		TSUser u = userRepository.findTSuserByEmail(userEmail);
		if (u == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_FindUserByEmail);
		
		Student student = new Student();
		student.setUsername(username);
		student.setPassword(password);
		student.setUser(u);
		studentRepository.save(student);
		return student;
	}

	//Checking to make sure we can update a student.
	@Transactional
	public Student updateStudent(String oldUsername, String username, String password, String userEmail) {
		if(username == null || username.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_Username);
		}
		if(oldUsername == null || oldUsername.equals("")) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_Username);
		}
		if(password == null  || password.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_Password);
		}
		if(!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_UserEmail);
		}
		Student student = studentRepository.findStudentByUsername(oldUsername);
		if(student == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_FindStudentByUsername);
		student.setUsername(username);
		student.setPassword(password);
		TSUser u = userRepository.findTSuserByEmail(userEmail);
		if (u == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_FindUserByEmail);
		student.setUser(u);
		studentRepository.save(student);
		return student;
	}

	//Checking to make sure we can get a student.
	@Transactional
	public Student getStudent(String username) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_Username);
		}
		Student a = studentRepository.findStudentByUsername(username);
		return a;
	}
	
	//Checking to make sure we can delete a student.
	@Transactional
	public boolean deleteStudent(String username) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Student_Username);
		}
		boolean done = false;
		Student a = getStudent(username);
		if (a != null) {
			studentRepository.delete(a);
			done = true;
		}
		return done;
	}

	//Checking to make sure we can create a session.
	@Transactional
	public Session createSession(int coID, Date date, Time time, Double amountPaid, String sName, String tName) {

		if(tName == null || tName.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_TutorName);
		}
		if(sName == null || sName.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_StudentName);
		}
		if(date == null || time == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_DateTime);
		}
		if(amountPaid < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_NegativeAmountPaid);
		}
		
		
		if(!isRoomAvailable(date, time)) {
			
			throw new IllegalArgumentException("There is no room available at this time");
			
		}
		
		if((time.compareTo(Time.valueOf("09:00:00")) < 0) && time.compareTo(Time.valueOf("20:00:00")) > 0) {
			
			throw new IllegalArgumentException("This is not a valid time");
			
		}
		
		Tutor t = tutorRepository.findTutorByUsername(tName);
		if (t == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_FindTutorByUsername);
		}
		
		boolean tIsAvailable = false;
		int tId = 0;
		List<Availability> tutorAvailabilities = getAvailabilityByTutor(tName);
		Availability av = null;
		for (Availability a : tutorAvailabilities) {
			
			Date avDate = a.getDate();
			Time avTime = a.getTime();
			
			if (date.toString().equals(avDate.toString()) && (time.toString().equals(avTime.toString()))) {
				
				if (a == null) {
					
					continue;
				}
				
				tIsAvailable = true;
				tId = a.getId();
				av = a;
				
				break;
				
			}
			
		}
		
		if(!tIsAvailable) {
			
			throw new IllegalArgumentException("The Tutor is busy during this time.");
			
		}
		
		Session session = new Session();
		CourseOffering co = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID));
		if (co == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_FindCourseOfferingByID);
		session.setCourseOffering(co);
		session.setDate(date);
		session.setTime(time);
		session.setAmountPaid(amountPaid);
		session.setConfirmed(false);
		List<Student> student = new ArrayList<Student>();
		if(studentRepository.findStudentByUsername(sName) == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_FindStudentByUsername);
		}
		session.setTutor(t);
		student.add(studentRepository.findStudentByUsername(sName));
		session.setStudent(student);
		if(tIsAvailable) {
			
			availabilityRepository.deleteById(tId);
			t.getAvailability().remove(av);
			
		}
		sessionRepository.save(session);
		return session;
	}
	
	//Checking to make sure we can update a session.
	@Transactional
	public Session updateSession(int oldID, int coID, Date date, Time time, Double amountPaid, String sName, String tName) {

		if(tName == null || tName.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_TutorName);
		}
		if(sName == null || sName.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_StudentName);
		}
		if(date == null || time == null){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_DateTime);
		}
		if(amountPaid < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_NegativeAmountPaid);
		}
		Session session = sessionRepository.findSessionBySessionID(oldID);
		if(session == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_FindSessionByID);
		CourseOffering co = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID));
		if (co == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_FindCourseOfferingByID);
		session.setCourseOffering(co);
		session.setDate(date);
		session.setTime(time);
		session.setAmountPaid(amountPaid);
		List<Student> student = new ArrayList<Student>();
		if(studentRepository.findStudentByUsername(sName) == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_FindStudentByUsername);
		student.add(studentRepository.findStudentByUsername(sName));
		session.setStudent(student);
		Tutor t = tutorRepository.findTutorByUsername(tName);
		if (t == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_FindTutorByUsername);
		sessionRepository.save(session);
		return session;
	}
	
	//Checking to make sure we can get all sessions.
	@Transactional
	public List<Session> getAllSessions() {
		return toList(sessionRepository.findAll());
	}
	
	//Checking to make sure we can get a session.
	@Transactional
	public Session getSession(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_ID);
		}
		Session a = sessionRepository.findSessionBySessionID(new Integer(id));
		return a;
	}
	
	//Checking to make sure we can delete a session.
	@Transactional
	public boolean deleteSession(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Session_ID);
		}
		
		
		boolean done = false;
		Session a = getSession(id);
		

		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		if (Period.between(currentDate, a.getDate().toLocalDate()).getDays() == 1) {
			
			int currentMinutes = Integer.parseInt(currentTime.toString().substring(0, 2));
			currentMinutes = currentMinutes * 60;
			currentMinutes = currentMinutes + Integer.parseInt(currentTime.toString().substring(3, 5));
			
			int sMinutes = Integer.parseInt(a.getTime().toString().substring(0, 2));
			sMinutes = sMinutes * 60;
			sMinutes = sMinutes + Integer.parseInt(a.getTime().toString().substring(3, 5));
			
			if(currentMinutes > sMinutes) {
				
				throw new IllegalArgumentException("It is too late to cancel a session!");
				
			}
			
		} else if (Period.between(currentDate, a.getDate().toLocalDate()).getDays() == 0) {
			
			throw new IllegalArgumentException("It is too late to cancel a session!");
			
		}
		if (a != null) {
			
			createAvailability(a.getDate(), a.getTime(), a.getTutor().getUsername());
			a.getTutor().getSession().remove(a);
			sessionRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	@Transactional
	public boolean checkAvailabilityUniqueForTutor(Tutor t, Availability av) {
		
		for (Availability tAv : t.getAvailability()) {
			
			if(tAv.getDate().toString().equals(av.getDate().toString()) && tAv.getTime().toString().equals(av.getTime().toString())) {
				
				throw new IllegalArgumentException("Availability already exists");
				
			} 
			
		}
		
		return true;
		
	}
	
	//Checking to make sure we can create a university.
	@Transactional
	public University createUniversity(String name, String addr) {

		if(name == null || name.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_University_Name);
		}
		if(addr == null || addr.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_University_Address);
		}
		
		University uni = new University();
		uni.setName(name);
		uni.setAddress(addr);
		//uni.setUniversityID(id);
		universityRepository.save(uni);
		return uni;
	}

	//Checking to make sure we can get all universities.
	@Transactional
	public List<University> getAllUniversities() {
		return toList(universityRepository.findAll());
	}
	
	//Checking to make sure we can create a user.
	@Transactional
	public TSUser createUser(String name, String email, int age, String phoneNum) {
		if(age < 12){
			throw new IllegalArgumentException(ErrorStrings.Invalid_User_AgeTooYoung);
		}
		if(name == null || name.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_User_Name);
		}
		if(email == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_User_Email);
		if(!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_User_Email);
		}
		
		//Special phone number check.
		if(!phoneNum.matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_User_PhoneNumber);
		}

		TSUser user = new TSUser();
		user.setName(name);
		user.setEmail(email);
		user.setAge(age);
		user.setPhoneNumber(phoneNum);
		userRepository.save(user);
		return user;
	}
	
	//Checking to make sure we can update a user.
	@Transactional
	public TSUser updateUser(String name, String oldEmail,String newEmail, int age, String phoneNum) {
		TSUser user = userRepository.findTSuserByEmail(oldEmail);
		user.setAge(age);
		user.setEmail(newEmail);
		user.setName(name);
		user.setPhoneNumber(phoneNum);
		userRepository.save(user);
		return user;
	}
	
	//Checking to make sure we can get a user.
	@Transactional
	public TSUser getUser(String email) {
		TSUser a = userRepository.findTSuserByEmail(email);
		return a;
	}
	
	//Checking to make sure we can delete a user.
	@Transactional
	public boolean deleteUser(String email) {
		boolean done = false;
		TSUser a = getUser(email);
		if (a != null) {
			userRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	//Checking to make sure we can get all users.
	@Transactional
	public List<TSUser> getAllUsers() {
		return toList(userRepository.findAll());
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}




	//	<----Room Bookings---->
	//Checking to get all rooms.
	@Transactional
	public List<Room> getAllRooms() {
		return toList(roomRepository.findAll());
	}

	//Checking to make sure we can create a room.
	@Transactional
	public Room createRoom(int RoomNumber) {
		if(RoomNumber < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Room_NegativeNumber);
		}
		Room room = new Room();
		room.setRoomNumber(RoomNumber);
		roomRepository.save(room);
		return room;
	}

	//Checking to make sure we can get a room.
	@Transactional
	public Room getRoom(int roomNumber) {
		Room a = roomRepository.findRoomByRoomNumber(roomNumber);
		return a;
	}

	//Checking to make sure we can delete a room.
	@Transactional
	public boolean deleteRoom(int id) {
		boolean done = false;
		Room a = getRoom(id);
		if (a != null) {
			roomRepository.delete(a);
			done = true;
		}
		return done;
	}

	//Checking to make sure we can update a room.
	@Transactional
	public Room updateRoom(int oldRoomNumber, int roomNumber) {
		if(roomNumber < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_Room_NegativeNumber);
		}
		Room room = roomRepository.findRoomByRoomNumber(oldRoomNumber);
		room.setRoomNumber(roomNumber);
		roomRepository.save(room);
		return room;
	}

	//

//	 <----University---->

	//Checking to make sure we can update a university.
	@Transactional
	public University updateUniversity(int oldID, String newName, String newAddress) {
		if(newName == null || newName.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_University_Name);
		}
		if(newAddress == null || newAddress.equals("")){
			throw new IllegalArgumentException(ErrorStrings.Invalid_University_Address);
		}
		University university = universityRepository.findUniversityByUniversityID(oldID);
		university.setAddress(newAddress);
		university.setName(newName);
		universityRepository.save(university);
		return university;
	}

	//Checking to make sure we can delete a university.
	@Transactional
	public boolean deleteUniversity(int id) {
		if(id < 0){
			throw new IllegalArgumentException(ErrorStrings.Invalid_University_ID);
		}
		boolean done = false;
		University a = universityRepository.findUniversityByUniversityID(id);
		if (a != null) {
			universityRepository.deleteById(id);
			done = true;
		}
		return done;
	}

	// get courses from specified university name
	@Transactional
	public List<Course> getAllCoursesByUniversity(String name) throws IllegalArgumentException {
		List<Course> courses = new ArrayList<>();
		
		// get all courses
		List<Course> allcourses = getAllCourses();
		if(allcourses == null)

			throw new IllegalArgumentException(ErrorStrings.Invalid_University_FindCourse);
		
		// filter by university name
		for(Course c : allcourses) {
			if(c.getUniversity().getName().equals(name))
				courses.add(c);
		}
		
		if(courses.size() == 0)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Service_CourseOfferedUni);
		
		return courses;
	}

	// get course offerings from specified course from associated university
	@Transactional
	public List<CourseOffering> getAllCourseOfferingsByCourse(String cname, String uniName) {
		List<CourseOffering> courseOs = new ArrayList<>();
		
		// get all course offerings
		List<CourseOffering> allcourseOs = getAllCourseOfferings();
		if(allcourseOs == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Service_CONone);
		
		for(CourseOffering co : allcourseOs) {
			// check name and university names that they are what we are looking for
			if(co.getCourse().getCourseName().equals(cname) 
					&& co.getCourse().getUniversity().getName().equals(uniName)) {
				courseOs.add(co);
			}
		}

		if(courseOs.size() == 0)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Service_TutorForCO);
		
		return courseOs;
	}
	

	@Transactional
	public boolean checkSessionConfirmed(Session s) {
		
		return s.isConfirmed();
		
	}
	
//	// get course offerings from specified course from associated university
//	public List<CourseOffering> getAllTutorsByCourseOffering(String cname, String uniName, String coname) {
//		List<CourseOffering> courseOs = getAllCourseOfferingsByCourse(cname, coname);
//		
//		// get all tutors
//		for(Tutor t : getAllTutors()) {
//			// check name and university names that they are what we are looking for
//			if(getCourse().getCourseName().equals(cname) 
//					&& co.getCourse().getUniversity().getName().equals(uniName))
//				courseOs.add(co);
//		}
//		return courseOs;
//	}

	// get tutors for specified course offering from associated course and university
	@Transactional
  public List<Tutor> getAllTutorsByCourseOffering(int id) {
		// find course offering from repository
		CourseOffering co = courseOfferingRepository.findCourseOfferingByCourseOfferingID(id);
		if(co == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Service_COBad);
		
		List<Tutor> tutors = new ArrayList<>();
		// get the tutors associated with it
		tutors = co.getTutors();

		if(tutors == null || tutors.size() == 0)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Service_TutorForCO);

		return tutors;
	}

	// find a tutor by the username
  @Transactional
	public Tutor findTutorByUsername(String username) {
		Tutor t = new Tutor();
		
		// find the correct tutor by the given username
		t = tutorRepository.findTutorByUsername(username);
		
		// check if it is null
		if (t == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_Service_Tutor);
		
		// otherwise return the found tutor
		return t;
	}
	
	// check is there is a room available at the give time
  @Transactional
	public boolean isRoomAvailable(Date date, Time startTime) {
		// check all rooms
		List<Room> rooms = getAllRooms();
		if(rooms != null) {
			for(Room r: rooms) {
				// set preliminary availability to true
				boolean isAvail = true;
				// look at all sessions of the room
				Set<Session> sessions = r.getSession();
				if(sessions != null) {
					for(Session s: new ArrayList<Session>(sessions)) {
						// if one of the sessions is at the same time, set available to false and move 
						// to the next room
						if((s.getTime().equals(startTime) && s.getDate().equals(date))) {
							isAvail = false;
							break;
						}
					}
				}
				// if none of the sessions for a room are at that time, then it is available
				if(isAvail == true)
					return true;
			}
		}
		return false;
	}

	//Gets all the reviews.
	@Transactional
	public Set<Review> getAllReviewsByTutor(String tutorUsername){
		
		Tutor tutor = getTutor(tutorUsername);
		if(tutor == null)
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Tutor);
		
		return tutor.getReview();
	}
				
	
	//Check to see if a session has passed or not.
	@Transactional
	public boolean isSessionActive(Session s) {
		if(s == null) {
			throw new IllegalArgumentException(ErrorStrings.Invalid_DTO_Availability);
		}
		//Compare the date.
		long millis=System.currentTimeMillis(); 
		Date currentDate = (Date) new java.util.Date(millis);
		
		if(s.getDate().compareTo(currentDate) > 0) {
			return true;
		} 
		return false;
	}
}
