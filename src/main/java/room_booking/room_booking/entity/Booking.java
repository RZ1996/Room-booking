package room_booking.room_booking.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime startOfReservation;
    private LocalDateTime  endOfReservation;
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
