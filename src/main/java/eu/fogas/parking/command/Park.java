package eu.fogas.parking.command;

import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import eu.fogas.parking.lot.ParkingLot;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class Park extends AbstractCommand {
    private static final String PARAMETER_CAR_NUMBER = "carNumber";

    private final String name = "park";
    private final int parametersCount = 1;

    @Override
    public void process(ParkingLot parkingLot, String param) throws InvalidParameterRuntimeException {
        String carNumber = parseString(PARAMETER_CAR_NUMBER, param);

        var slotNum = parkingLot.park(carNumber);

        if (slotNum.isEmpty()) {
            commandLog.info("Sorry, parking lot is full");
        } else {
            commandLog.info("Allocated slot number: {}", slotNum.get());
        }
    }
}
