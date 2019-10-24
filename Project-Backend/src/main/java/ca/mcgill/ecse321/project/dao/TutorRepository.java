package ca.mcgill.ecse321.project.dao;

//import CRUD from spring
import org.springframework.data.repository.CrudRepository;

//import model class
import ca.mcgill.ecse321.project.model.*;

public interface TutorRepository extends CrudRepository<Tutor, Integer>{
	
	Tutor findTutorByUsername(String username);
	
	<List>Tutor findTutorByUser(TSUser user);

}
