package room_booking.room_booking.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import room_booking.room_booking.dto.BookingDTO;
import room_booking.room_booking.entity.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.name", target = "roomName")
    BookingDTO mapBookingToBookingDTO(Booking booking);
}