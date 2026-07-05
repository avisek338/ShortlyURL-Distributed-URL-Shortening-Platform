package avisek.example.urlservice.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Getter
public class ApiError {
    private String message;
    private String timeStamp;
    private HttpStatus statusCode;

    public ApiError(String message, HttpStatus statusCode) {
        this.message = message;
        this.timeStamp = LocalDate.now().toString();
        this.statusCode = statusCode;
    }
}
