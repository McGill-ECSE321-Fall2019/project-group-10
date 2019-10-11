package ca.mcgill.ecse321.project.dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import ca.mcgill.ecse321.project.model.User;

public interface UserRepository extends CrudRepository<User, String>{

	User findUserByEmail(String email);

}