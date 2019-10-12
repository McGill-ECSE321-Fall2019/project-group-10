package ca.mcgill.ecse321.project.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
	RoleRepository roleRepository;
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	SessionRepository sessionRepository;
	@Autowired
	UniversityRepository universityRepository;
	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public Availability createAvailability(int date, int time, int id, String tName) {
		Availability availability = new Availability();
		availability.setAvailabilityID(id);
		availability.setTime(time);
		availability.setDate(date);
		availability.setTutor((Tutor)roleRepository.findRoleByUsername(tName));
		availabilityRepository.save(availability);
		return availability;
	}

	@Transactional
	public Availability updateAvailability(int oldID, int date, int time, int id, String tName) {
		Availability availability = availabilityRepository.findAvailabilityByAvailabilityID(oldID);
		availability.setAvailabilityID(id);
		availability.setTime(time);
		availability.setDate(date);
		availability.setTutor((Tutor)roleRepository.findRoleByUsername(tName));
		availabilityRepository.save(availability);
		return availability;
	}
	
	@Transactional
	public List<Availability> getAllAvailabilities() {
		return toList(availabilityRepository.findAll());
	}
	
	@Transactional
	public Availability getAvailability(int id) {
		Availability a = availabilityRepository.findAvailabilityByID(new Integer(id));
		return a;
	}
	
	@Transactional
	public List<Availability> getAvailabilityByTutor(String username) {
		Tutor tutor = (Tutor)roleRepository.findRoleByUsername(username);
		return toList(availabilityRepository.findAvailabiltyByTutor(tutor));
	}
	
	@Transactional
	public boolean deleteAvailability(int id) {
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
		CourseOffering courseOffering = new CourseOffering();
		courseOffering.setCourseOfferingID(id);
		courseOffering.setYear(year);
		courseOffering.setTerm(term);
<<<<<<< HEAD
		courseOffering.setCourse(courseRepository.findCourseByCourseID(courseID));
=======
		courseOffering.setCourses(course);
>>>>>>> 1e69f5501645633c158c02c846febb27941a610d
		courseOfferingRepository.save(courseOffering);
		return courseOffering;
	}
	
	@Transactional
	public List<CourseOffering> getAllCourseOfferings() {
		return toList(courseOfferingRepository.findAll());
	}
	
	@Transactional
	public Course createCourse(String description, String courseName, int id, int uniID) {
		Course course = new Course();
		course.setDescription(description);
		course.setCourseName(courseName);
		course.setCourseID(id);
		course.setUniversity(universityRepository.findUniversityByUniversityID(uniID));
		courseRepository.save(course);
		return course;
	}
	
	@Transactional
<<<<<<< HEAD
	public Text createText(int id, String description, boolean isAllowed, String revieweeUsername, int coID) {
		Review text = new Text();
=======
	public Text createText(int id, String description, boolean isAllowed, Role reviewee, CourseOffering co) {
		Text text = new Text();
>>>>>>> 1e69f5501645633c158c02c846febb27941a610d
		text.setDescription(description);
		text.setReviewID(id);
		text.setIsAllowed(isAllowed);
		text.setWrittenAbout(roleRepository.findRoleByUsername(revieweeUsername));
		text.setCourseOffering(courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID)));
		reviewRepository.save(text);
		return (Text)text;
	}
	
	@Transactional
<<<<<<< HEAD
	public Rating createRating(int id, RatingValue value, String revieweeUsername, int coID) {
		Review rating = new Rating();
		rating.setRatingValue(value);
=======
	public Rating createRating(int id, int ratingValue, Role reviewee, CourseOffering co) {
		Rating rating = new Rating();
		rating.setRatingValue(ratingValue);
>>>>>>> 1e69f5501645633c158c02c846febb27941a610d
		rating.setReviewID(id);
		rating.setWrittenAbout(roleRepository.findRoleByUsername(revieweeUsername));
		rating.setCourseOffering(courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID)));
		reviewRepository.save(rating);
		return rating;
	}
	
	@Transactional
<<<<<<< HEAD
	public Tutor createTutor(String username, String password, String userEmail, int hourlyRate, int exp, Education level) {
		Role tutor = new Tutor();
=======
	public Tutor createTutor(String username, String password, User user, double hourlyRate, int exp, Education level) {
		Tutor tutor = new Tutor();
>>>>>>> 1e69f5501645633c158c02c846febb27941a610d
		tutor.setUsername(username);
		tutor.setPassword(password);
		tutor.setUser(userRepository.findUserByEmail(userEmail));
		tutor.setHourlyRate(hourlyRate);
		tutor.setExperience(exp);
		tutor.setEducation(level);
		roleRepository.save(tutor);
		return (Tutor)tutor;
	}
	
	@Transactional
	public List<Role> getAllRoles() {
		return toList(roleRepository.findAll());
	}
	
	@Transactional
	public Student createStudent(String username, String password, String userEmail) {
		Role student = new Student();
		student.setUsername(username);
		student.setPassword(password);
		student.setUser(userRepository.findUserByEmail(userEmail));
		roleRepository.save(student);
		return (Student)student;
	}

	@Transactional
	public Session createSession(CourseOffering co, int date, int time, int amountPaid, int id, String sName, String tName) {
		Session session = new Session();
		session.setCourseOffering(co); //Not in the domain model
		session.setDate(date);
		session.setTime(time); //Not in the domain model
		session.setAmountPaid(amountPaid);
<<<<<<< HEAD
		session.setStudent((Student)roleRepository.findRoleByUsername(sName));
		session.setTutor((Tutor)roleRepository.findRoleByUsername(tName));
=======
		session.setStudent(s); //Not in the domain model
		session.setTutor(t);
>>>>>>> 1e69f5501645633c158c02c846febb27941a610d
		session.setSessionID(id);
		sessionRepository.save(session);
		return session;
	}
	
	@Transactional
	public List<Session> getAllSessions() {
		return toList(sessionRepository.findAll());
	}
	
	@Transactional
	public University createUniversity(String name, String addr, int id) {
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
	public User createUser(String name, String email, int age, int phoneNum) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setAge(age);
		user.setPhoneNumber(phoneNum);
		userRepository.save(user);
		return user;
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
}
