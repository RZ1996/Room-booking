package room_booking.room_booking.entity;
import jakarta.persistence.Entity;
import java.time.LocalDate;


@Entity
public class Booking {
    private Long id;
    private User user;
    private LocalDate startOfReservation;
    private LocalDate endOfReservation;
    private String note;

    enum Color{
        BLUE,
        GREEN,
        RED
    }

    enum State{
        ACTIVE,
        CANCELED
    }



}
