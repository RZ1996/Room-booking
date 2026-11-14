package room_booking.room_booking.mapper;
import org.mapstruct.Mapper;
import room_booking.room_booking.dto.RoomDTO;
import room_booking.room_booking.entity.Room;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomDTO mapRoomToRoomDTO(Room room);
    List<RoomDTO> mapRoomsToRoomDTOs(List<Room> rooms);
}