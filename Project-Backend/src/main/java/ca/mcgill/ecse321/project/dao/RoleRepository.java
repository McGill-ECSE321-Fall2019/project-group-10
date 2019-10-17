package ca.mcgill.ecse321.project.dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import ca.mcgill.ecse321.project.model.*;

public interface RoleRepository extends CrudRepository<Role, String>{

	Role findRoleByUsername(String username);
	
	<List>Role findRoleByUser(TSUser user);

}