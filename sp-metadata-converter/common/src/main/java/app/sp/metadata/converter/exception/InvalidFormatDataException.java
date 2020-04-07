package app.sp.metadata.converter.exception;

public class InvalidFormatDataException extends RuntimeException {

    private static final long serialVersionUID = 4219453701769057587L;

    public InvalidFormatDataException() {
    }

    public InvalidFormatDataException(final String message) {
        super(message);
    }

    public InvalidFormatDataException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
