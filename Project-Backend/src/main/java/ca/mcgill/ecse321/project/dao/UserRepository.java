package ca.mcgill.ecse321.project.dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import ca.mcgill.ecse321.project.model.TSUser;

public interface UserRepository extends CrudRepository<TSUser, String>{

	TSUser findUserByEmail(java.lang.String email);

}