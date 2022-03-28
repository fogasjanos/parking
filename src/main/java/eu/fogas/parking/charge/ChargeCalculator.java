package eu.fogas.parking.charge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Builder
public class ChargeCalculator implements Charger {
    private final int baseHours;
    private final int basePrice;
    private final int extraPrice;

    @Override
    public Optional<Integer> getCharge(int hours) {
        if (hours < 1) {
            log.warn("Invalid hours! {}", hours);
            return Optional.empty();
        }
        if (hours <= baseHours) {
            log.debug("Returning {} as price for {} hours.", basePrice, hours);
            return Optional.of(basePrice);
        }
        var calculatedValue = basePrice + ((hours - baseHours) * extraPrice);
        log.debug("Returning {} as price for {} hours.", calculatedValue, hours);
        return Optional.of(calculatedValue);
    }
}
