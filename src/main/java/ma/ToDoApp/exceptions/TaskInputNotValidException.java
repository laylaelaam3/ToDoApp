package ma.ToDoApp.exceptions;


public class TaskInputNotValidException extends RuntimeException{
    public TaskInputNotValidException(String message) {
        super(message);
    }
}
