package dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import model.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer>{

	Review findReviewByReviewID(Integer reviewID);
	
	<List>Review findReviewbyRole(Role role);

}
