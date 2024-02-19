package ma.ToDoApp.services;

import lombok.AllArgsConstructor;
import ma.ToDoApp.dtos.UserRequestDto;
import ma.ToDoApp.dtos.UserResponseDto;
import ma.ToDoApp.enumurations.ExceptionsMessage;
import ma.ToDoApp.exceptions.UserInputNotValidException;
import ma.ToDoApp.exceptions.UserNotFoundException;
import ma.ToDoApp.mappers.MappingProfile;
import ma.ToDoApp.repositories.UserRepository;
import ma.ToDoApp.utils.InputValidatorUtil;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(MappingProfile::mapToUserDto).toList();
    }
    public UserResponseDto createUser(UserRequestDto userDto) {
        if(!InputValidatorUtil.isValidEmail(userDto.getEmail()))
            throw new UserInputNotValidException("Email already exists");
        if(!InputValidatorUtil.isStringEmpty(userDto.getFirstName()))
            throw new UserInputNotValidException("firstName  is null");
        if(!InputValidatorUtil.isStringEmpty(userDto.getLastName()))
            throw new UserInputNotValidException("lastName  is null");
        var user = MappingProfile.mapToUserEntity(userDto);
        return MappingProfile.mapToUserDto(userRepository.save(user));
    }
    public Object getUserById(Long id) throws UserNotFoundException{
        var user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(ExceptionsMessage.USER_NOT_FOUND.getMessage()));
        return new Object() {
            public Long id = user.getId();
            public String fullName = user.getLastName().toUpperCase() + ", " + user.getFirstName();
            public String email = user.getEmail();
            public List<Object> tasks = Collections.singletonList(user.getTasks().stream().map(task -> new Object() {
                public Long id = task.getId();
                public String title = task.getTitle();
                public String description = task.getDescription();
                public String status = task.getStatus();
                public String dueDate = task.getDueDate().toString();
                public String createdAt = task.getCreatedAt().toString();
                public String updatedAt = task.getUpdatedAt().toString();
            }).toList());
        };
    }

    public UserResponseDto addUser(UserRequestDto userDto) {
        if(InputValidatorUtil.isValidEmail(userDto.getEmail()))
            throw new UserInputNotValidException("Email already exists");
        if(!InputValidatorUtil.isStringEmpty(userDto.getFirstName()))
            throw new UserInputNotValidException("firstName  is null");
        if(!InputValidatorUtil.isStringEmpty(userDto.getLastName()))
            throw new UserInputNotValidException("lastName  is null");
        var user = MappingProfile.mapToUserEntity(userDto);
        return MappingProfile.mapToUserDto(userRepository.save(user));
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userDto) throws UserNotFoundException {
        var user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(ExceptionsMessage.USER_INPUT_NOT_VALID.getMessage()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        return MappingProfile.mapToUserDto(userRepository.save(user));
    }
}