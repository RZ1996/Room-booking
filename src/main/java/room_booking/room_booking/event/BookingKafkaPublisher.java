package room_booking.room_booking.event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BookingKafkaPublisher {

    public static final String TOPIC = "booking-events";

    private static final Logger log = LoggerFactory.getLogger(BookingKafkaPublisher.class);

    private final KafkaTemplate<String, BookingEventMessage> kafkaTemplate;

    public BookingKafkaPublisher(KafkaTemplate<String, BookingEventMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @TransactionalEventListener
    public void onBookingCreated(BookingCreatedEvent event) {
        send(new BookingEventMessage("CREATED", event.bookingId(), event.userEmail(),
                event.roomName(), event.start(), event.end()));
    }

    @TransactionalEventListener
    public void onBookingCancelled(BookingCancelledEvent event) {
        send(new BookingEventMessage("CANCELLED", event.bookingId(), event.userEmail(),
                event.roomName(), event.start(), event.end()));
    }

    private void send(BookingEventMessage message) {
        try {
            kafkaTemplate.send(TOPIC, message.bookingId().toString(), message);
            log.info("Sent {} event for booking {} to Kafka", message.type(), message.bookingId());
        } catch (Exception ex) {
            log.error("Could not send {} event for booking {} to Kafka", message.type(), message.bookingId(), ex);
        }
    }
}
