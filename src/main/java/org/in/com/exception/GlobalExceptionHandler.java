package org.in.com.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SuppressWarnings("serial")
@ControllerAdvice
public class GlobalExceptionHandler extends Exception {


	@ExceptionHandler(InvalidException.class)
    public ResponseEntity<String> handleCustomException(InvalidException e) {
      
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  
}