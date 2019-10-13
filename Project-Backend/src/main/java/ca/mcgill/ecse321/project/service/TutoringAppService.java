package ca.mcgill.ecse321.project.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public Availability createAvailability(Date date, Time time, int id, String tName) {
				
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the availability...");
		}
		if(tName == null || tName.equals("")){
			throw new IllegalArgumentException("Invalid name...");
		}
		if(date == null){
			throw new IllegalArgumentException("Invalid date parameters...");
		}
		if(time == null){
			throw new IllegalArgumentException("Invalid time parameters...");
		}
				
		Availability availability = new Availability();
		availability.setAvailabilityID(id);
		availability.setTime(time);
		availability.setDate(date);
		availability.setTutor(tutorRepository.findTutorByUsername(tName));
		availabilityRepository.save(availability);
		return availability;
	}

	@Transactional
	public Availability updateAvailability(int oldID, Date date, Time time, int id, String tName) {
		if(id < 0 || oldID < 0){
			throw new IllegalArgumentException("Incorrect id value for the availability...");
		}
		if(tName == null || tName.equals("")){
			throw new IllegalArgumentException("Invalid name...");
		}
		if(date == null){
			throw new IllegalArgumentException("Invalid date parameters...");
		}
		if(time == null){
			throw new IllegalArgumentException("Invalid time parameters...");
		}
		
		Availability availability = availabilityRepository.findAvailabilityByAvailabilityID(oldID);
		availability.setAvailabilityID(id);
		availability.setTime(time);
		availability.setDate(date);
		Tutor t = tutorRepository.findTutorByUsername(tName);
		if( t == null)
			throw new IllegalArgumentException("Please specify a valid Tutor");
		availability.setTutor(t);
		availabilityRepository.save(availability);
		return availability;
	}
	
	@Transactional
	public List<Availability> getAllAvailabilities() {
		return toList(availabilityRepository.findAll());
	}
	
	@Transactional
	public Availability getAvailability(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the availability...");
		}
				
		Availability a = availabilityRepository.findAvailabilityByAvailabilityID(new Integer(id));
		return a;
	}
	
	@Transactional
	public List<Availability> getAvailabilityByTutor(String username) {
		Tutor tutor = (Tutor)roleRepository.findRoleByUsername(username);
		return toList(availabilityRepository.findAvailabilityByTutor(tutor));
	}
	
	@Transactional
	public boolean deleteAvailability(int id) {
		if(id < 0){
			throw new IllegalArgumentException("You are searching for an invalid id...");
		}

		boolean done = false;
		Availability a = getAvailability(id);
		if (a != null) {
			availabilityRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	@Transactional
	public boolean deleteAvailabilityByTutor(String username) {
		if(username == null || username.equals("")){
			throw new IllegalArgumentException("You are searching for an invalid username...");
		}

		boolean done = false;
		List<Availability> availList = getAvailabilityByTutor(username);
		for(Availability a: getAvailabilityByTutor(username)) {
			if (a != null) {
				availabilityRepository.delete(a);
			}
		}
		done = true;
		
		return done;
	}
	

	@Transactional
	public CourseOffering createCourseOffering(int id, String term, int year, int courseID) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the course offering...");
		}
		if(year < 1900){
			throw new IllegalArgumentException("That is far too long ago...");
		}
		if(courseID < 0){
			throw new IllegalArgumentException("Invalid courseID...");
		}
		if(!(term.equals("winter") || term.equals("summer") || term.equals("fall")) || term == null){
			throw new IllegalArgumentException("Invalid term choice...");
		}
		
		CourseOffering courseOffering = new CourseOffering();
		courseOffering.setCourseOfferingID(id);
		courseOffering.setYear(year);
		courseOffering.setTerm(term);
		Course c = courseRepository.findCourseByCourseID(courseID);
		if( c == null)
			throw new IllegalArgumentException("Please specify a valid Course");
		courseOffering.setCourse(c);
		courseOfferingRepository.save(courseOffering);
		return courseOffering;
	}
	
	@Transactional
	public CourseOffering updateCourseOffering(int oldID, int id, String term, int year, int courseID) {
		if(id < 0 || oldID < 0){
			throw new IllegalArgumentException("Incorrect id value for the course offering...");
		}
		if(year < 1900){
			throw new IllegalArgumentException("That is far too long ago...");
		}
		if(courseID < 0){
			throw new IllegalArgumentException("Invalid courseID...");
		}
		if(!(term.equals("winter") || term.equals("summer") || term.equals("fall"))){
			throw new IllegalArgumentException("Invalid term choice...");
		}
		CourseOffering courseOffering = courseOfferingRepository.findCourseOfferingByCourseOfferingID(oldID);
		if(courseOffering== null)
			throw new IllegalArgumentException("Please specify a valid Course Offering to Update");
		courseOffering.setCourseOfferingID(id);
		courseOffering.setYear(year);
		courseOffering.setTerm(term);
		Course c = courseRepository.findCourseByCourseID(courseID);
		if( c == null)
			throw new IllegalArgumentException("Please specify a valid Course");
		courseOffering.setCourse(c);
		courseOfferingRepository.save(courseOffering);
		return courseOffering;
	}
	
	@Transactional
	public List<CourseOffering> getAllCourseOfferings() {
		return toList(courseOfferingRepository.findAll());
	}
	
	@Transactional
	public CourseOffering getCourseOffering(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the course offering...");
		}
		CourseOffering a = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(id));
		return a;
	}
	
	@Transactional
	public boolean deleteCourseOffering(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the course offering...");
		}
		boolean done = false;
		CourseOffering a = getCourseOffering(id);
		if (a != null) {
			courseOfferingRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	@Transactional
	public Course createCourse(String description, String courseName, int id, int uniID) {
		if(id < 0 || uniID < 0){
			throw new IllegalArgumentException("Incorrect id value for the course creation...");
		}
		if(courseName == null || courseName.equals("")){
			throw new IllegalArgumentException("Please insert a course name to search...");
		}
		if(description == null || description.equals("")){
			throw new IllegalArgumentException("Please insert a brief description...");
		}
		
		Course course = new Course();
		course.setDescription(description);
		course.setCourseName(courseName);
		course.setCourseID(id);
		University u = universityRepository.findUniversityByUniversityID(uniID);
		if( u == null)
			throw new IllegalArgumentException("Please specify a valid University");
		course.setUniversity(u);
		courseRepository.save(course);
		return course;
	}
	
	@Transactional
	public Course updateCourse(int oldID, String description, String courseName, int id, int uniID) {
		if(id < 0 || uniID < 0 || oldID < 0){
			throw new IllegalArgumentException("Incorrect id value for the course update...");
		}
		if(courseName == null || courseName.equals("")){
			throw new IllegalArgumentException("Please insert a course name to search...");
		}
		if(description == null || description.equals("")){
			throw new IllegalArgumentException("Please insert a brief description...");
		}
		Course course = courseRepository.findCourseByCourseID(oldID);
		if( course == null)
			throw new IllegalArgumentException("Please specify a valid Course");
		course.setDescription(description);
		course.setCourseName(courseName);
		course.setCourseID(id);
		University u = universityRepository.findUniversityByUniversityID(uniID);
		if( u == null)
			throw new IllegalArgumentException("Please specify a valid University");
		course.setUniversity(u);
		courseRepository.save(course);
		return course;
	}
	
	@Transactional
	public Course getCourse(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the course request...");
		}
		Course a = courseRepository.findCourseByCourseID(new Integer(id));
		return a;
	}
	
	@Transactional
	public boolean deleteCourse(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the course search...");
		}
		boolean done = false;
		Course a = getCourse(id);
		if (a != null) {
			courseRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	@Transactional
	public List<Course> getAllCourses() {
		return toList(courseRepository.findAll());
	}
	
	@Transactional
	public Text createText(int id, String description, boolean isAllowed, String revieweeUsername, int coID) {
		if(id < 0 || coID < 0){
			throw new IllegalArgumentException("Incorrect id value");
		}
		if(revieweeUsername == null || revieweeUsername.equals("")){
			throw new IllegalArgumentException("Please insert a reviewee username...");
		}
		if(description == null || description.equals("")){
			throw new IllegalArgumentException("Please insert a brief description...");
		}
		
		Text text = new Text();
		text.setDescription(description);
		text.setReviewID(id);
		text.setIsAllowed(isAllowed);
		if(tutorRepository.findTutorByUsername(revieweeUsername) != null)
			text.setWrittenAbout(tutorRepository.findTutorByUsername(revieweeUsername));
		else if (studentRepository.findStudentByUsername(revieweeUsername) != null)
			text.setWrittenAbout(studentRepository.findStudentByUsername(revieweeUsername));
		else 
			throw new IllegalArgumentException("Please enter a valid Reviewee");
		CourseOffering c = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID));
		if(c == null)
			throw new IllegalArgumentException("Please enter a valid Course Offering");
		text.setCourseOffering(c);
		textRepository.save(text);
		return (Text)text;
	}
	
	@Transactional
	public Text updateText(int oldID, int id, String description, boolean isAllowed, String revieweeUsername, int coID) {
		if(id < 0 || coID < 0 || oldID < 0){
			throw new IllegalArgumentException("Incorrect id value");
		}
		if(revieweeUsername == null || revieweeUsername.equals("")){
			throw new IllegalArgumentException("Please insert a reviewee username...");
		}
		if(description == null || description.equals("")){
			throw new IllegalArgumentException("Please insert a brief description...");
		}
		
		Text text = textRepository.findTextByReviewID(oldID);
		if(text == null)
			throw new IllegalArgumentException("Please enter a valid Text Review to update");
		text.setDescription(description);
		text.setReviewID(id);
		text.setIsAllowed(isAllowed);
		if(tutorRepository.findTutorByUsername(revieweeUsername) != null)
			text.setWrittenAbout(tutorRepository.findTutorByUsername(revieweeUsername));
		else if (studentRepository.findStudentByUsername(revieweeUsername) != null)
			text.setWrittenAbout(studentRepository.findStudentByUsername(revieweeUsername));
		else 
			throw new IllegalArgumentException("Please enter a valid Reviewee");
		CourseOffering c = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID));
		if(c == null)
			throw new IllegalArgumentException("Please enter a valid Course Offering");
		text.setCourseOffering(c);
		textRepository.save(text);
		return (Text)text;
	}
	
	@Transactional
	public List<Text> getAllTexts() {
		return toList(textRepository.findAll());
	}
	
	@Transactional
	public Text getText(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the text request...");
		}

		Text a = textRepository.findTextByReviewID(id);
		return a;
	}
	
	@Transactional
	public boolean deleteText(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the text deletion...");
		}

		boolean done = false;
		Text a = getText(id);
		if (a != null) {
			textRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	@Transactional
	public Rating createRating(int id, int ratingValue, String revieweeUsername, int coID) {
		if(id < 0 || coID < 0){
			throw new IllegalArgumentException("Incorrect id value");
		}
		if(revieweeUsername == null || revieweeUsername.equals("")){
			throw new IllegalArgumentException("Please insert a reviewee username...");
		}
		if(ratingValue < 0){
			throw new IllegalArgumentException("You can't give your tutor a negative rating...");
		}
		
		Rating rating = new Rating();
		
		try {
		rating.setRatingValue(ratingValue);
		} catch(RuntimeException e) {
			throw new IllegalArgumentException("Rating value must be between 1 and 5");
		}
		
		rating.setReviewID(id);
		if(tutorRepository.findTutorByUsername(revieweeUsername) != null)
			rating.setWrittenAbout(tutorRepository.findTutorByUsername(revieweeUsername));
		else if (studentRepository.findStudentByUsername(revieweeUsername) != null)
			rating.setWrittenAbout(studentRepository.findStudentByUsername(revieweeUsername));
		else 
			throw new IllegalArgumentException("Please enter a valid Reviewee");
		CourseOffering c = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID));
		if(c == null)
			throw new IllegalArgumentException("Please enter a valid Course Offering");
		rating.setCourseOffering(c);
		ratingRepository.save(rating);
		return rating;
	}
	
	@Transactional
	public Rating updateRating(int oldID, int id, int ratingValue, String revieweeUsername, int coID) {
		if(id < 0 || coID < 0){
			throw new IllegalArgumentException("Incorrect id value");
		}
		if(revieweeUsername == null || revieweeUsername.equals("")){
			throw new IllegalArgumentException("Please insert a reviewee username...");
		}
		if(ratingValue < 0){
			throw new IllegalArgumentException("You can't give your tutor a negative rating...");
		}
		Rating rating = ratingRepository.findRatingByReviewID(oldID);
		if(rating == null)
			throw new IllegalArgumentException("Please enter a valid Rating Review to update");
		try {
		rating.setRatingValue(ratingValue);
		} catch(RuntimeException e) {
			throw new IllegalArgumentException("Rating value must be between 1 and 5");
		}
		rating.setReviewID(id);
		if(tutorRepository.findTutorByUsername(revieweeUsername) != null)
			rating.setWrittenAbout(tutorRepository.findTutorByUsername(revieweeUsername));
		else if (studentRepository.findStudentByUsername(revieweeUsername) != null)
			rating.setWrittenAbout(studentRepository.findStudentByUsername(revieweeUsername));
		else 
			throw new IllegalArgumentException("Please enter a valid Reviewee");
		rating.setCourseOffering(courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID)));
		ratingRepository.save(rating);
		return rating;
	}
	
	@Transactional
	public List<Rating> getAllRatings() {
		return toList(ratingRepository.findAll());
	}
	
	@Transactional
	public Rating getRating(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the rating search...");
		}
		
		Rating a = ratingRepository.findRatingByReviewID(id);
		return a;
	}
	
	@Transactional
	public boolean deleteRating(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the rating deletion...");
		}
		
		boolean done = false;
		Rating a = getRating(id);
		if (a != null) {
			ratingRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	@Transactional
	public Tutor createTutor(String username, String password, String userEmail, double hourlyRate, int exp, Education level) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException("Please insert a username...");
		}
		if(password.equals("") || password == null){
			throw new IllegalArgumentException("Please insert a password...");
		}
		//Regex check for email
		if(!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
			throw new IllegalArgumentException("Please insert a proper email...");
		}
		if(hourlyRate < 0){
			throw new IllegalArgumentException("Don't think you want to pay the student for your session...");
		}
		if(exp < 0){
			throw new IllegalArgumentException("Please input a valid number of years for your experience...");
		}
		if(level == null){
			throw new IllegalArgumentException("Please provide your education level...");
		}
		Tutor tutor = new Tutor();
		tutor.setUsername(username);
		tutor.setPassword(password);
		User u = userRepository.findUserByEmail(userEmail);
		if (u == null)
			throw new IllegalArgumentException("Please input a valid user");
		tutor.setUser(u);
		tutor.setHourlyRate(hourlyRate);
		tutor.setExperience(exp);
		tutor.setEducation(level);
		tutorRepository.save(tutor);
		return tutor;
	}
	
	@Transactional
	public Tutor updateTutor(String oldUsername, String username, String password, String userEmail, double hourlyRate, int exp, Education level) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException("Please insert a username...");
		}
		if(oldUsername.equals("") || oldUsername == null){
			throw new IllegalArgumentException("Please insert a proper username...");
		}
		if(password.equals("") || password == null){
			throw new IllegalArgumentException("Please insert a password...");
		}
		if(!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
			throw new IllegalArgumentException("Please insert a proper email...");
		}
		if(hourlyRate < 0){
			throw new IllegalArgumentException("Don't think you want to pay the student for your session...");
		}
		if(exp < 0){
			throw new IllegalArgumentException("Please input a valid number of years for your experience...");
		}
		if(level == null){
			throw new IllegalArgumentException("Please provide your education level...");
		}
		Tutor tutor = tutorRepository.findTutorByUsername(oldUsername);
		if(tutor == null)
			throw new IllegalArgumentException("Please input a valid tutor to update");
		tutor.setUsername(username);
		tutor.setPassword(password);
		User u = userRepository.findUserByEmail(userEmail);
		if (u == null)
			throw new IllegalArgumentException("Please input a valid user");
		tutor.setUser(u);
		tutor.setHourlyRate(hourlyRate);
		tutor.setExperience(exp);
		tutor.setEducation(level);
		tutorRepository.save(tutor);
		return tutor;
	}
	
	@Transactional
	public Tutor getTutor(String username) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException("Please insert a username...");
		}
		Tutor a = tutorRepository.findTutorByUsername(username);
		return a;
	}
	
	@Transactional
	public boolean deleteTutor(String username) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException("Please insert a username...");
		}
		boolean done = false;
		Tutor a = getTutor(username);
		if (a != null) {
			tutorRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	@Transactional
	public List<Student> getAllStudents() {
		return toList(studentRepository.findAll());
	}
	
	@Transactional
	public List<Tutor> getAllTutors() {
		return toList(tutorRepository.findAll());
	}
	
	@Transactional
	public Student createStudent(String username, String password, String userEmail) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException("Please insert a username...");
		}
		if(password.equals("") || password == null){
			throw new IllegalArgumentException("Please insert a password...");
		}
		if(!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
			throw new IllegalArgumentException("Please insert a proper email...");
		}
		Student student = new Student();
		student.setUsername(username);
		student.setPassword(password);
		User u = userRepository.findUserByEmail(userEmail);
		if (u == null)
			throw new IllegalArgumentException("Please input a valid user");
		student.setUser(u);
		studentRepository.save(student);
		return student;
	}
	
	@Transactional
	public Student updateStudent(String oldUsername, String username, String password, String userEmail) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException("Please insert a better username...");
		}
		if(oldUsername.equals("") || oldUsername == null){
			throw new IllegalArgumentException("Please insert a username...");
		}
		if(password.equals("") || password == null){
			throw new IllegalArgumentException("Please insert a password...");
		}
		if(!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
			throw new IllegalArgumentException("Please insert a proper email...");
		}
		Student student = studentRepository.findStudentByUsername(oldUsername);
		if(student == null)
			throw new IllegalArgumentException("Please input a valid student");
		student.setUsername(username);
		student.setPassword(password);
		User u = userRepository.findUserByEmail(userEmail);
		if (u == null)
			throw new IllegalArgumentException("Please input a valid user");
		student.setUser(u);
		studentRepository.save(student);
		return student;
	}
	
	@Transactional
	public Student getStudent(String username) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException("Please insert a username...");
		}
		Student a = studentRepository.findStudentByUsername(username);
		return a;
	}
	
	@Transactional
	public boolean deleteStudent(String username) {
		if(username.equals("") || username == null){
			throw new IllegalArgumentException("Please insert a username...");
		}
		boolean done = false;
		Student a = getStudent(username);
		if (a != null) {
			studentRepository.delete(a);
			done = true;
		}
		return done;
	}

	@Transactional
	public Session createSession(int coID, Date date, Time time, Double amountPaid, int id, String sName, String tName) {
			
		if(id < 0 || coID < 0){
			throw new IllegalArgumentException("Incorrect id value for the session creation...");
		}
		if(tName == null || tName.equals("")){
			throw new IllegalArgumentException("Invalid name...");
		}
		if(sName == null || sName.equals("")){
			throw new IllegalArgumentException("Invalid name...");
		}
		if(date == null || time == null){
			throw new IllegalArgumentException("Invalid time parameters...");
		}
		if(amountPaid < 0){
			throw new IllegalArgumentException("So your student is paying you?? Please provide positive amount paid...");
		}

		Session session = new Session();
		CourseOffering co = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID));
		if (co == null)
			throw new IllegalArgumentException("Please input a valid course offering");
		session.setCourseOffering(co);
		session.setDate(date);
		session.setTime(time);
		session.setAmountPaid(amountPaid);
		List<Student> student = new ArrayList<Student>();
		if(studentRepository.findStudentByUsername(sName) == null)
			throw new IllegalArgumentException("Please input a valid student");
		student.add(studentRepository.findStudentByUsername(sName));
		session.setStudent(student);
		Tutor t = tutorRepository.findTutorByUsername(tName);
		if (t == null)
			throw new IllegalArgumentException("Please input a valid tutor");
		session.setTutor(t);
		session.setSessionID(id);
		sessionRepository.save(session);
		return session;
	}
	
	@Transactional
	public Session updateSession(int oldID, int coID, Date date, Time time, Double amountPaid, int id, String sName, String tName) {
		
		if(id < 0 || coID < 0 || oldID < 0){
			throw new IllegalArgumentException("Incorrect id value for the session update...");
		}
		if(tName == null || tName.equals("")){
			throw new IllegalArgumentException("Invalid name...");
		}
		if(sName == null || sName.equals("")){
			throw new IllegalArgumentException("Invalid name...");
		}
		if(date == null || time == null){
			throw new IllegalArgumentException("Invalid time parameters...");
		}
		if(amountPaid < 0){
			throw new IllegalArgumentException("So your student is paying you?? Please provide positive amount paid...");
		}
		Session session = sessionRepository.findSessionBySessionID(oldID);
		if(session == null)
			throw new IllegalArgumentException("Please input a valid session");
		CourseOffering co = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID));
		if (co == null)
			throw new IllegalArgumentException("Please input a valid course offering");
		session.setCourseOffering(co);
		session.setDate(date);
		session.setTime(time);
		session.setAmountPaid(amountPaid);
		List<Student> student = new ArrayList<Student>();
		if(studentRepository.findStudentByUsername(sName) == null)
			throw new IllegalArgumentException("Please input a valid student");
		student.add(studentRepository.findStudentByUsername(sName));
		session.setStudent(student);
		Tutor t = tutorRepository.findTutorByUsername(tName);
		if (t == null)
			throw new IllegalArgumentException("Please input a valid tutor");
		session.setSessionID(id);
		sessionRepository.save(session);
		return session;
	}
	
	@Transactional
	public List<Session> getAllSessions() {
		return toList(sessionRepository.findAll());
	}
	
	@Transactional
	public Session getSession(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the session search...");
		}
		Session a = sessionRepository.findSessionBySessionID(new Integer(id));
		return a;
	}
	
	@Transactional
	public boolean deleteSession(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the session deletion...");
		}
		boolean done = false;
		Session a = getSession(id);
		if (a != null) {
			sessionRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	@Transactional
	public University createUniversity(String name, String addr, int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the university creation...");
		}
		if(name == null || name.equals("")){
			throw new IllegalArgumentException("Invalid name...");
		}
		if(addr == null || addr.equals("")){
			throw new IllegalArgumentException("Invalid address...");
		}
		
		University uni = new University();
		uni.setName(name);
		uni.setAddress(addr);
		uni.setUniversityID(id);
		universityRepository.save(uni);
		return uni;
	}
	
	@Transactional
	public List<University> getAllUniversities() {
		return toList(universityRepository.findAll());
	}
	
	@Transactional
	public User createUser(String name, String email, int age, double phoneNum) {
		if(age < 12){
			throw new IllegalArgumentException("Must be above the age of 12 for this tutoring service...");
		}
		if(name == null || name.equals("")){
			throw new IllegalArgumentException("Invalid name...");
		}
		if(email == null)
			throw new IllegalArgumentException("Please insert a proper email...");
		if(!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
			throw new IllegalArgumentException("Please insert a proper email...");
		}
		
		//Special phone number check.
		if(!Double.toString(phoneNum).matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")){
			throw new IllegalArgumentException("Invalid phone number...");
		}

		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setAge(age);
		user.setPhoneNumber(phoneNum);
		userRepository.save(user);
		return user;
	}
	
	@Transactional
	public User updateUser(String name, String oldEmail,String newEmail, int age,double phoneNum) {
		User user = userRepository.findUserByEmail(oldEmail);
		user.setAge(age);
		user.setEmail(newEmail);
		user.setName(name);
		user.setPhoneNumber(phoneNum);
		userRepository.save(user);
		return user;
	}
	
	@Transactional
	public User getUser(String email) {
		User a = userRepository.findUserByEmail(email);
		return a;
	}
	
	@Transactional
	public boolean deleteUser(String email) {
		boolean done = false;
		User a = getUser(email);
		if (a != null) {
			userRepository.delete(a);
			done = true;
		}
		return done;
	}
	
	@Transactional
	public List<User> getAllUsers() {
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

	@Transactional
	public List<Room> getAllRooms() {
		return toList(roomRepository.findAll());
	}

	@Transactional
	public Room createRoom(int RoomNumber) {
		if(RoomNumber < 0){
			throw new IllegalArgumentException("Room number cannot be negative");
		}
		Room room = new Room();
		room.setRoomNumber(RoomNumber);
		roomRepository.save(room);
		return room;
	}

	@Transactional
	public Room getRoom(int roomNumber) {
		Room a = roomRepository.findRoomByRoomNumber(roomNumber);
		return a;
	}

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

	@Transactional
	public Room updateRoom(int oldRoomNumber, int roomNumber) {
		if(roomNumber < 0){
			throw new IllegalArgumentException("Room number cannot be negative");
		}
		Room room = roomRepository.findRoomByRoomNumber(oldRoomNumber);
		room.setRoomNumber(roomNumber);
		roomRepository.save(room);
		return room;
	}

	//

//	 <----University---->

	@Transactional
	public University updateUniversity(int oldID, String newName, String newAddress,int id) {
		if(id < 0){
			throw new IllegalArgumentException("Incorrect id value for the university creation...");
		}
		if(newName == null || newName.equals("")){
			throw new IllegalArgumentException("Invalid name...");
		}
		if(newAddress == null || newAddress.equals("")){
			throw new IllegalArgumentException("Invalid address...");
		}
		University university = universityRepository.findUniversityByUniversityID(oldID);
		university.setUniversityID(id);
		university.setAddress(newAddress);
		university.setName(newName);
		universityRepository.save(university);
		return university;
	}

	@Transactional
	public boolean deleteUniversity(int id) {
		if(id < 0){
			throw new IllegalArgumentException("You are searching for an invalid id...");
		}
		boolean done = false;
		Availability a = getAvailability(id);
		if (a != null) {
			universityRepository.deleteById(id);
			done = true;
		}
		return done;
	}

	
//										Reviews

	
}
