package eu.fogas.parking.command;

import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import eu.fogas.parking.lot.ParkingLot;
import eu.fogas.parking.lot.model.Ticket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class Status extends AbstractCommand {
    private static final String HEADER = "Slot No. Registration No.";
    private static final String COL1_PATTERN = "%-9d";
    private static final String NEW_LINE = System.lineSeparator();

    private final String name = "status";
    private final int parametersCount = 0;

    @Override
    public void process(ParkingLot parkingLot) throws InvalidParameterRuntimeException {
        commandLog.info(getStatusAsString(parkingLot.status()));
    }

    private String getStatusAsString(Set<Ticket> status) {
        StringBuilder sb = new StringBuilder(HEADER);
        status.forEach(ticket ->
                sb.append(NEW_LINE)
                        .append(String.format(COL1_PATTERN, ticket.slotNumber()))
                        .append(ticket.registrationNumber()));
        return sb.toString();
    }
}
