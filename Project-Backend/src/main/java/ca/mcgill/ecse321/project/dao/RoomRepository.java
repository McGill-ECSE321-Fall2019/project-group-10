package ca.mcgill.ecse321.project.dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import ca.mcgill.ecse321.project.model.*;

public interface RoomRepository extends CrudRepository<Room, Integer>{

	Room findRoomByRoomNumber(Integer roomNum);
	
	Room findRoomById(Integer id);

}