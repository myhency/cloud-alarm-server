package cloud.stock.common;


import cloud.stock.auth.domain.exceptions.UserNotExistsException;
import cloud.stock.stockitem.domain.exceptions.AlreadyExistStockItemException;
import cloud.stock.stockitem.domain.exceptions.NotExistStockItemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleBadRequestException(MethodArgumentNotValidException e) {

        ErrorResponse response = ErrorResponse
                .create()
                .status(ErrorCode.BAD_REQUEST.getStatus())
                .message(ErrorCode.BAD_REQUEST.getMessage())
                .code(ErrorCode.BAD_REQUEST.getCode());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistStockItemException.class)
    protected ResponseEntity<ErrorResponse> handleAlreadyExistException(RuntimeException e) {

        ErrorResponse response = ErrorResponse
                .create()
                .status(ErrorCode.ALREADY_EXISTS.getStatus())
                .message(ErrorCode.ALREADY_EXISTS.getMessage())
                .code(ErrorCode.ALREADY_EXISTS.getCode());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NotExistStockItemException.class, UserNotExistsException.class})
    protected ResponseEntity<ErrorResponse> handleNotExistException(RuntimeException e) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

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

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse
                .create()
                .status(errorCode.getStatus())
                .message(e.toString());

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
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
