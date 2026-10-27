package room_booking.room_booking.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDTO {

    private LocalDateTime startOfReservation;
    private LocalDateTime endOfReservation;
    private String note;
}
