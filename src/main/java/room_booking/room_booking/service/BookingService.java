package room_booking.room_booking.service;
import org.springframework.stereotype.Service;
import room_booking.room_booking.dto.BookingDTO;
import room_booking.room_booking.dto.BookingRequestDTO;
import room_booking.room_booking.entity.Booking;
import room_booking.room_booking.exceptions.BookingNotFoundException;
import room_booking.room_booking.exceptions.UserNotFoundException;
import room_booking.room_booking.mapper.BookingMapper;
import room_booking.room_booking.repository.BookingRepository;
import room_booking.room_booking.repository.UserRepository;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;


    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, UserRepository userRepository){
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.userRepository = userRepository;

    }

    public List<BookingDTO> getBookings(){

        return bookingMapper.mapBookingsToBookingDTOs(bookingRepository.findAll());
    }

    public BookingDTO getBookingById(Long id){
        return bookingMapper.mapBookingToBookingDTO(bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found")));
    }

    public BookingDTO createBooking(BookingRequestDTO bookingRequestDTO, Long userId){
        Booking booking = new Booking();
        booking.setUser(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found")));
        booking.setStartOfReservation(bookingRequestDTO.getStartOfReservation());
        booking.setEndOfReservation(bookingRequestDTO.getEndOfReservation());
        booking.setNote(bookingRequestDTO.getNote());
        booking.setColor(bookingRequestDTO.getColor());
        booking.setState(bookingRequestDTO.getState());
        bookingRepository.save(booking);
       return bookingMapper.mapBookingToBookingDTO(booking);
    }

    public BookingDTO updateBooking(Long bookingId, BookingRequestDTO bookingRequestDTO, Long userId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
       validateUser(booking.getUser().getId(), userId);
        booking.setStartOfReservation(bookingRequestDTO.getStartOfReservation());
        booking.setEndOfReservation(bookingRequestDTO.getEndOfReservation());
        booking.setNote(bookingRequestDTO.getNote());
        booking.setColor(bookingRequestDTO.getColor());
        booking.setState(bookingRequestDTO.getState());
        return bookingMapper.mapBookingToBookingDTO(booking);
    }

    public void deleteBooking(Long id, Long userId){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        validateUser(booking.getUser().getId(), userId);
        bookingRepository.deleteById(booking.getId());

    }

    private void validateUser(Long booking, Long ownerId){
        if(!booking.equals(ownerId))
            throw new BookingNotFoundException("Booking not found");

    }



}
