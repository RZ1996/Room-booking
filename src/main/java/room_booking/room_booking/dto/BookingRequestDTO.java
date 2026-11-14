package room_booking.room_booking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {

    @NotNull
    private Long roomId;

    @NotNull
    private LocalDateTime startOfReservation;

    @NotNull
    private LocalDateTime endOfReservation;

    @Size(max = 200)
    private String note;
}