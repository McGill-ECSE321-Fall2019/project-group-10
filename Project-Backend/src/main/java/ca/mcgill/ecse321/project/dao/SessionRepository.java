package ca.mcgill.ecse321.project.dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import ca.mcgill.ecse321.project.model.*;

public interface SessionRepository extends CrudRepository<Session, Integer>{

	Session findSessionBySessionID(Integer sessionID);
	
	boolean existsByTutorAndStudent(Tutor tutor, Student student);

}