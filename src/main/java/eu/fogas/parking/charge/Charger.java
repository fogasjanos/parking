package eu.fogas.parking.charge;

import java.util.Optional;

public interface Charger {
    Optional<Integer> getCharge(int hours);
}
