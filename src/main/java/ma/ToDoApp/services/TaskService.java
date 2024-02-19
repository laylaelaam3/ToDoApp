package ma.ToDoApp.services;

import ma.ToDoApp.dtos.TaskRequestDto;
import ma.ToDoApp.dtos.TaskResponseDto;
import ma.ToDoApp.exceptions.TaskNotFoundException;
import java.util.List;
public interface TaskService {
    List<TaskResponseDto> getAllTasks();
    TaskResponseDto createTask(TaskRequestDto taskDto);
    TaskResponseDto getTaskById(Long id) throws TaskNotFoundException;
    TaskResponseDto updateTask(Long id, TaskRequestDto taskDto) throws TaskNotFoundException;
    void deleteTask(Long id) throws TaskNotFoundException;
}
