package room_booking.room_booking.mapper;
import org.mapstruct.Mapper;
import room_booking.room_booking.dto.RegisterDTO;
import room_booking.room_booking.dto.UserDTO;
import room_booking.room_booking.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO mapUserToUserDTO(User user);
    User mapUserDTOtoUser(UserDTO registerDTO);
    RegisterDTO mapUserToRegisterDTO(User user);
}
