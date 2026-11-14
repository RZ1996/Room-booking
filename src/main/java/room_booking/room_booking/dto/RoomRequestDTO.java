package room_booking.room_booking.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoomRequestDTO {

    @NotBlank
    @Size(max = 50)
    private String name;

    @Min(1)
    private int capacity;

    @NotNull
    private Boolean active;
}