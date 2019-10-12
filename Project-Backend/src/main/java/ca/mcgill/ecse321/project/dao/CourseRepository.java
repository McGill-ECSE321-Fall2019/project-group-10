package ca.mcgill.ecse321.project.dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import ca.mcgill.ecse321.project.model.*;

public interface CourseRepository extends CrudRepository<Course, Integer>{

	Course findCourseByCourseID(Integer courseID);
	
	<List>Course findCourseByUniversity(University uni);

}