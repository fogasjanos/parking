package eu.fogas.parking.command;

import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import eu.fogas.parking.lot.ParkingLot;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class Create extends AbstractCommand {
    private static final String PARAMETER_SIZE = "size";

    private final String name = "create";
    private final int parametersCount = 1;

    @Override
    public void process(ParkingLot parkingLot, String param) throws InvalidParameterRuntimeException {
        int size = parseInt(PARAMETER_SIZE, param);

        parkingLot.create(size);
        commandLog.info("Created parking lot with {} slots", size);
    }

}
