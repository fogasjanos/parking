package eu.fogas.parking.exception;

public class InvalidParameterRuntimeException extends ParkingRuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidParameterRuntimeException(String message) {
        super(message);
    }

    public InvalidParameterRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
