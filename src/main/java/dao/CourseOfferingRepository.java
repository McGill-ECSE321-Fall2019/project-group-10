package dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import model.CourseOffering;

public interface CourseOfferingRepository extends CrudRepository<CourseOffering, Integer>{

	CourseOffering findCourseOfferingByCourseOfferingID(Integer courseOfferingID);
	
	<List>CourseOffering findByCourse(Course course);
	
	<List>CourseOffering findByTermAndYear(String term, Integer year);

}