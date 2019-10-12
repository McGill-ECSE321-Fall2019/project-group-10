package ca.mcgill.ecse321.project.dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import ca.mcgill.ecse321.project.model.*;

import java.util.List;

public interface AvailabilityRepository extends CrudRepository<Availability, Integer>{

	Availability findAvailabilityByAvailabilityID(Integer id);
	
	List<Availability> findAvailabilityByTutor(Tutor tutor);

}