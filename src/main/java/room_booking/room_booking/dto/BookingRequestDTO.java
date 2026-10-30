package room_booking.room_booking.dto;
import lombok.Data;
import room_booking.room_booking.entity.Color;
import room_booking.room_booking.entity.State;
import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {
    private Color color;
    private State state;
    private LocalDateTime startOfReservation;
    private LocalDateTime endOfReservation;
    private String note;
}
