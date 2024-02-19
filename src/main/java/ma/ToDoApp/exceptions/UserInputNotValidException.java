package ma.ToDoApp.exceptions;


public class UserInputNotValidException extends RuntimeException{
    public UserInputNotValidException(String message) {
        super(message);
    }
}
