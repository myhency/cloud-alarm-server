package cloud.stock.common;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


//@ControllerAdvice
public class ControllerExceptionHandler {

//    @ExceptionHandler(InvalidParameterException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidParameterException(InvalidParameterException e) {
        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse
                .create()
                .status(errorCode.getStatus())
                .message(e.toString())
                .errors(e.getErrors());

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }

//    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse
                .create()
                .status(errorCode.getStatus())
                .message(e.toString());

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }

//    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse
                .create()
                .status(errorCode.getStatus())
                .message(e.toString())
                .errors(e.getErrors());

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }

//    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse
                .create()
                .status(errorCode.getStatus())
                .message(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }

}
