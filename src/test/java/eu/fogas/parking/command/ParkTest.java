package eu.fogas.parking.command;

import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import eu.fogas.parking.lot.ParkingLot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkTest {
    @Mock(name = "commandLog")
    private Logger commandLogMock;
    @Mock
    private ParkingLot parkingLotMock;
    @InjectMocks
    private Park command;

    @Test
    void process_shouldThrowException_whenParametersCountIsZero() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> command.process(parkingLotMock));

        assertEquals("Invalid parameter count! Expected: 1", e.getMessage());
    }

    @Test
    void process_shouldThrowException_whenParametersCountNotMatching() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> command.process(parkingLotMock, "3", "Five"));

        assertEquals("Invalid parameter count! Expected: 1", e.getMessage());
    }

    @Test
    void process_shouldThrowException_whenCarNumberIsEmpty() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> command.process(parkingLotMock, ""));

        assertEquals("Parameter carNumber should not be blank!", e.getMessage());
    }

    @Test
    void process_shouldCallParkingLotPark() {
        String carNum = "ECTO-1";
        int slotNum = 2;
        when(parkingLotMock.park(carNum)).thenReturn(Optional.of(slotNum));

        command.process(parkingLotMock, carNum);

        verify(parkingLotMock).park(carNum);
        verify(commandLogMock).info(eq("Allocated slot number: {}"), eq(slotNum));
        verifyNoMoreInteractions(parkingLotMock, commandLogMock);
    }

    @Test
    void process_shouldLogError_whenParkingLotIsFull() {
        String carNum = "ECTO-1";
        when(parkingLotMock.park(carNum)).thenReturn(Optional.empty());

        command.process(parkingLotMock, carNum);

        verify(parkingLotMock).park(carNum);
        verify(commandLogMock).info(eq("Sorry, parking lot is full"));
        verifyNoMoreInteractions(parkingLotMock, commandLogMock);
    }

    @Test
    void getName_shouldReturnPark() {
        assertEquals("park", command.getName());
    }

    @Test
    void getParametersCount_shouldReturnTwo() {
        assertEquals(1, command.getParametersCount());
    }
}