package ca.mcgill.ecse321.project.service;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.model.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestObjects {
	
	@Autowired
	private TutoringAppService service;
	@Autowired 
	private AvailabilityRepository availabilityRepository;
	@Autowired
	private CourseOfferingRepository courseOfferingRepository;  
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private ReviewRepository reviewRepository;  
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private RoomRepository roomRepository; 
	@Autowired
	private SessionRepository sessionRepository; 
	@Autowired
	private UniversityRepository universityRepository; 
	@Autowired
	private UserRepository userRepository;
	private static final Date AVAILABILITY_DATE = Date.valueOf(LocalDate.now().plusDays(3));
	private static final Time AVAILABILITY_TIME = Time.valueOf("11:30:00");
	private static final Date AVAILABILITY_DATE2 = Date.valueOf(LocalDate.now().plusDays(4));
	private static final Time AVAILABILITY_TIME2 = Time.valueOf("12:30:00");
	
	@Test
	public void runApplication() {
		// INSTRUCTIONS:
		// first delete everything from the repositories
		// then run the setup code
		// then delete again
		String purpose = "end";
		switch(purpose) {
			case "startup": deleteAll(); break;
			case "create": deleteAll(); createObjects(); break;
			case "end": deleteAll(); break;
			default: break;
		}
			
		// needed for Junit test
		assertEquals(1,1);
		
	}
	
	public void createObjects() {
		// save some objects in the database to test the restful apis
		
		// create universities
		service.createUniversity("McGill", "3040 University");
		service.createUniversity("Concordia", "3040 Guy");
		service.createUniversity("University of Montreal", "3040 Cote des Neiges");
		
		// create courses attached to universities
		service.createCourse("Intro to Software","ECSE 321", service.getAllUniversities().get(0).getUniversityID());
		service.createCourse("DPM","ECSE 211", service.getAllUniversities().get(0).getUniversityID());
		service.createCourse("Electromagnetic Waves","ECSE 354", service.getAllUniversities().get(1).getUniversityID());
		service.createCourse("Power","ECSE 362", service.getAllUniversities().get(2).getUniversityID());
		
		// create course offerings attached to courses 
		CourseOffering c1 = service.createCourseOffering(Term.Fall, 2019, service.getAllCourses().get(0).getCourseID());
		CourseOffering c2 = service.createCourseOffering(Term.Winter, 2020, service.getAllCourses().get(0).getCourseID());
		CourseOffering c3 = service.createCourseOffering(Term.Fall, 2019, service.getAllCourses().get(1).getCourseID());
		CourseOffering c4 = service.createCourseOffering(Term.Fall, 2019, service.getAllCourses().get(2).getCourseID());
//		// create some rooms
//		service.createRoom(1);
//		service.createRoom(2);
//		service.createRoom(3);
		
		// create a tutor
		service.createUser("aName", "alexander.gruenwald19@gmail.com", 22, "5145555555");
		Tutor t = service.createTutor("agruenwald", "password", "alexander.gruenwald19@gmail.com", 12, 3, Education.highschool);
		List<CourseOffering> tutoredCourses = new ArrayList<>();
		tutoredCourses.add(c1);
		tutoredCourses.add(c2);
		tutoredCourses.add(c3);
		tutoredCourses.add(c4);
		t.setCourseOfferings(tutoredCourses);
		c1.addTutor(t);
		c2.addTutor(t);
		c3.addTutor(t);
		c4.addTutor(t);
		
		service.createAvailability(AVAILABILITY_DATE, AVAILABILITY_TIME, "agruenwald");
		service.createAvailability(AVAILABILITY_DATE2, AVAILABILITY_TIME2, "agruenwald");
		
//		// create a student
		service.createUser("Student", "student.tester@mcgill.ca", 24, "5145555552");
		Student s = service.createStudent("cmc", "dogs", "student.tester@mcgill.ca");
		studentRepository.save(s);
		
//		// create a room
		service.createRoom(1);
		
//		// create a session
		service.createSession(c1.getCourseOfferingID(), AVAILABILITY_DATE, AVAILABILITY_TIME, 12.0, "cmc", "agruenwald");
		
		// create some reviews
		service.createText("Best tutor ever", true, "agruenwald", c1.getCourseOfferingID());
		service.createRating(5, "agruenwald", c1.getCourseOfferingID());
		service.createText("Worst tutor ever", true, "agruenwald", c2.getCourseOfferingID());
		service.createRating(1, "agruenwald", c3.getCourseOfferingID());
		
		courseOfferingRepository.save(c1);
		courseOfferingRepository.save(c2);
		courseOfferingRepository.save(c3);
		courseOfferingRepository.save(c4);
		tutorRepository.save(t);
	}
	
	public void deleteAll() {
		// clear in order of dependencies
		roomRepository.deleteAll();
		reviewRepository.deleteAll();
		sessionRepository.deleteAll();
		courseOfferingRepository.deleteAll();
		courseRepository.deleteAll();
		universityRepository.deleteAll();
		availabilityRepository.deleteAll();
		roleRepository.deleteAll();
		tutorRepository.deleteAll();
		studentRepository.deleteAll();
		userRepository.deleteAll();
	}
}
