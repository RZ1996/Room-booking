package room_booking.room_booking.event;
import java.time.LocalDateTime;

public record BookingEventMessage(String type, Long bookingId, String userEmail, String roomName,
                                  LocalDateTime start, LocalDateTime end) {
}
