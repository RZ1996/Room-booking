package room_booking.room_booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDTO {

    private Long id;
    private String color;
    private String state;
    private LocalDateTime startOfReservation;
    private LocalDateTime endOfReservation;
    private String note;
}