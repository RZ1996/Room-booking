package room_booking.room_booking.controller;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import room_booking.room_booking.dto.BookingDTO;
import room_booking.room_booking.service.BookingService;

import java.util.List;

@RestController
@Data
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){

        this.bookingService = bookingService;
    }

    @GetMapping("/api/bookings")
    public ResponseEntity<List<BookingDTO>> getBookings(){
        List<BookingDTO> bookings = bookingService.getBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/api/bookings/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId){
        BookingDTO bookingDTO = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(bookingDTO);
    }

    @PostMapping("/api/bookings")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO){
        BookingDTO createdBookingDTO = bookingService.createBooking(bookingDTO);
        return ResponseEntity.ok(createdBookingDTO);
    }

    @PutMapping("/api/bookings/{bookingId}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long bookingId, @RequestBody BookingDTO bookingDTO){
        BookingDTO createdBookingDTO = bookingService.updateBooking(bookingId, bookingDTO);
        return ResponseEntity.ok(createdBookingDTO);
    }

    @DeleteMapping("/api/bookings/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId){
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
