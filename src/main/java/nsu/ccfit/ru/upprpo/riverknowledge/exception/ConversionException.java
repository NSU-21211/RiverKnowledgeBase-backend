package nsu.ccfit.ru.upprpo.riverknowledge.exception;

public class ConversionException extends RuntimeException {
    public ConversionException(Exception exception) {
        super(exception);
    }
}
