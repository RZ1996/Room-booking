package room_booking.room_booking.dto;
import lombok.Data;
import room_booking.room_booking.entity.Color;
import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {
    private Color color;
    private LocalDateTime startOfReservation;
    private LocalDateTime endOfReservation;
    private String note;
}
