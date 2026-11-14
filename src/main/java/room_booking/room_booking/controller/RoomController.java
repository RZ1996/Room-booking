package room_booking.room_booking.controller;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import room_booking.room_booking.dto.RoomDTO;
import room_booking.room_booking.dto.RoomRequestDTO;
import room_booking.room_booking.service.RoomService;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getRooms() {
        return ResponseEntity.ok(roomService.getRooms());
    }

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomRequestDTO roomRequestDTO,
                                              @AuthenticationPrincipal Jwt jwt) {
        if (!isAdmin(jwt)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        RoomDTO createdRoomDTO = roomService.createRoom(roomRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoomDTO);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long roomId,
                                              @Valid @RequestBody RoomRequestDTO roomRequestDTO,
                                              @AuthenticationPrincipal Jwt jwt) {
        if (!isAdmin(jwt)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(roomService.updateRoom(roomId, roomRequestDTO));
    }

    private boolean isAdmin(Jwt jwt) {
        return "ADMIN".equals(jwt.getClaimAsString("role"));
    }
}