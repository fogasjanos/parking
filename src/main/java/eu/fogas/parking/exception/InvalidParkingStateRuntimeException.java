package eu.fogas.parking.exception;

public class InvalidParkingStateRuntimeException extends ParkingRuntimeException {

    public InvalidParkingStateRuntimeException(String message) {
        super(message);
    }

    public InvalidParkingStateRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
