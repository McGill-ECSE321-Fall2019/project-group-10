package ca.mcgill.ecse321.project.dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import ca.mcgill.ecse321.project.model.*;

public interface SessionRepository extends CrudRepository<Session, Integer>{

	Session findSessionBySessionID(Integer sessionID);
	
	<List>Session findSessionByRole(Role role);
	
	<List>Session findSessionByDate(Integer date);
	
	boolean existsByTutorAndStudent(Tutor tutor, Student student);

}