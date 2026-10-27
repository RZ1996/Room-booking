package room_booking.room_booking.service;
import lombok.Data;
import org.springframework.stereotype.Service;
import room_booking.room_booking.mapper.BookingMapper;
import room_booking.room_booking.repository.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper){
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }


}
