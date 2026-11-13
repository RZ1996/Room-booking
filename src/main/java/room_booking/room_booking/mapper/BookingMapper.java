package room_booking.room_booking.mapper;
import org.mapstruct.Mapper;
import room_booking.room_booking.dto.BookingDTO;
import room_booking.room_booking.entity.Booking;


@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingDTO mapBookingToBookingDTO(Booking booking);


}
