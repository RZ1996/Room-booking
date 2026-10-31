package room_booking.room_booking.mapper;
import org.mapstruct.Mapper;
import room_booking.room_booking.dto.BookingDTO;
import room_booking.room_booking.entity.Booking;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingDTO mapBookingToBookingDTO(Booking booking);
    List<BookingDTO> mapBookingsToBookingDTOs(List<Booking> bookings);

}
