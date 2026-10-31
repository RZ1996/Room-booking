package room_booking.room_booking.controller;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import room_booking.room_booking.dto.LoginDTO;
import room_booking.room_booking.dto.RegisterDTO;
import room_booking.room_booking.dto.TokenDTO;
import room_booking.room_booking.dto.UserDTO;
import room_booking.room_booking.service.UserService;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        UserDTO userDTO = userService.registerUser(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<TokenDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.loginUser(loginDTO));
    }
}
