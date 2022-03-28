package eu.fogas.parking.command;

import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import eu.fogas.parking.lot.ParkingLot;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class Leave extends AbstractCommand {
    private static final String PARAMETER_CAR_NUMBER = "carNumber";
    private static final String PARAMETER_HOURS = "hours";

    private final String name = "leave";
    private final int parametersCount = 2;

    @Override
    public void process(ParkingLot parkingLot, String param1, String param2) throws InvalidParameterRuntimeException {
        String carNumber = parseString(PARAMETER_CAR_NUMBER, param1);
        int hours = parseInt(PARAMETER_HOURS, param2);

        var slotNum = parkingLot.leave(carNumber);
        if (slotNum.isEmpty()) {
            commandLog.info("Registration Number {} not found", carNumber);
            return;
        }

        Optional<Integer> charge = parkingLot.getCharger().getCharge(hours);
        if (charge.isPresent()) {
            commandLog.info("Registration Number {} from Slot {} has left with Charge {}", carNumber, slotNum.get(), charge.get());
        } else {
            commandLog.info("Invalid hours! {}", hours);
        }
    }
}
