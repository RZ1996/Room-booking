package room_booking.room_booking.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import room_booking.room_booking.dto.RoomDTO;
import room_booking.room_booking.dto.RoomRequestDTO;
import room_booking.room_booking.entity.Room;
import room_booking.room_booking.exceptions.RoomNotFoundException;
import room_booking.room_booking.mapper.RoomMapper;
import room_booking.room_booking.repository.RoomRepository;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Transactional(readOnly = true)
    public List<RoomDTO> getRooms() {
        return roomMapper.mapRoomsToRoomDTOs(roomRepository.findAll());
    }

    @Transactional
    public RoomDTO createRoom(RoomRequestDTO roomRequestDTO) {
        Room room = new Room();
        setRoomData(room, roomRequestDTO);
        roomRepository.save(room);
        return roomMapper.mapRoomToRoomDTO(room);
    }

    @Transactional
    public RoomDTO updateRoom(Long roomId, RoomRequestDTO roomRequestDTO) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
        setRoomData(room, roomRequestDTO);
        roomRepository.save(room);
        return roomMapper.mapRoomToRoomDTO(room);
    }

    private void setRoomData(Room room, RoomRequestDTO roomRequestDTO) {
        room.setName(roomRequestDTO.getName());
        room.setCapacity(roomRequestDTO.getCapacity());
        room.setActive(roomRequestDTO.getActive());
    }
}