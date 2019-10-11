package dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import model.Course;

public interface CourseRepository extends CrudRepository<Course, Integer>{

	Course findCourseByCourseID(Integer courseID);
	
	<List>Course findCourseByUniversity(University uni);

}