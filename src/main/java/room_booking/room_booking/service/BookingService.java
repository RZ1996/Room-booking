package room_booking.room_booking.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import room_booking.room_booking.dto.BookingDTO;
import room_booking.room_booking.dto.BookingRequestDTO;
import room_booking.room_booking.entity.Booking;
import room_booking.room_booking.entity.Room;
import room_booking.room_booking.entity.State;
import room_booking.room_booking.exceptions.*;
import room_booking.room_booking.mapper.BookingMapper;
import room_booking.room_booking.repository.BookingRepository;
import room_booking.room_booking.repository.RoomRepository;
import room_booking.room_booking.repository.UserRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper,
                          UserRepository userRepository, RoomRepository roomRepository){
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional(readOnly = true)
    public Page<BookingDTO> getBookings(Long roomId, Pageable pageable){
        Page<Booking> bookings;
        if (roomId == null) {
            bookings = bookingRepository.findAll(pageable);
        } else {
            bookings = bookingRepository.findByRoomId(roomId, pageable);
        }
        return bookings.map(bookingMapper::mapBookingToBookingDTO);
    }

    @Transactional(readOnly = true)
    public Page<BookingDTO> getBookingsMy(Long userId, Pageable pageable){
        return bookingRepository.findAllBookingByUserId(userId, pageable)
                .map(bookingMapper::mapBookingToBookingDTO);
    }

    @Transactional(readOnly = true)
    public BookingDTO getBookingById(Long bookingId){
        return bookingMapper.mapBookingToBookingDTO(bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found")));
    }

    @Transactional
    public BookingDTO createBooking(BookingRequestDTO bookingRequestDTO, Long userId){
        Long countedBookings = bookingRepository.countByUserIdAndStateAndStartOfReservationGreaterThan(
                userId, State.OPENED, LocalDateTime.now());
        if(countedBookings >= 5)
            throw new BookingMaximumOpenedAmountException("Maximum 5 booking can be created");
        Booking booking = new Booking();
        booking.setUser(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found")));
        setTimeForBooking(booking, bookingRequestDTO);
        Room room = findActiveRoom(bookingRequestDTO.getRoomId());
        validateRoomIsFree(room.getId(), bookingRequestDTO.getStartOfReservation(), bookingRequestDTO.getEndOfReservation());
        booking.setNote(bookingRequestDTO.getNote());
        booking.setRoom(room);
        booking.setState(State.OPENED);
        bookingRepository.save(booking);
        return bookingMapper.mapBookingToBookingDTO(booking);
    }
    @Transactional
    public BookingDTO updateBooking(Long bookingId, BookingRequestDTO bookingRequestDTO, Long userId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        validateUser(booking.getUser().getId(), userId);
        if(booking.getState().equals(State.CANCELLED))
            throw new BookingAlreadyCancelled("Booking is already cancelled");
        if(!booking.getStartOfReservation().isAfter(LocalDateTime.now()))
            throw new BookingInProgressException("Cannot update a booking that already started");
        setTimeForBooking(booking, bookingRequestDTO);
        Room room = findActiveRoom(bookingRequestDTO.getRoomId());
        validateRoomIsFreeForUpdate(bookingId, room.getId(),
                bookingRequestDTO.getStartOfReservation(), bookingRequestDTO.getEndOfReservation());
        booking.setNote(bookingRequestDTO.getNote());
        booking.setRoom(room);
        bookingRepository.save(booking);
        return bookingMapper.mapBookingToBookingDTO(booking);
    }

    @Transactional
    public void deleteBooking(Long id, Long userId, boolean isAdmin){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        if(!isAdmin){
            validateUser(booking.getUser().getId(), userId);
        }
        if(booking.getEndOfReservation().isBefore(LocalDateTime.now()))
            throw new BookingIsExistingInThePast("Cannot cancelled old booking");
        if(booking.getState().equals(State.CANCELLED))
            throw new BookingAlreadyCancelled("Booking is already cancelled");
        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(booking.getStartOfReservation()) && now.isBefore(booking.getEndOfReservation())) {
            throw new BookingInProgressException("Cannot cancel a booking that is in progress");
        }
        booking.setState(State.CANCELLED);
        bookingRepository.save(booking);
    }

    private void validateUser(Long ownerId, Long userId){
        if(!userId.equals(ownerId))
            throw new BookingNotFoundException("Booking not found");
    }

    private Room findActiveRoom(Long roomId){
        Room room = roomRepository.findByIdForUpdate(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
        if(!room.isActive())
            throw new RoomNotActiveException("Room is not active");
        return room;
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

    private void validateRoomIsFree(Long roomId, LocalDateTime start, LocalDateTime end) {
        if (bookingRepository.existsByRoomIdAndStateAndStartOfReservationLessThanAndEndOfReservationGreaterThan(
                roomId, State.OPENED, end, start)) {
            throw new BookingAlreadyReserved("Room is already booked at this time");
        }
    }

    private void validateRoomIsFreeForUpdate(Long bookingId, Long roomId, LocalDateTime start, LocalDateTime end) {
        if (bookingRepository.existsByRoomIdAndStateAndStartOfReservationLessThanAndEndOfReservationGreaterThanAndIdNot(
                roomId, State.OPENED, end, start, bookingId)) {
            throw new BookingAlreadyReserved("Room is already booked at this time");
        }
    }


}