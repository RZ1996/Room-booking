package room_booking.room_booking.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import room_booking.room_booking.dto.LoginDTO;
import room_booking.room_booking.dto.UserDTO;
import room_booking.room_booking.entity.Role;
import room_booking.room_booking.entity.User;
import room_booking.room_booking.exceptions.UserAlreadyExistException;
import room_booking.room_booking.exceptions.UserNotFoundException;
import room_booking.room_booking.mapper.UserMapper;
import room_booking.room_booking.repository.UserRepository;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder){

        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return userMapper.mapUserToRegisterDTO(user);
    }

    public UserDTO loginUser(LoginDTO loginDTO){

        if(userRepository.findByEmail(loginDTO.getEmail()).isEmpty()){
            throw new UserNotFoundException("User not found");
        }
//        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
//            throw new BadCredentialsException("Wrong password");
//        }
        return null;
    }



}
