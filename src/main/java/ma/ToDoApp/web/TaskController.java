package ma.ToDoApp.web;

import lombok.AllArgsConstructor;
import ma.ToDoApp.dtos.TaskRequestDto;
import ma.ToDoApp.dtos.TaskResponseDto;
import ma.ToDoApp.exceptions.TaskInputNotValidException;
import ma.ToDoApp.exceptions.TaskNotFoundException;
import ma.ToDoApp.exceptions.UserInputNotValidException;
import ma.ToDoApp.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @GetMapping(value = "/" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getTaskById(@PathVariable("id") Long id) throws TaskNotFoundException {
        try {
            TaskResponseDto taskResponseDto = taskService.getTaskById(id);
            return ResponseEntity.ok(taskResponseDto);
        }catch (TaskNotFoundException e){
            throw new TaskNotFoundException("Task not found !");
        }
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTask(@RequestBody TaskRequestDto taskDto) {
        try {
            TaskResponseDto taskResponseDto = taskService.createTask(taskDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDto);
        }catch (UserInputNotValidException e){
                return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateById(@PathVariable("id") Long id ,@RequestBody TaskRequestDto taskDto) {
        try {
            TaskResponseDto responseDto = taskService.updateTask(id,taskDto);
            return ResponseEntity.ok(responseDto);
        }catch (TaskNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) throws TaskNotFoundException {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok().build();
        }catch (TaskNotFoundException e){
            throw new TaskInputNotValidException("Task not found with id = "+ id);
        }
    }


}