package room_booking.room_booking.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Modifying
    @Query("UPDATE Booking b SET b.state = :finished, b.version = b.version + 1 " +
            "WHERE b.state = :opened AND b.endOfReservation < :now")
    int finishPastBookings(@Param("opened") State opened,
                           @Param("finished") State finished,
                           @Param("now") LocalDateTime now);
}