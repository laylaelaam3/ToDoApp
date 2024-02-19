package ma.ToDoApp.services;

import lombok.AllArgsConstructor;
import ma.ToDoApp.dtos.TaskRequestDto;
import ma.ToDoApp.dtos.TaskResponseDto;
import ma.ToDoApp.enumurations.ExceptionsMessage;
import ma.ToDoApp.exceptions.TaskNotFoundException;
import ma.ToDoApp.exceptions.UserInputNotValidException;
import ma.ToDoApp.mappers.MappingProfile;
import ma.ToDoApp.repositories.TaskRepository;
import ma.ToDoApp.utils.InputValidatorUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(MappingProfile::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDto createTask(TaskRequestDto taskDto) {
        if (!InputValidatorUtil.isValidEmail(taskDto.getStatus()))
            throw new UserInputNotValidException("status is null");
        if (!InputValidatorUtil.isStringEmpty(taskDto.getTitle()))
            throw new UserInputNotValidException("title  is null");
        if (!InputValidatorUtil.isFueDateIsValid(taskDto.getDueDate()))
            throw new UserInputNotValidException("Date is not Valid");
        var task = MappingProfile.mapToEntity(taskDto);
        return MappingProfile.mapToDto(taskRepository.save(task));
    }

    @Override
    public TaskResponseDto getTaskById(Long id) throws TaskNotFoundException {
        var task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(ExceptionsMessage.TASK_NOT_FOUND.getMessage()));
        return MappingProfile.mapToDto(task);
    }

    @Override
    public TaskResponseDto updateTask(Long id, TaskRequestDto taskDto) throws TaskNotFoundException {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(
                        ExceptionsMessage.TASK_NOT_FOUND.getMessage()));
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setId(taskDto.getId());
        task.setStatus(taskDto.getStatus());
        task.setDueDate(task.getDueDate());
        return MappingProfile.mapToDto(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) throws TaskNotFoundException {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(ExceptionsMessage.TASK_NOT_FOUND.getMessage()));
        taskRepository.delete(task);
    }
}