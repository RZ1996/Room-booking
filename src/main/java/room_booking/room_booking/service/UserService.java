package room_booking.room_booking.service;
import org.springframework.stereotype.Service;
import room_booking.room_booking.dto.LoginDTO;
import room_booking.room_booking.dto.UserDTO;
import room_booking.room_booking.entity.Booking;
import room_booking.room_booking.entity.User;
import room_booking.room_booking.exceptions.BookingNotFoundException;
import room_booking.room_booking.exceptions.UserAlreadyExistException;
import room_booking.room_booking.exceptions.UserNotFoundException;
import room_booking.room_booking.mapper.UserMapper;
import room_booking.room_booking.repository.UserRepository;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){

        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UserDTO registerUser(UserDTO userDTO){
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new UserAlreadyExistException("User not found");
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurName(userDTO.getSurName());
        user.setEmail(userDTO.getEmail());
        user.setCreatedAt(LocalDate.now());
        userRepository.save(user);
        return userMapper.mapUserToUserDTO(user);
    }

    public UserDTO loginUser(LoginDTO loginDTO){

        if(userRepository.findByEmail(loginDTO.getEmail()).isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return null;
    }

    public void validateUser(Long booking, Long userId){
      if(!booking.equals(userId))
          throw new BookingNotFoundException("Booking not found");

    }



}
