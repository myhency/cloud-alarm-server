package cloud.stock.common;

import org.springframework.validation.Errors;

public class EmptyResultDataAccessException extends CustomException {
    private final ErrorCode errorCode;

    public EmptyResultDataAccessException(ErrorCode errorCode) {
        super(ErrorCode.EMPTY_RESULT);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
