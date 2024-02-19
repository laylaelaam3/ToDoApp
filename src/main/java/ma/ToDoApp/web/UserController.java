package ma.ToDoApp.web;

import lombok.AllArgsConstructor;
import ma.ToDoApp.dtos.UserRequestDto;
import ma.ToDoApp.dtos.UserResponseDto;
import ma.ToDoApp.exceptions.UserInputNotValidException;
import ma.ToDoApp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users" /*, produces = "application/json", consumes = "application/json"*/)
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userDto) {
        try{
            UserResponseDto userResponseDto = userService.createUser(userDto);
            return ResponseEntity.ok(userResponseDto);
        }catch (UserInputNotValidException e){
            return ResponseEntity.notFound().build();
        }
    }
}