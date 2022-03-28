package eu.fogas.parking.exception;

public class ParkingRuntimeException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public ParkingRuntimeException(String message) {
        super(message);
    }

    public ParkingRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
