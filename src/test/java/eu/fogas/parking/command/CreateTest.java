package eu.fogas.parking.command;

import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import eu.fogas.parking.lot.ParkingLot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @ParameterizedTest
    @MethodSource("invalidParametersSupplier")
    void process_shouldThrowException_whenParametersCountNotMatching(String[] params) {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> command.process(parkingLotMock, params));

        assertEquals("Invalid parameter count! Expected: 1", e.getMessage());
    }

    private static Stream<Arguments> invalidParametersSupplier() {
        return Stream.of(
                Arguments.of((Object) new String[]{}),
                Arguments.of((Object) new String[]{"3", "12"})
        );
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
        verify(commandLogMock).info("Created parking lot with {} slots", 3);
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