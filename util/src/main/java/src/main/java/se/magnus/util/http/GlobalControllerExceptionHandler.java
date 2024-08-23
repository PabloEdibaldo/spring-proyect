package src.main.java.se.magnus.util.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import src.main.java.se.magnus.api.exceptions.InvalidInputException;
import src.main.java.se.magnus.api.exceptions.NotFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;


@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private static  final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody HttpErrorInfo handleNotFoundExceptions(
            ServerHttpRequest request, NotFoundException ex){
        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody HttpErrorInfo handleInvalidInputException(
            ServerHttpRequest request, InvalidInputException ex){
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY,request,ex);
    }

    private HttpErrorInfo createHttpErrorInfo(
            HttpStatus httpStatus,
            ServerHttpRequest request,
            NotFoundException ex) {

        final String path = request.getPath().pathWithinApplication().value();
        final String message = ex.getMessage();

        LOG.debug("Returning Http status:{}, for path:{}, message:{}", httpStatus,path,message);
        return new HttpErrorInfo(httpStatus,path,message);
    }

}
