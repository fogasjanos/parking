package eu.fogas.parking.command;

import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import eu.fogas.parking.lot.ParkingLot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class CreateTest {
    @Mock(name = "commandLog")
    private Logger commandLogMock;
    @Mock
    private ParkingLot parkingLotMock;
    @InjectMocks
    private Create command;

    @Test
    void process_shouldThrowException_whenParametersCountIsZero() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> command.process(parkingLotMock));

        assertEquals("Invalid parameter count! Expected: 1", e.getMessage());
    }

    @Test
    void process_shouldThrowException_whenParametersCountNotMatching() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> command.process(parkingLotMock, "3", "12"));

        assertEquals("Invalid parameter count! Expected: 1", e.getMessage());
    }

    @Test
    void process_shouldThrowException_whenParameterNotInt() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> command.process(parkingLotMock, "parkingLot"));

        assertEquals("Invalid parameter: size", e.getMessage());
    }

    @Test
    void process_shouldCallParkingLotCreate() {
        command.process(parkingLotMock, "3");

        verify(parkingLotMock).create(3);
        verify(commandLogMock).info(eq("Created parking lot with {} slots"), eq(3));
        verifyNoMoreInteractions(parkingLotMock, commandLogMock);
    }

    @Test
    void getName_shouldReturnCreate() {
        assertEquals("create", command.getName());
    }

    @Test
    void getParametersCount_shouldReturnOne() {
        assertEquals(1, command.getParametersCount());
    }
}