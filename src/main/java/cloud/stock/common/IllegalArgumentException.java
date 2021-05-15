package cloud.stock.common;

import org.springframework.validation.Errors;

public class IllegalArgumentException extends CustomException {

    private final Errors errors;

    public IllegalArgumentException(Errors errors) {
        super(ErrorCode.INVALID_PARAMETER);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
