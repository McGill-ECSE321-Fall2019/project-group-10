package ca.mcgill.ecse321.project.dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import ca.mcgill.ecse321.project.model.TSUser;

public interface UserRepository extends CrudRepository<TSUser, Integer>{

	TSUser findTSuserById(Integer id);
	TSUser findTSuserByEmail(String email);

}