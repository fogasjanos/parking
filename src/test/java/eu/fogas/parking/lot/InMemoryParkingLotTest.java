package eu.fogas.parking.lot;

import eu.fogas.parking.config.ConfigProvider;
import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import eu.fogas.parking.exception.InvalidParkingStateRuntimeException;
import eu.fogas.parking.lot.model.TicketModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InMemoryParkingLotTest {
    @InjectMocks
    private InMemoryParkingLot parkingLot;
    @Mock
    private ConfigProvider configProvider;

    @BeforeEach
    void setUp() {
        parkingLot.reset();
    }

    @Test
    void create_shouldCreateWithNumberOfLots() {
        int numberOfSlots = 3;

        parkingLot.create(numberOfSlots);

        assertEquals(numberOfSlots, parkingLot.getFreeSlotsCount());
    }

    @Test
    void create_shouldReCreateLots_whenLotsWereCreatedBefore() {
        int numberOfSlots = 3;

        parkingLot.create(numberOfSlots - 2);
        parkingLot.create(numberOfSlots);

        assertEquals(numberOfSlots, parkingLot.getFreeSlotsCount());
    }


    @Test
    void create_shouldAcceptOne() {
        int numberOfSlots = 1;

        parkingLot.create(numberOfSlots);

        assertEquals(numberOfSlots, parkingLot.getFreeSlotsCount());
    }

    @Test
    void create_shouldThrowInvalidParameterRuntimeException_whenNumberOfSlotsIsZero() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> parkingLot.create(0));

        assertEquals("Cannot create slots lower than one.", e.getMessage());
    }

    @Test
    void create_shouldThrowInvalidParameterRuntimeException_whenNumberOfSlotsIsNegative() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> parkingLot.create(-10));

        assertEquals("Cannot create slots lower than one.", e.getMessage());
    }

    @Test
    void park_shouldThrowInvalidParkingStateRuntimeException_whenParkingLotNotCreated() {
        var e = assertThrows(InvalidParkingStateRuntimeException.class,
                () -> parkingLot.park("ECTO-1"));

        assertEquals("Parking lot not created!", e.getMessage());
    }

    @Test
    void park_shouldThrowInvalidParameterRuntimeException_whenCarNumberIsNull() {
        parkingLot.create(1);

        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> parkingLot.park(null));

        assertEquals("Invalid carNumber! null", e.getMessage());
    }

    @Test
    void park_shouldThrowInvalidParameterRuntimeException_whenCarNumberIsReused() {
        parkingLot.create(1);
        String carNum = "OUTATIME";
        parkingLot.park(carNum);

        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> parkingLot.park(carNum));

        assertEquals("Car with this number has already been parked! " + carNum, e.getMessage());
    }

    @Test
    void park_shouldReturnFreeSlot_whenItsAvailable() {
        parkingLot.create(3);

        Optional<Integer> nextSlot = parkingLot.park("OUTATIME");

        assertTrue(nextSlot.isPresent());
        assertEquals(1, nextSlot.get());
    }

    @Test
    void park_shouldReturnEmptyOptional_whenNoSlotsAvailable() {
        parkingLot.create(1);
        parkingLot.park("ECTO-1");

        Optional<Integer> nextSlot = parkingLot.park("STARK 4");

        assertTrue(nextSlot.isEmpty());
    }

    @Test
    void park_shouldGiveSlotOrdered() {
        parkingLot.create(3);
        var s1 = parkingLot.park("ROBIN-1");
        var s2 = parkingLot.park("PARZIVAL");
        var leaveResult = parkingLot.leave("ROBIN-1");
        var s1b = parkingLot.park("SNK 80Q3");
        var s3 = parkingLot.park("KAZ 2Y5");

        assertTrue(s1.isPresent());
        assertEquals(1, s1.get());

        assertTrue(s2.isPresent());
        assertEquals(2, s2.get());

        assertTrue(leaveResult.isPresent());
        assertEquals(1, leaveResult.get());

        assertTrue(s1b.isPresent());
        assertEquals(1, s1b.get());

        assertTrue(s3.isPresent());
        assertEquals(3, s3.get());
    }

    @Test
    void leave_shouldThrowInvalidParkingStateRuntimeException_whenParkingLotNotCreated() {
        var e = assertThrows(InvalidParkingStateRuntimeException.class,
                () -> parkingLot.leave("KNIGHT"));

        assertEquals("Parking lot not created!", e.getMessage());
    }

    @Test
    void leave_shouldThrowInvalidParameterRuntimeException_whenCarNumberIsNull() {
        parkingLot.create(1);

        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> parkingLot.leave(null));

        assertEquals("Invalid carNumber! null", e.getMessage());
    }

    @Test
    void leave_shouldFreeSlot() {
        parkingLot.create(3);
        parkingLot.park("STAN L33"); // 1
        parkingLot.park("FLASHDRV"); // 2
        var beforeLeave = parkingLot.getFreeSlotsCount();
        var leaveResult = parkingLot.leave("STAN L33");
        var afterLeave = parkingLot.getFreeSlotsCount();
        var slot = parkingLot.park("NRVOUS");

        assertTrue(slot.isPresent());
        assertTrue(leaveResult.isPresent());
        assertEquals(1, leaveResult.get());
        assertEquals(1, slot.get());
        assertEquals(1, beforeLeave);
        assertEquals(2, afterLeave);
    }

    @Test
    void leave_shouldReturnFalse_whenCarNotFound() {
        parkingLot.create(3);
        parkingLot.park("KITT");
        var beforeLeave = parkingLot.getFreeSlotsCount();
        var result = parkingLot.leave("KARR");
        var afterLeave = parkingLot.getFreeSlotsCount();

        assertFalse(result.isPresent());
        assertEquals(afterLeave, beforeLeave);
    }

    @Test
    void park_shouldReturnEmptySet_whenParkingLotNotCreated() {
        var result = parkingLot.status();

        assertTrue(result.isEmpty());
    }

    @Test
    void status_shouldReturnOrderedSet() {
        parkingLot.create(3);
        String carNum1 = "ADDAMS-42";
        String carNum2 = "M4RV1N";
        String carNum3 = "GWNIVER";
        parkingLot.park(carNum1);
        parkingLot.park(carNum2);
        parkingLot.park(carNum3);

        var result = parkingLot.status();

        var it = result.iterator();
        assertEquals(new TicketModel(1, carNum1), it.next());
        assertEquals(new TicketModel(2, carNum2), it.next());
        assertEquals(new TicketModel(3, carNum3), it.next());
        assertFalse(it.hasNext());
    }
}