package room_booking.room_booking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import room_booking.room_booking.entity.Color;

import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {

    @NotNull
    private Color color;

    @NotNull
    private LocalDateTime startOfReservation;

    @NotNull
    private LocalDateTime endOfReservation;

    @Size(max = 200)
    private String note;
}