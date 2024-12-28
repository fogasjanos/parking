package eu.fogas.parking.exception;

public class ConfigurationRuntimeException extends ParkingRuntimeException {

    public ConfigurationRuntimeException(String message) {
        super(message);
    }

    public ConfigurationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
