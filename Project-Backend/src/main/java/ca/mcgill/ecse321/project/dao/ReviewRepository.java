package ca.mcgill.ecse321.project.dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import ca.mcgill.ecse321.project.model.*;

public interface ReviewRepository extends CrudRepository<Review, Integer>{

	Review findReviewByReviewID(Integer reviewID);
	
	<List>Review findReviewbyRole(Role role);

}
