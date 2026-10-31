package room_booking.room_booking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import room_booking.room_booking.entity.Booking;
import room_booking.room_booking.entity.Color;
import room_booking.room_booking.entity.State;

import java.time.LocalDateTime;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByColorAndStateAndStartOfReservationLessThanAndEndOfReservationGreaterThan(
            Color color, State state, LocalDateTime newEnd, LocalDateTime newStart);

    boolean existsByColorAndStateAndStartOfReservationLessThanAndEndOfReservationGreaterThanAndIdNot(
            Color color, State state, LocalDateTime newEnd, LocalDateTime newStart, Long id);

}
