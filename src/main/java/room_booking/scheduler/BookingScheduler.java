package room_booking.room_booking.scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import room_booking.room_booking.entity.State;
import room_booking.room_booking.repository.BookingRepository;
import java.time.LocalDateTime;

@Component
public class BookingScheduler {

    private static final Logger log = LoggerFactory.getLogger(BookingScheduler.class);

    private final BookingRepository bookingRepository;

    public BookingScheduler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Scheduled(cron = "0 */5 * * * *")
    @Transactional
    public void finishPastBookings() {
        int finished = bookingRepository.finishPastBookings(State.OPENED, State.FINISHED, LocalDateTime.now());
        if (finished > 0) {
            log.info("Finished {} past bookings", finished);
        }
    }
}