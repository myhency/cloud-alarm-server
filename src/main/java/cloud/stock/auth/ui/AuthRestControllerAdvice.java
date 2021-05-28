package cloud.stock.auth.ui;

import cloud.stock.auth.domain.exceptions.LoginFailException;
import cloud.stock.common.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthRestControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity handleLoginFailException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>("LoginFailException"));
    }
}
