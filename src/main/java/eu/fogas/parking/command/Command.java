package eu.fogas.parking.command;

import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import eu.fogas.parking.lot.ParkingLot;
import org.slf4j.Logger;

public interface Command {
    String getName();

    int getParametersCount();

    default void process(ParkingLot parkingLot) throws InvalidParameterRuntimeException {
        throw new InvalidParameterRuntimeException("Invalid parameter count! Expected: " + getParametersCount());
    }

    default void process(ParkingLot parkingLot, String param) throws InvalidParameterRuntimeException {
        throw new InvalidParameterRuntimeException("Invalid parameter count! Expected: " + getParametersCount());
    }

    default void process(ParkingLot parkingLot, String param1, String param2) throws InvalidParameterRuntimeException {
        throw new InvalidParameterRuntimeException("Invalid parameter count! Expected: " + getParametersCount());
    }

    default void process(ParkingLot parkingLot, String... params) throws InvalidParameterRuntimeException {
        throw new InvalidParameterRuntimeException("Invalid parameter count! Expected: " + getParametersCount());
    }

}
