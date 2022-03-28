package eu.fogas.parking.lot;

import eu.fogas.parking.charge.ChargeCalculator;
import eu.fogas.parking.charge.Charger;
import eu.fogas.parking.config.ChargeConfig;
import eu.fogas.parking.config.ConfigProvider;
import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import eu.fogas.parking.exception.InvalidParkingStateRuntimeException;
import eu.fogas.parking.lot.model.TicketModel;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class InMemoryParkingLot implements ParkingLot {
    private final SortedSet<Integer> freeSlots;
    private final Map<String, TicketModel> tickets;
    private final Verify verify;
    private final Charger charger;

    public InMemoryParkingLot(ConfigProvider configProvider) {
        freeSlots = new TreeSet<>();
        tickets = new HashMap<>();
        verify = new Verify();
        var cc = new ChargeConfig(configProvider);
        charger = ChargeCalculator.builder()
                .baseHours(cc.getBaseHours())
                .basePrice(cc.getBasePrice())
                .extraPrice(cc.getExtraPrice())
                .build();
    }

    @Override
    public void create(int numberOfSlots) {
        verify.numberOfSlots(numberOfSlots);
        reset();
        IntStream.rangeClosed(1, numberOfSlots)
                .boxed()
                .forEach(freeSlots::add);
    }

    @Override
    public Optional<Integer> park(String carNumber) {
        verify.parkingLotCreated()
                .carNumber(carNumber)
                .carNumberNotReused(carNumber);

        if (freeSlots.isEmpty()) {
            log.trace("There is no free slot.");
            return Optional.empty();
        }
        var slot = freeSlots.first();
        freeSlots.remove(slot);
        tickets.put(carNumber, new TicketModel(slot, carNumber));
        return Optional.of(slot);
    }

    @Override
    public Optional<Integer> leave(String carNumber) {
        verify.parkingLotCreated()
                .carNumber(carNumber);

        var ticket = tickets.remove(carNumber);
        if (ticket == null) {
            return Optional.empty();
        }
        int slotNumber = ticket.getSlotNumber();
        freeSlots.add(slotNumber);
        return Optional.of(slotNumber);
    }

    @Override
    public Set<TicketModel> status() {
        return Set.copyOf(tickets.values())
                .stream()
                .sorted(Comparator.comparingInt(TicketModel::getSlotNumber))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public int getFreeSlotsCount() {
        return freeSlots.size();
    }

    @Override
    public Charger getCharger() {
        return charger;
    }

    void reset() {
        freeSlots.clear();
        tickets.clear();
    }

    private class Verify {

        private Verify parkingLotCreated() {
            if (tickets.isEmpty() && freeSlots.isEmpty()) {
                throw new InvalidParkingStateRuntimeException("Parking lot not created!");
            }
            return this;
        }

        private Verify carNumber(String carNumber) {
            if (carNumber == null) {
                throw new InvalidParameterRuntimeException("Invalid carNumber! " + carNumber);
            }
            return this;
        }

        private Verify carNumberNotReused(String carNumber) {
            if (tickets.containsKey(carNumber)) {
                throw new InvalidParameterRuntimeException("Car with this number has already been parked! " + carNumber);
            }
            return this;
        }

        private Verify numberOfSlots(int numberOfSlots) {
            if (numberOfSlots < 1) {
                throw new InvalidParameterRuntimeException("Cannot create slots lower than one.");
            }
            return this;
        }
    }
}
