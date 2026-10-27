package room_booking.room_booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import room_booking.room_booking.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
