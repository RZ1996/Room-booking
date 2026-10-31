package room_booking.room_booking.service;
import org.springframework.stereotype.Service;
import room_booking.room_booking.dto.BookingDTO;
import room_booking.room_booking.dto.BookingRequestDTO;
import room_booking.room_booking.entity.Booking;
import room_booking.room_booking.entity.Color;
import room_booking.room_booking.entity.State;
import room_booking.room_booking.exceptions.BookingAlreadyReserved;
import room_booking.room_booking.exceptions.BookingNotFoundException;
import room_booking.room_booking.exceptions.BookingWrongTimeException;
import room_booking.room_booking.exceptions.UserNotFoundException;
import room_booking.room_booking.mapper.BookingMapper;
import room_booking.room_booking.repository.BookingRepository;
import room_booking.room_booking.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
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

    public BookingDTO getBookingById(Long bookingId){
        return bookingMapper.mapBookingToBookingDTO(bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found")));
    }


    public BookingDTO createBooking(BookingRequestDTO bookingRequestDTO, Long userId){
        Booking booking = new Booking();
        booking.setUser(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found")));
        setTimeForBooking(booking, bookingRequestDTO);
        validateRoomIsFree(bookingRequestDTO.getColor(), bookingRequestDTO.getStartOfReservation(), bookingRequestDTO.getEndOfReservation());
        booking.setNote(bookingRequestDTO.getNote());
        booking.setColor(bookingRequestDTO.getColor());
        booking.setState(State.OPENED);
        bookingRepository.save(booking);
       return bookingMapper.mapBookingToBookingDTO(booking);
    }

    public BookingDTO updateBooking(Long bookingId, BookingRequestDTO bookingRequestDTO, Long userId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        validateUser(booking.getUser().getId(), userId);
        setTimeForBooking(booking, bookingRequestDTO);
        validateRoomIsFreeForUpdate(bookingId, bookingRequestDTO.getColor(),
                bookingRequestDTO.getStartOfReservation(), bookingRequestDTO.getEndOfReservation());
        booking.setNote(bookingRequestDTO.getNote());
        booking.setColor(bookingRequestDTO.getColor());
        bookingRepository.save(booking);
        return bookingMapper.mapBookingToBookingDTO(booking);
    }

    public void deleteBooking(Long id, Long userId){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        validateUser(booking.getUser().getId(), userId);
        bookingRepository.deleteById(booking.getId());

    }

    private void validateUser(Long ownerId, Long userId){
        if(!userId.equals(ownerId))
            throw new BookingNotFoundException("Booking not found");

    }

    private void setTimeForBooking(Booking booking, BookingRequestDTO bookingRequestDTO){
        if((!bookingRequestDTO.getStartOfReservation().isBefore(bookingRequestDTO.getEndOfReservation())))
            throw new BookingWrongTimeException("End must be after start");
        Duration length = Duration.between(bookingRequestDTO.getStartOfReservation(), bookingRequestDTO.getEndOfReservation());
        if (length.compareTo(Duration.ofHours(4)) > 0) {
            throw new BookingWrongTimeException("Booking can last max 4 hours");
        }
        if (bookingRequestDTO.getStartOfReservation().isBefore(LocalDateTime.now())) throw new BookingWrongTimeException("Start cannot be in the past");
        booking.setStartOfReservation(bookingRequestDTO.getStartOfReservation());
        booking.setEndOfReservation(bookingRequestDTO.getEndOfReservation());
    }

    private void validateRoomIsFree(Color color, LocalDateTime start, LocalDateTime end) {
        if (bookingRepository.existsByColorAndStateAndStartOfReservationLessThanAndEndOfReservationGreaterThan(
                color, State.OPENED, end, start)) {
            throw new BookingAlreadyReserved("Room is already booked at this time");
        }
    }

    private void validateRoomIsFreeForUpdate(Long bookingId, Color color, LocalDateTime start, LocalDateTime end) {
        if (bookingRepository.existsByColorAndStateAndStartOfReservationLessThanAndEndOfReservationGreaterThanAndIdNot(
                color, State.OPENED, end, start, bookingId)) {
            throw new BookingAlreadyReserved("Room is already booked at this time");
        }
    }



}
