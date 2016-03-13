package dictionary.yandex.api.com;

public enum ErrorCode {
    ERR_OK(200, "Operation completed successfully"),
    ERR_KEY_INVALID(401, "Invalid API key"),
    ERR_KEY_BLOCKED(402, "This API key has been blocked"),
    ERR_DAILY_REQ_LIMIT_EXCEEDED(403, "Exceeded the daily limit on the number of requests"),
    ERR_TEXT_TOO_LONG(413, "The text size exceeds the maximum"),
    ERR_LANG_NOT_SUPPORTED(501, "The specified translation direction is not supported");

    private int value;
    private String description;

    private ErrorCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static ErrorCode fromValue(final int value) {
        for (ErrorCode l : values()) {
            if (l.value == value) {
                return l;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return  "Value: " + value + "\nDescription: " + description + "\n";
    }
}
