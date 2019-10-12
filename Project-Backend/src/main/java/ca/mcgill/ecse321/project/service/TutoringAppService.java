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
		courseOffering.setCourse(courseRepository.findCourseByCourseID(courseID));
		courseOfferingRepository.save(courseOffering);
		return courseOffering;
	}
	
	@Transactional
	public CourseOffering updateCourseOffering(int oldID, int id, String term, int year, int courseID) {
		CourseOffering courseOffering = courseOfferingRepository.findCourseOfferingByCourseOfferingID(oldID);
		courseOffering.setCourseOfferingID(id);
		courseOffering.setYear(year);
		courseOffering.setTerm(term);
		courseOffering.setCourse(courseRepository.findCourseByCourseID(courseID));
		courseOfferingRepository.save(courseOffering);
		return courseOffering;
	}
	
	@Transactional
	public List<CourseOffering> getAllCourseOfferings() {
		return toList(courseOfferingRepository.findAll());
	}
	
	@Transactional
	public CourseOffering getCourseOffering(int id) {
		CourseOffering a = courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(id));
		return a;
	}
	
	@Transactional
	public boolean deleteCourseOffering(int id) {
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
		Course course = new Course();
		course.setDescription(description);
		course.setCourseName(courseName);
		course.setCourseID(id);
		course.setUniversity(universityRepository.findUniversityByUniversityID(uniID));
		courseRepository.save(course);
		return course;
	}
	
	@Transactional
	public Course updateCourse(int oldID, String description, String courseName, int id, int uniID) {
		Course course = courseRepository.findCourseByCourseID(oldID);
		course.setDescription(description);
		course.setCourseName(courseName);
		course.setCourseID(id);
		course.setUniversity(universityRepository.findUniversityByUniversityID(uniID));
		courseRepository.save(course);
		return course;
	}
	
	@Transactional
	public Course getCourse(int id) {
		Course a = courseRepository.findCourseByCourseID(new Integer(id));
		return a;
	}
	
	@Transactional
	public boolean deleteCourse(int id) {
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
		Text text = new Text();
		text.setDescription(description);
		text.setReviewID(id);
		text.setIsAllowed(isAllowed);
		text.setWrittenAbout(roleRepository.findRoleByUsername(revieweeUsername));
		text.setCourseOffering(courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID)));
		textRepository.save(text);
		return (Text)text;
	}
	
	@Transactional
	public Rating createRating(int id, int ratingValue, String revieweeUsername, int coID) {
		Rating rating = new Rating();
		rating.setRatingValue(ratingValue);
		rating.setReviewID(id);
		rating.setWrittenAbout(roleRepository.findRoleByUsername(revieweeUsername));
		rating.setCourseOffering(courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID)));
		ratingRepository.save(rating);
		return rating;
	}
	
	@Transactional
	public Tutor createTutor(String username, String password, String userEmail, double hourlyRate, int exp, Education level) {
		Tutor tutor = new Tutor();
		tutor.setUsername(username);
		tutor.setPassword(password);
		tutor.setUser(userRepository.findUserByEmail(userEmail));
		tutor.setHourlyRate(hourlyRate);
		tutor.setExperience(exp);
		tutor.setEducation(level);
		tutorRepository.save(tutor);
		return tutor;
	}
	
	@Transactional
	public Tutor createTutor(String oldUsername, String username, String password, String userEmail, double hourlyRate, int exp, Education level) {
		Tutor tutor = tutorRepository.findTutorByUsername(oldUsername);
		tutor.setUsername(username);
		tutor.setPassword(password);
		tutor.setUser(userRepository.findUserByEmail(userEmail));
		tutor.setHourlyRate(hourlyRate);
		tutor.setExperience(exp);
		tutor.setEducation(level);
		tutorRepository.save(tutor);
		return tutor;
	}
	
	@Transactional
	public Tutor getTutor(String username) {
		Tutor a = tutorRepository.findTutorByUsername(username);
		return a;
	}
	
	@Transactional
	public boolean deleteTutor(String username) {
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
		Student student = new Student();
		student.setUsername(username);
		student.setPassword(password);
		student.setUser(userRepository.findUserByEmail(userEmail));
		studentRepository.save(student);
		return student;
	}
	
	@Transactional
	public Student updateStudent(String oldUsername, String username, String password, String userEmail) {
		Student student = studentRepository.findStudentByUsername(oldUsername);
		student.setUsername(username);
		student.setPassword(password);
		student.setUser(userRepository.findUserByEmail(userEmail));
		studentRepository.save(student);
		return student;
	}
	
	@Transactional
	public Student getStudent(String username) {
		Student a = studentRepository.findStudentByUsername(username);
		return a;
	}
	
	@Transactional
	public boolean deleteStudent(String username) {
		boolean done = false;
		Student a = getStudent(username);
		if (a != null) {
			studentRepository.delete(a);
			done = true;
		}
		return done;
	}

	@Transactional
	public Session createSession(int coID, int date, int time, int amountPaid, int id, String sName, String tName) {
		Session session = new Session();
		session.setCourseOffering(courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID)));
		session.setDate(date);
		session.setTime(time);
		session.setAmountPaid(amountPaid);
		List<Student> student = new ArrayList<Student>();
		student.add(studentRepository.findStudentByUsername(sName));
		session.setStudent(student);
		session.setTutor(tutorRepository.findTutorByUsername(tName));
		session.setSessionID(id);
		sessionRepository.save(session);
		return session;
	}
	
	@Transactional
	public Session updateSession(int oldID, int coID, int date, int time, int amountPaid, int id, String sName, String tName) {
		Session session = sessionRepository.findSessionBySessionID(oldID);
		session.setCourseOffering(courseOfferingRepository.findCourseOfferingByCourseOfferingID(new Integer(coID)));
		session.setDate(date);
		session.setTime(time);
		session.setAmountPaid(amountPaid);
		List<Student> student = new ArrayList<Student>();
		student.add(studentRepository.findStudentByUsername(sName));
		session.setStudent(student);
		session.setTutor(tutorRepository.findTutorByUsername(tName));
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
		Session a = sessionRepository.findSessionBySessionID(new Integer(id));
		return a;
	}
	
	@Transactional
	public boolean deleteSession(int id) {
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
