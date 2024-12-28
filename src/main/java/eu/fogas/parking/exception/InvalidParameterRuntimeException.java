package eu.fogas.parking.exception;

public class InvalidParameterRuntimeException extends ParkingRuntimeException {

    public InvalidParameterRuntimeException(String message) {
        super(message);
    }

    public InvalidParameterRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
