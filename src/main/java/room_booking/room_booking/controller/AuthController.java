package room_booking.room_booking.controller;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import room_booking.room_booking.dto.UserDTO;
import room_booking.room_booking.service.UserService;

@RestController
@Data
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    public UserDTO registerUser(@RequestBody UserDTO userDTO){

        return null;
    }
}
