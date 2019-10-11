package dao;

// import CRUD from spring
import org.springframework.data.repository.CrudRepository;

// import model class
import model.Room;

public interface RoomRepository extends CrudRepository<Room, Integer>{

	Room findRoomByRoomNumber(Integer roomNum);

}