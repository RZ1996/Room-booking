package room_booking.room_booking.service;
import lombok.Data;
import org.springframework.stereotype.Service;
import room_booking.room_booking.dto.UserDTO;
import room_booking.room_booking.entity.User;
import room_booking.room_booking.mapper.UserMapper;
import room_booking.room_booking.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){

        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UserDTO registerUser(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurName(userDTO.getSurName());
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
        return userMapper.mapUserToUserDTO(user);
    }



}
