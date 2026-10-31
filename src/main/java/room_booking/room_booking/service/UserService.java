package room_booking.room_booking.service;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import room_booking.room_booking.dto.LoginDTO;
import room_booking.room_booking.dto.RegisterDTO;
import room_booking.room_booking.dto.TokenDTO;
import room_booking.room_booking.dto.UserDTO;
import room_booking.room_booking.entity.Role;
import room_booking.room_booking.entity.User;
import room_booking.room_booking.exceptions.UserAlreadyExistException;
import room_booking.room_booking.mapper.UserMapper;
import room_booking.room_booking.repository.UserRepository;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, UserMapper userMapper,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserDTO registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("User with this email already exists");
        }
        User user = new User();
        user.setName(registerDTO.getName());
        user.setSurName(registerDTO.getSurName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDate.now());
        userRepository.save(user);
        return userMapper.mapUserToUserDTO(user);
    }

    public TokenDTO loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Wrong email or password"));
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Wrong email or password");
        }
        return new TokenDTO(jwtService.generateToken(user));
    }
}