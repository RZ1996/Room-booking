package room_booking.room_booking.event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BookingNotificationListener {

    private static final Logger log = LoggerFactory.getLogger(BookingNotificationListener.class);

    @TransactionalEventListener
    public void onBookingCreated(BookingCreatedEvent event) {
        log.info("EMAIL to {}: booking {} for {} from {} to {} is confirmed",
                event.userEmail(), event.bookingId(), event.roomName(), event.start(), event.end());
    }

    @TransactionalEventListener
    public void onBookingCancelled(BookingCancelledEvent event) {
        log.info("EMAIL to {}: booking {} for {} from {} to {} was cancelled",
                event.userEmail(), event.bookingId(), event.roomName(), event.start(), event.end());
    }
}