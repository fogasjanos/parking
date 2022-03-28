package eu.fogas.parking.exception;

public class ConfigurationRuntimeException extends ParkingRuntimeException {
    static final long serialVersionUID = 1L;

    public ConfigurationRuntimeException(String message) {
        super(message);
    }

    public ConfigurationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
