package eu.fogas.parking.lot;

import eu.fogas.parking.charge.Charger;
import eu.fogas.parking.lot.model.TicketModel;

import java.util.Optional;
import java.util.Set;

public interface ParkingLot {

    void create(int numberOfSlots);

    Optional<Integer> park(String carNumber);

    Optional<Integer> leave(String carNumber);

    Set<TicketModel> status();

    int getFreeSlotsCount();

    Charger getCharger();
}
