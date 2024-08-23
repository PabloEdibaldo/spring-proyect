package src.main.java.se.magnus.api.exceptions;

public class InvalidInputException extends NotFoundException{
    public InvalidInputException(){}

    public InvalidInputException(String message){
        super(message);
    }
    public InvalidInputException(String message, Throwable cause){
        super(message,cause);
    }
    public InvalidInputException(Throwable cause){
        super(cause);
    }
}
