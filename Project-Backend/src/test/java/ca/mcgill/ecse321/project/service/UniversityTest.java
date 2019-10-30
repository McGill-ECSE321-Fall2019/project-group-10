package ca.mcgill.ecse321.project.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.project.model.*;
import ca.mcgill.ecse321.project.ErrorStrings;
import ca.mcgill.ecse321.project.dao.*;
import ca.mcgill.ecse321.project.service.TutoringAppService;

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
	@Autowired
	private TutorRepository tutorRepository; 
	@Autowired
	private StudentRepository studentRepository; 

	@Before
	public void clearDatabase1() {
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

	// try to create a new university
	@Test
	public void testCreateUniversity() {
//		sets up information to create a university
		String address="65 Sherbrooke St. East";
		String name= "McGill University";


		try {
			service.createUniversity(name, address);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		// check that all the attributes are correct
		List<University> allUniversities = service.getAllUniversities(); //checks the list of all the universities

		assertEquals(1, allUniversities.size()); //checks the size of the universityList
		assertEquals(name,allUniversities.get(0).getName()); //checks the name of the university
		assertEquals(address, allUniversities.get(0).getAddress()); //checks the address of the university

	}


	// try to update a university case
	@Test
	public void testUpdateUniversity() {
//		sets up the university information
		String name = "McGill University";
		String address = "65 Sherbrooke St East";

		try {
			service.createUniversity(name, address);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		// check that there is one university in the repo
		List<University> allUniversities = service.getAllUniversities(); //creates a list of universities
		// check that there is one university in the repo
		assertEquals(1, allUniversities.size());

		name = "Concordia University";
		address = "8080 Sherbrooke St West";

		try {
			service.updateUniversity(allUniversities.get(0).getUniversityID(), name, address);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		allUniversities = service.getAllUniversities();

		// check that all there is only one university still
		assertEquals(1, allUniversities.size()); //check if the university is getting updated
		assertEquals(name, allUniversities.get(0).getName()); // checks if the name of the university is updated
		assertEquals(address, allUniversities.get(0).getAddress()); //checks if the address of university is updated
	}


	// try to delete a university
	@Test
	public void testDeleteUniversity() {
		assertEquals(0, service.getAllUniversities().size());

		String name = "McGill University";
		String address = "65 Sherbrooke St East";

		// create a university to delete
		try {
			service.createUniversity(name, address);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		
		List<University> allUniversities = service.getAllUniversities(); //gets the list of all the universities
		assertEquals(1, allUniversities.size()); //checks if the list is populating 

		try {
			service.deleteUniversity(allUniversities.get(0).getUniversityID());
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		// check that is was deleted
		allUniversities = service.getAllUniversities();
		assertEquals(0, allUniversities.size());
	}

	// create a university with a null name
	@Test
	public void testCreateUniversityNullName() {

		String address="65 Sherbrooke St. East";
		String name= null;

		String error = null;
		try {
			service.createUniversity(name, address);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check the correct error
		assertEquals(ErrorStrings.Invalid_University_Name, error);
		List<University> allUniversities = service.getAllUniversities(); //gets the list of all the universities
		assertEquals(0, allUniversities.size()); //validates the university is not being added to the list

	}

	// create a university with a null address
	@Test
	public void testCreateUniversityNullAddress() {

		String address=null;
		String name= "McGill University";


		String error = null;
		try {
			service.createUniversity(name, address);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		// check the correct error
		assertEquals(ErrorStrings.Invalid_University_Address, error);
		List<University> allUniversities = service.getAllUniversities(); //gets the list of all the universities
		assertEquals(0, allUniversities.size()); //checks if the university is not added to the list 

	}

}
