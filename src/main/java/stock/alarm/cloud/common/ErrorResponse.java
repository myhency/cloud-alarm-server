package stock.alarm.cloud.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private String code;
    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrorList;

    public ErrorResponse() {
    }

    static public ErrorResponse create() {
        return new ErrorResponse();
    }

    public ErrorResponse code(String code) {
        this.code = code;
        return this;
    }

    public ErrorResponse status(int status) {
        this.status = status;
        return this;
    }

    public ErrorResponse message(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse errors(Errors errors) {
        setCustomFieldErrors(errors.getFieldErrors());
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public List<CustomFieldError> getCustomFieldErrorList() {
        return customFieldErrorList;
    }

    public void setCustomFieldErrors(List<FieldError> fieldErrors) {
        customFieldErrorList = new ArrayList<>();
        fieldErrors.forEach(error -> {
            customFieldErrorList.add(new CustomFieldError(
                    error.getCodes()[0],
                    error.getRejectedValue(),
                    error.getDefaultMessage()
            ));
        });
    }

    public static class CustomFieldError {
        private String field;
        private Object value;
        private String reason;

        public CustomFieldError(String field, Object value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }
    }
}
