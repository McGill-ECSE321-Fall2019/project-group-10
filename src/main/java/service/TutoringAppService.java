package service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.*;
import model.*;

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
	public Availability createAvailability(int date, int time, int id, Tutor tutor) {
		Availability availability = new Availability();
		availability.setAvailabilityID(id);
		availability.setTime(time);
		availability.setDate(date);
		availability.setTutor(tutor);
		availabilityRepository.save(availability);
		return availability;
	}
	
	@Transactional
	public List<Availability> getAllAvailabilities() {
		return toList(availabilitytRepository.findAll());
	}
	
	@Transactional
	public Availability getAvailability(int id) {
		Availability a = availabilityRepository.findAvailabilityByAvailabilityID(new Integer(id));
		return a;
	}
	
	@Transactional
	public List<Availability> getAvailabilityByTutor(String username) {
		Tutor tutor = (Tutor)roleRepository.findRoleByUsername(username);
		return toList(availabilityRepository.findAvailabilityByTutor(tutor));
	}

	@Transactional
	public CourseOffering createCourseOffering(int id, String term, int year, Course course) {
		CourseOffering courseOffering = new CourseOffering();
		courseOffering.setCourseOfferingID(id);
		courseOffering.setYear(year);
		courseOffering.setTerm(term);
		courseOffering.setCourse(course);
		courseOfferingRepository.save(courseOffering);
		return courseOffering;
	}
	
	@Transactional
	public Course createCourse(String description, String courseName, int id, University uni) {
		Course course = new Course();
		course.setDescription(description);
		course.setCourseName(courseName);
		course.setCourseID(id);
		course.setUniversity(uni);
		courseRepository.save(course);
		return course;
	}
	
	@Transactional
	public Text createText(int id, String description, boolean isAllowed, Role reviewee, CourseOffering co) {
		Review text = new Text();
		text.setDescription(description);
		text.setReviewID(id);
		text.setIsAllowed(isAllowed);
		text.setWrittenAbout(reviewee);
		text.setCourseOffering(co);
		reviewRepository.save(text);
		return text;
	}
	
	@Transactional
	public Rating createRating(int id, RatingValue value, Role reviewee, CourseOffering co) {
		Review rating = new Rating();
		rating.setRatingValue(value);
		rating.setReviewID(id);
		rating.setWrittenAbout(reviewee);
		rating.setCourseOffering(co);
		reviewRepository.save(rating);
		return rating;
	}
	
	@Transactional
	public Tutor createTutor(String username, String password, User user, int hourlyRate, int exp, Education level) {
		Role tutor = new Tutor();
		tutor.setUsername(username);
		tutor.setPassword(password);
		tutor.setUser(user);
		tutor.setHourlyRate(hourlyRate);
		tutor.setExperience(exp);
		tutor.setEducation(level);
		roleRepository.save(tutor);
		return tutor;
	}
	
	@Transactional
	public List<Role> getAllRoles() {
		return toList(roleRepository.findAll());
	}
	
	@Transactional
	public Student createStudent(String username, String password, User user) {
		Role student = new Student();
		student.setUsername(username);
		student.setPassword(password);
		student.setUser(user);
		roleRepository.save(student);
		return student;
	}

	@Transactional
	public Session createSession(CourseOffering co, int date, int time, int amountPaid, int id, Student s, Tutor t) {
		Session session = new Session();
		session.setCourseOffering(co);
		session.setDate(date);
		session.setTime(time);
		session.setAmountPaid(amountPaid);
		session.setStudent(s);
		session.setTutor(t);
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
		user.setEmail(email)
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
