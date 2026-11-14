package room_booking.room_booking.dto;
import lombok.Data;

@Data
public class RoomDTO {

    private Long id;
    private String name;
    private int capacity;
    private boolean active;
}