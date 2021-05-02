package cloud.stock.common;

public enum ErrorCode {

    INVALID_PARAMETER(400, "INVALID-REQUEST-DATA", "Invalid Request Data");

    private final String code;
    private final String message;
    private final int status;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
