package room_booking.room_booking.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {
    private String Color;
    private String State;
    private LocalDateTime startOfReservation;
    private LocalDateTime endOfReservation;
    private String note;
}
