package room_booking.room_booking.service;
import lombok.Data;
import org.springframework.stereotype.Service;
import room_booking.room_booking.mapper.UserMapper;
import room_booking.room_booking.repository.UserRepository;

@Service
@Data
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){

        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

}
