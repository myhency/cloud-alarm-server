package cloud.stock.common;

import org.springframework.validation.Errors;

public class MethodArgumentNotValidException extends CustomException {

    private final Errors errors;

    public MethodArgumentNotValidException(Errors errors) {
        super(ErrorCode.BAD_REQUEST);
        this.errors = errors;
    }

    public Errors getErrors() {

        return errors;
    }

}
