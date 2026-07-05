package avisek.example.urlservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShortCodeAlreadyExistException.class)
    public ResponseEntity<ApiError> shortCodeAlreadyExistException(ShortCodeAlreadyExistException exception){
       ApiError apiError = new ApiError(exception.getMessage(), HttpStatus.BAD_REQUEST);
       return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
    }
}
