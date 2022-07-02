package edunhnil.project.forum.api.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import edunhnil.project.forum.api.exception.InvalidDateFormat;
import edunhnil.project.forum.api.exception.InvalidRequestException;
import edunhnil.project.forum.api.exception.ResourceNotFoundException;
import edunhnil.project.forum.api.log.LoggerFactory;
import edunhnil.project.forum.api.log.LoggerType;
import edunhnil.project.forum.api.log.AppLogger;;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);

    @ExceptionHandler(InvalidDateFormat.class)
    public ResponseEntity<String> handleInvalidDateFormatException(InvalidDateFormat e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), null, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), null, HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), null, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<>(" Param(s) in your request is in wrong type or empty value!", null,
                HttpStatus.BAD_REQUEST.value());
    }

}
