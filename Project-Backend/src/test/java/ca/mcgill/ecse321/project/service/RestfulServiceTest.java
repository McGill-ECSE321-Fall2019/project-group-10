package ca.mcgill.ecse321.project.service;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.project.ProjectApplication;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.model.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestfulServiceTest {
	
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
	
	@Test
	public void runApplication() {
		// INSTRUCTIONS:
		// first delete everything from the repositories
		// then run the setup code
		// then delete again
		String purpose = "startup";
		switch(purpose) {
			case "startup": deleteAll(); break;
			case "create": setUp(); break;
			case "end": deleteAll(); break;
		}
			
		// needed for Junit test
		assertEquals(1,1);
		
	}
	
	public void setUp() {
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
		
		// create some rooms
		Room r1 = service.createRoom(1);
		Room r2 = service.createRoom(2);
		Room r3 = service.createRoom(3);
		
		// create a tutor
		service.createUser("aName", "tutor.tester@mcgill.ca", 22, "5145555555");
		Tutor t = service.createTutor("username", "password", "tutor.tester@mcgill.ca", 12, 3, Education.highschool);
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
		
		// create a student
		service.createUser("aName_student", "student.tester@mcgill.ca", 22, "5145555555");
		service.createStudent("studentUser", "password2", "student.tester@mcgill.ca");
		
		// create a session
		Session s1 = service.createSession(service.getAllCourseOfferings().get(0).getCourseOfferingID(), 
				Date.valueOf("2018-02-01"), Time.valueOf("11:11:11"), 23.50, 
				service.getAllStudents().get(0).getUsername(), 
				service.getAllTutors().get(0).getUsername());
		s1.setRoom(r1);
		r1.addSession(s1);
		
		Session s2 = service.createSession(service.getAllCourseOfferings().get(0).getCourseOfferingID(), 
				Date.valueOf("2019-02-01"), Time.valueOf("11:11:11"), 23.50, 
				service.getAllStudents().get(0).getUsername(), 
				service.getAllTutors().get(0).getUsername());
		s2.setRoom(r2);
		r2.addSession(s2);
		
		Session s3 = service.createSession(service.getAllCourseOfferings().get(0).getCourseOfferingID(), 
				Date.valueOf("2019-02-01"), Time.valueOf("11:11:11"), 23.50, 
				service.getAllStudents().get(0).getUsername(), 
				service.getAllTutors().get(0).getUsername());
		s3.setRoom(r3);
		r3.addSession(s3);
		
		// create some reviews
		service.createText("Best tutor ever", true, "username", c1.getCourseOfferingID());
		service.createRating(5, "username", c1.getCourseOfferingID());
		service.createText("Worst tutor ever", true, "username", c2.getCourseOfferingID());
		service.createRating(1, "username", c3.getCourseOfferingID());
		
		courseOfferingRepository.save(c1);
		courseOfferingRepository.save(c2);
		courseOfferingRepository.save(c3);
		courseOfferingRepository.save(c4);
		tutorRepository.save(t);
	}
	
	
	public void deleteAll() {
		// clear in order of dependencies
		sessionRepository.deleteAll();
		roomRepository.deleteAll();
		reviewRepository.deleteAll();
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
