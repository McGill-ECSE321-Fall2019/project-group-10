package dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import model.Role;

public interface RoleRepository extends CrudRepository<Role, String>{

	Role findRoleByUsername(String username);
	
	<List>Role findRoleByUser(User user);

}