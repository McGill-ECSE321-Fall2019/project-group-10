package ca.mcgill.ecse321.project.dao;

//import CRUD from spring
import org.springframework.data.repository.CrudRepository;

//import model class
import ca.mcgill.ecse321.project.model.*;

public interface StudentRepository extends CrudRepository<Student, String>{
	
	Student findStudentByUsername(String username);
	
	<List>Student findStudentByUser(TSUser user);

}

