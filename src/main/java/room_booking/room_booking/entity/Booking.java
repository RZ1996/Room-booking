package room_booking.room_booking.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(indexes = {
        @Index(name = "idx_booking_color_state_start_end",
                columnList = "color, state, startOfReservation, endOfReservation"),
        @Index(name = "idx_booking_user_state_start",
                columnList = "user_id, state, startOfReservation")
})
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="startOfReservation", nullable=false)
    private LocalDateTime startOfReservation;

    @Column(name="endOfReservation", nullable=false)
    private LocalDateTime  endOfReservation;

    @Column(name="note", length=200)
    private String note;

    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    private State state;

}

