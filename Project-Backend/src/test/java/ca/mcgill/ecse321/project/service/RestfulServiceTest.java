// from: https://www.codejava.net/java-se/networking/an-http-utility-class-to-send-getpost-request
package ca.mcgill.ecse321.project.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.After;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;

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
	
	//@Autowired
	//private MockMvc mockMvc;
	
	private String host = "http://localhost:";
	private String port = "8080";
	private String COid1 = "1";
	private String COid2 = "1";
	
	//@Test
	public void runApplication() {
		// INSTRUCTIONS:
		// first delete everything from the repositories
		// then run the setup code
		// then delete again
		String purpose = "end1";
		switch(purpose) {
			case "startup": deleteAll(); break;
			case "create": createObjects(); break;
			case "end": deleteAll(); break;
			default: break;
		}
			
		// needed for Junit test
		assertEquals(1,1);
		
	}
	
	@Before
	public void setup() {
		createObjects();
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
		COid1 = (new Integer(c1.getCourseOfferingID())).toString();
		COid2 = (new Integer(c2.getCourseOfferingID())).toString();
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
//		
//		// create a student
//		service.createUser("aName_student", "student.tester@mcgill.ca", 22, "5145555555");
//		service.createStudent("studentUser", "password2", "student.tester@mcgill.ca");
//		
//		// create a session
//		Session s1 = service.createSession(service.getAllCourseOfferings().get(0).getCourseOfferingID(), 
//				Date.valueOf("2018-02-01"), Time.valueOf("11:11:11"), 23.50, 
//				service.getAllStudents().get(0).getUsername(), 
//				service.getAllTutors().get(0).getUsername());
//		s1.setRoom(r1);
//		r1.addSession(s1);
//		
//		Session s2 = service.createSession(service.getAllCourseOfferings().get(0).getCourseOfferingID(), 
//				Date.valueOf("2019-02-01"), Time.valueOf("11:11:11"), 23.50, 
//				service.getAllStudents().get(0).getUsername(), 
//				service.getAllTutors().get(0).getUsername());
//		s2.setRoom(r2);
//		r2.addSession(s2);
//		
//		Session s3 = service.createSession(service.getAllCourseOfferings().get(0).getCourseOfferingID(), 
//				Date.valueOf("2019-02-01"), Time.valueOf("11:11:11"), 23.50, 
//				service.getAllStudents().get(0).getUsername(), 
//				service.getAllTutors().get(0).getUsername());
//		s3.setRoom(r3);
//		r3.addSession(s3);
//		
//		// create some reviews
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
	
	@After
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
	
	@Test
	public void getUniversities() {
		HttpURLConnection httpCon = null;
		String response = null;
		try {
			httpCon = doGETRequest("/universities");
			response = readMultipleLinesRespone(httpCon);
	    } catch (MalformedURLException e) {
            System.out.println("The specified URL is malformed: " + e.getMessage());
            fail();
	    } catch (IOException e) {
            System.out.println("An I/O error occurs: " + e.getMessage());
        }
		String expected = "[{\"name\":\"McGill\",\"address\":\"3040 University\"},"
				+ "{\"name\":\"Concordia\",\"address\":\"3040 Guy\"},"
				+ "{\"name\":\"University of Montreal\",\"address\":\"3040 Cote des Neiges\"}]";
		assertEquals(expected, response);
	}
	
	@Test
	public void getCourses() {
		HttpURLConnection httpCon = null;
		String response = null;
		try {
			httpCon = doGETRequest("/courses");
			response = readMultipleLinesRespone(httpCon);
	    } catch (MalformedURLException e) {
            System.out.println("The specified URL is malformed: " + e.getMessage());
            fail();
	    } catch (IOException e) {
            System.out.println("An I/O error occurs: " + e.getMessage());
        }
		String expected = "[{\"courseName\":\"ECSE 321\","
				+ "\"description\":\"Intro to Software\","
				+ "\"uniName\":\"McGill\"},{\"courseName\":\"ECSE "
				+ "211\",\"description\":\"DPM\",\"uniName\":\"McGill\"},"
				+ "{\"courseName\":\"ECSE 354\",\"description\":\"Electromagnetic "
				+ "Waves\",\"uniName\":\"Concordia\"},"
				+ "{\"courseName\":\"ECSE 362\",\"description\":"
				+ "\"Power\",\"uniName\":\"University of Montreal\"}]";
		assertEquals(expected, response);
	}
	
	@Test
	public void getCoursesByUniversity() {
		HttpURLConnection httpCon = null;
		String response = null;
		try {
			httpCon = doGETRequest("/universities/McGill");
			response = readMultipleLinesRespone(httpCon);
	    } catch (MalformedURLException e) {
            System.out.println("The specified URL is malformed: " + e.getMessage());
            fail();
	    } catch (IOException e) {
            System.out.println("An I/O error occurs: " + e.getMessage());
        }
		String expected = "[{\"courseName\":\"ECSE 321\",\"description\""
				+ ":\"Intro to Software\",\"uniName\":\"McGill\"},"
				+ "{\"courseName\":\"ECSE 211\",\"description\":\"DPM\","
				+ "\"uniName\":\"McGill\"}]";
		assertEquals(expected, response);
	}
	
//	@Test
//	public void getCourseOfferings() {
//		HttpURLConnection httpCon = null;
//		String response = null;
//		try {
//			httpCon = doGETRequest("/courses/McGill/ECSE%20321");
//			response = readMultipleLinesRespone(httpCon);
//	    } catch (MalformedURLException e) {
//            System.out.println("The specified URL is malformed: " + e.getMessage());
//            fail();
//	    } catch (IOException e) {
//            System.out.println("An I/O error occurs: " + e.getMessage());
//        }
//		String expected = "[{\"id\":"
//				+ COid1
//				+ ",\"description\":2019,\"isAllowed\":\"Fall\"},"
//				+ "{\"id\":"
//				+ COid2
//				+ ",\"description\":2020,\"isAllowed\":\"Winter\"}]";
//		assertEquals(expected, response);
//	}
//	
//	@Test
//	public void getTutorsByCourseOffering() {
//		HttpURLConnection httpCon = null;
//		String response = null;
//		
//		try {
//			httpCon = doGETRequest("/courseoffering/" + COid1);
//			response = readMultipleLinesRespone(httpCon);
//	    } catch (MalformedURLException e) {
//            System.out.println("The specified URL is malformed: " + e.getMessage());
//            fail();
//	    } catch (IOException e) {
//            System.out.println("An I/O error occurs: " + e.getMessage());
//        }
//		String expected = "[{\"hourlyRate\":12.0,\"experience\":3,\"education\":"
//				+ "\"highschool\",\"ratings\":[{\"rating\":5},"
//				+ "{\"rating\":1}],\"texts\":[],\"avails\":[],\"user\":null,"
//				+ "\"username\":\"username\"}]";
//		assertEquals(expected, response);
//	}
	
	@Test
	public void getTutorsByUsername() {
		HttpURLConnection httpCon = null;
		String response = null;
		
		try {
			httpCon = doGETRequest("/tutor/username");
			response = readMultipleLinesRespone(httpCon);
	    } catch (MalformedURLException e) {
            System.out.println("The specified URL is malformed: " + e.getMessage());
            fail();
	    } catch (IOException e) {
            System.out.println("An I/O error occurs: " + e.getMessage());
        }
		String expected = "{\"hourlyRate\":12.0,\"experience\":3,\"education\":"
				+ "\"highschool\",\"ratings\":[{\"rating\":5},"
				+ "{\"rating\":1}],\"texts\":[],\"avails\":[],\"user\":null,"
				+ "\"username\":\"username\"}";
		assertEquals(expected, response);
	}
	
	public static String readMultipleLinesRespone(HttpURLConnection httpConn) throws IOException {
        InputStream inputStream = null;
        if (httpConn != null) {
            inputStream = httpConn.getInputStream();
        } else {
            throw new IOException("Connection not established.");
        }
 
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        String response = "";
 
        String line = "";
        while ((line = reader.readLine()) != null) {
            response = response + line;
        }
        reader.close();

        return response;
    }
	
	public HttpURLConnection doGETRequest(String extension)  throws IOException{
		
		URL url = new URL(host+port+extension);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoInput(true);
		httpCon.setRequestMethod("GET");
		
		return httpCon;
	}
	
	// FOR POST TESTS: copied this code from https://www.codejava.net/java-se/networking/an-http-utility-class-to-send-getpost-request
	  public static HttpURLConnection sendPostRequest(String requestURL,
	            Map<String, String> params, HttpURLConnection httpConn) throws IOException {
	        URL url = new URL(requestURL);
	        httpConn = (HttpURLConnection) url.openConnection();
	        httpConn.setUseCaches(false);
	 
	        httpConn.setDoInput(true); // true indicates the server returns response
	 
	        StringBuffer requestParams = new StringBuffer();
	 
	        if (params != null && params.size() > 0) {
	 
	            httpConn.setDoOutput(true); // true indicates POST request
	 
	            // creates the params string, encode them using URLEncoder
	            Iterator<String> paramIterator = params.keySet().iterator();
	            while (paramIterator.hasNext()) {
	                String key = paramIterator.next();
	                String value = params.get(key);
	                requestParams.append(URLEncoder.encode(key, "UTF-8"));
	                requestParams.append("=").append(
	                        URLEncoder.encode(value, "UTF-8"));
	                requestParams.append("&");
	            }
	 
	            // sends POST data
	            OutputStreamWriter writer = new OutputStreamWriter(
	                    httpConn.getOutputStream());
	            writer.write(requestParams.toString());
	            writer.flush();
	        }
	 
	        return httpConn;
	    }

}
