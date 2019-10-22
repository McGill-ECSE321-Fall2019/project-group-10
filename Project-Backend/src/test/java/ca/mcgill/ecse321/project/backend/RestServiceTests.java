package ca.mcgill.ecse321.project.backend;

import ca.mcgill.ecse321.project.model.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.service.TutoringAppService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestServiceTests {
	
	@Autowired
	private TutoringAppService service;
	@Autowired 
	private AvailabilityRepository availabilityRepository;
	@Autowired
	private static CourseOfferingRepository courseOfferingRepository;  
	@Autowired
	private static CourseRepository courseRepository;
	@Autowired
	private ReviewRepository reviewRepository;  
	@Autowired
	private RoleRepository roleRepository; 
	@Autowired
	private RoomRepository roomRepository; 
	@Autowired
	private SessionRepository sessionRepository; 
	@Autowired
	private static UniversityRepository universityRepository; 
	@Autowired
	private UserRepository userRepository;

	@Test
	public void test() {
		// TODO Auto-generated method stub
		
		//String task = "create";
		String task = "delete";
		
		if(task == "create") {
			service.createUniversity("McGill", "3040 University");
			
			service.createCourse("Intro to Software","ECSE 321", service.getAllUniversities().get(0).getUniversityID());
			service.createCourseOffering(Term.Fall, 2019, service.getAllCourses().get(0).getCourseID());
			service.createCourseOffering(Term.Winter, 2020, service.getAllCourses().get(0).getCourseID());
			
			service.createCourse("Control","ECSE 403", service.getAllUniversities().get(0).getUniversityID());
			service.createCourseOffering(Term.Fall, 2019, service.getAllCourses().get(1).getCourseID());
			
			service.createUniversity("Concordia", "30 Guy");
			service.createCourse("DPM","ECSE 211", service.getAllUniversities().get(1).getUniversityID());
			service.createCourseOffering(Term.Fall, 2019, service.getAllCourses().get(2).getCourseID());
		}
		else {
			courseOfferingRepository.deleteAll();
			courseRepository.deleteAll();
			universityRepository.deleteAll();
		}

	}

}
