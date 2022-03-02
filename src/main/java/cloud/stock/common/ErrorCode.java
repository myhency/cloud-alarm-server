package cloud.stock.common;

public enum ErrorCode {

    ALREADY_EXISTS(409, "ALREADY_EXISTS", "Already Exists"),
    BAD_REQUEST(400, "BAD-REQUEST", "Bad Request"),
    INVALID_PARAMETER(400, "INVALID-REQUEST-DATA", "Invalid Request Data"),
    EMPTY_RESULT(204, "EMPTY-RESULT", "No data returns");

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
