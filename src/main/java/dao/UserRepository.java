package dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import model.User;

public interface UserRepository extends CrudRepository<User, String>{

	User findUserByEmail(String email);

}