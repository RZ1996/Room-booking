package room_booking.room_booking.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import room_booking.room_booking.entity.Booking;
import room_booking.room_booking.entity.State;
import java.time.LocalDateTime;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByRoomIdAndStateAndStartOfReservationLessThanAndEndOfReservationGreaterThan(
            Long roomId, State state, LocalDateTime newEnd, LocalDateTime newStart);

    boolean existsByRoomIdAndStateAndStartOfReservationLessThanAndEndOfReservationGreaterThanAndIdNot(
            Long roomId, State state, LocalDateTime newEnd, LocalDateTime newStart, Long id);

    Long countByUserIdAndStateAndStartOfReservationGreaterThan(Long userId, State state, LocalDateTime now);

    Page<Booking> findAllBookingByUserId(Long userId, Pageable pageable);

    Page<Booking> findByRoomId(Long roomId, Pageable pageable);
}