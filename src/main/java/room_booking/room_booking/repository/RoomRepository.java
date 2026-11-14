package room_booking.room_booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import room_booking.room_booking.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
