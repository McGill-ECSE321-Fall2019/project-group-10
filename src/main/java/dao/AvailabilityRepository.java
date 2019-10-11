package dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import model.Availability;

import java.util.List;

public interface AvailabilityRepository extends CrudRepository<Availability, Integer>{

	Availability findAvailabilityByID(Integer id);
	
	List<Availability> findByTutor(Tutor tutor);

}