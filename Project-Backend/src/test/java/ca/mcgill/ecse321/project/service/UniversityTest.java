package ca.mcgill.ecse321.project.service;

import org.junit.After; 
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.service.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UniversityTest {
	
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
	private RoomRepository roomRepository; 
	@Autowired
	private SessionRepository sessionRepository; 
	@Autowired
	private UniversityRepository universityRepository; 
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void setUp(){
		service.createUniversity("McGill", "3040 University", 1);
	}
	
	@After
	public void clearDatabase() {
		sessionRepository.deleteAll();
		roomRepository.deleteAll();
		reviewRepository.deleteAll();
		courseOfferingRepository.deleteAll();
		courseRepository.deleteAll();
		universityRepository.deleteAll();
		availabilityRepository.deleteAll();
		roleRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	public void testCreateUniversity() {

		int id = 1;
		String address="65 Sherbrooke St. East";
		String name= "McGill University";
		

		try {
			service.createUniversity(name, address, id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<University> allUniversities = service.getAllUniversities();

		assertEquals(1, allUniversities.size());
		assertEquals(id, allUniversities.get(0).getUniversityID());
		assertEquals(name,allUniversities.get(0).getName());
		assertEquals(address, allUniversities.get(0).getAddress());
		
		}
	
	
	@Test
	public void testUpdateUniversity() {

		
		
		String name = "McGill University";
		String address = "65 Sherbrooke St East";
		int id = 1;
		
		try {
			service.createUniversity(name, address, id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<University> allUniversities = service.getAllUniversities();

		assertEquals(1, allUniversities.size());
		
		name = "Concordia University";
		address = "8080 Sherbrooke St West";
		id = 2;
		try {
			service.updateUniversity(name, address, id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertEquals(1, allUniversities.size());
		assertEquals(name, allUniversities.get(0).getName());
		assertEquals(address, allUniversities.get(0).getAddress());
		assertEquals(id, allUniversities.get(0).getUniversityID());
	}
	
	
	
	@Test
	public void testDeleteSession() {
		assertEquals(0, service.getAllUniversities().size());
		
		String name = "McGill University";
		String address = "65 Sherbrooke St East";
		int id = 1;
		
		try {
			service.createUniversity(name, address, id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		List<University> allUniversities = service.getAllUniversities();
		assertEquals(1, allUniversities.size());
		
		try {
			service.deleteUniversity(id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		 allUniversities = service.getAllUniversities();
		 assertEquals(0, allUniversities.size());
	}
	
}
