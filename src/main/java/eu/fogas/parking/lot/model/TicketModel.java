package eu.fogas.parking.lot.model;

import lombok.Data;

@Data
public class TicketModel {
    private final int slotNumber;
    private final String registrationNumber;
}
