package dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import model.University;

public interface UniversityRepository extends CrudRepository<University, Integer>{

	University findUniversityByUniversityID(Integer universityID);

}