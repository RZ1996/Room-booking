package room_booking.room_booking.service;
import org.springframework.stereotype.Service;
import room_booking.room_booking.dto.BookingDTO;
import room_booking.room_booking.entity.Booking;
import room_booking.room_booking.exceptions.BookingNotFoundException;
import room_booking.room_booking.mapper.BookingMapper;
import room_booking.room_booking.repository.BookingRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper){
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    public List<BookingDTO> getBookings(){

        List<BookingDTO> bookings = bookingMapper.mapBookingsToBookingDTOs(bookingRepository.findAll());
        return bookings;
    }

    public BookingDTO getBookingById(Long id){
        BookingDTO bookingDTO = bookingMapper.mapBookingToBookingDTO(bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found")));
        return bookingDTO;
    }

    public BookingDTO createBooking(BookingDTO bookingDTO){
        Booking booking = new Booking();
        booking.setStartOfReservation(LocalDateTime.now());
       return null;
    }

    public BookingDTO updateBooking(Long bookingId, BookingDTO bookingDTO){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        return null;
    }

    public void deleteBooking(Long id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        bookingRepository.deleteById(booking.getId());

    }


}
