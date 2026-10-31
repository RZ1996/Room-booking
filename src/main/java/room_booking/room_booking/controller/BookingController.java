package room_booking.room_booking.controller;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import room_booking.room_booking.dto.BookingDTO;
import room_booking.room_booking.dto.BookingRequestDTO;
import room_booking.room_booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getBookings() {
        return ResponseEntity.ok(bookingService.getBookings());
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO,
                                                    @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        BookingDTO createdBookingDTO = bookingService.createBooking(bookingRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBookingDTO);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long bookingId,
                                                    @Valid @RequestBody BookingRequestDTO bookingRequestDTO,
                                                    @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        BookingDTO updatedBookingDTO = bookingService.updateBooking(bookingId, bookingRequestDTO, userId);
        return ResponseEntity.ok(updatedBookingDTO);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId,
                                              @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        bookingService.deleteBooking(bookingId, userId);
        return ResponseEntity.noContent().build();
    }
}