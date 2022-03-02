package cloud.stock.alarm.ui;

import cloud.stock.alarm.domain.exceptions.AlreadyExistAlarmException;
import cloud.stock.alarm.domain.exceptions.InvalidAlarmModificationDataException;
import cloud.stock.alarm.domain.exceptions.NotExistAlarmException;
import cloud.stock.common.ResponseDto;
import cloud.stock.alarm.domain.exceptions.NotExistStockItemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AlarmRestControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistAlarmException.class)
    public ResponseEntity handleAlreadyExistAlarmException() {
        return ResponseEntity.badRequest().body(new ResponseDto<>("AlreadyExistAlarmException"));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotExistStockItemException.class)
    public ResponseEntity handleNotExistStockItemException() {
        return ResponseEntity.badRequest().body(new ResponseDto<>("NotExistStockItemException"));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAlarmModificationDataException.class)
    public ResponseEntity handleInvalidAlarmModificationDataException() {
        return ResponseEntity.badRequest().body(new ResponseDto<>("InvalidAlarmModificationDataException"));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotExistAlarmException.class)
    public ResponseEntity handleNotExistAlarmException() {
        return ResponseEntity.badRequest().body(new ResponseDto<>("NotExistAlarmException"));
    }
}
