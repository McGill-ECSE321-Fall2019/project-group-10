package ca.mcgill.ecse321.project.service;

import org.junit.After; 
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;

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
	
	@Mock
	private TutoringAppService service;

	@Mock 
	private AvailabilityRepository availabilityRepository;
	@Mock
	private CourseOfferingRepository courseOfferingRepository;  
	@Mock
	private CourseRepository courseRepository;
	@Mock
	private ReviewRepository reviewRepository;  
	@Mock
	private RoleRepository roleRepository; 
	@Mock
	private RoomRepository roomRepository; 
	@Mock
	private SessionRepository sessionRepository; 
	@Mock
	private UniversityRepository universityRepository; 
	@Mock
	private UserRepository userRepository;
	
	
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
			service.updateUniversity(1, name, address, id);
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
	public void testDeleteUniversity() {
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
	
	@Test
	public void testCreateUniversityNullName() {

		int id = 1;
		String address="65 Sherbrooke St. East";
		String name= null;
		

		String error = null;
		try {
			service.createUniversity(name, address, id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals("Invalid name...", error);
		List<University> allUniversities = service.getAllUniversities();
		assertEquals(0, allUniversities.size());
		
		}
	
	@Test
	public void testCreateUniversityNullAddress() {

		int id = 1;
		String address=null;
		String name= "McGill University";
		

		String error = null;
		try {
			service.createUniversity(name, address, id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals("Invalid address...", error);
		List<University> allUniversities = service.getAllUniversities();
		assertEquals(0, allUniversities.size());
		
		}
	
	@Test
	public void testCreateUniversityInvalidID() {

		int id = -1;
		String address="65 Sherbrooke St. East";
		String name= "McGill University";
		

		String error = null;
		try {
			service.createUniversity(name, address, id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		assertEquals("Incorrect id value for the university creation...", error);
		List<University> allUniversities = service.getAllUniversities();
		assertEquals(0, allUniversities.size());
		
		}
	
}
