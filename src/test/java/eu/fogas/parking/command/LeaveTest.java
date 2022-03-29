package eu.fogas.parking.command;

import eu.fogas.parking.charge.ChargeCalculator;
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
class LeaveTest {
    @Mock(name = "commandLog")
    private Logger commandLogMock;
    @Mock
    private ParkingLot parkingLotMock;
    @Mock
    private ChargeCalculator chargeCalculatorMock;
    @InjectMocks
    private Leave command;

    @Test
    void process_shouldThrowException_whenParametersCountIsZero() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> command.process(parkingLotMock));

        assertEquals("Invalid parameter count! Expected: 2", e.getMessage());
    }

    @Test
    void process_shouldThrowException_whenParametersCountNotMatching() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> command.process(parkingLotMock, "3", "12", "Five"));

        assertEquals("Invalid parameter count! Expected: 2", e.getMessage());
    }

    @Test
    void process_shouldThrowException_whenParamterHoursNotInt() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> command.process(parkingLotMock, "OUTATIME", "P4R4M3T3R"));

        assertEquals("Invalid parameter: hours", e.getMessage());
    }

    @Test
    void process_shouldThrowException_whenParamterCarNumberEmpty() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> command.process(parkingLotMock, "", "P4R4M3T3R"));

        assertEquals("Parameter carNumber should not be blank!", e.getMessage());
    }

    @Test
    void process_shouldCallParkingLotLeaveAndChargeCalculator() {
        String carNum = "ECTO-1";
        int slotNum = 2;
        int cost = 99;
        when(parkingLotMock.leave(carNum)).thenReturn(Optional.of(slotNum));
        when(chargeCalculatorMock.getCharge(1)).thenReturn(Optional.of(cost));
        when(parkingLotMock.getCharger()).thenReturn(chargeCalculatorMock);

        command.process(parkingLotMock, carNum, "1");

        verify(parkingLotMock).leave(carNum);
        verify(commandLogMock).info(eq("Registration Number {} from Slot {} has left with Charge {}"),
                eq(carNum),
                eq(slotNum),
                eq(cost));
        verifyNoMoreInteractions(parkingLotMock, commandLogMock, chargeCalculatorMock);
    }

    @Test
    void process_shouldLogInvalidHours_whenHoursLessThanOne() {
        String carNum = "ECTO-1";
        int slotNum = 2;
        int cost = 99;
        when(parkingLotMock.leave(carNum)).thenReturn(Optional.of(slotNum));
        when(chargeCalculatorMock.getCharge(0)).thenReturn(Optional.empty());
        when(parkingLotMock.getCharger()).thenReturn(chargeCalculatorMock);

        command.process(parkingLotMock, carNum, "0");

        verify(parkingLotMock).leave(carNum);
        verify(commandLogMock).info(eq("Invalid hours! {}"), eq(0));
        verifyNoMoreInteractions(parkingLotMock, commandLogMock, chargeCalculatorMock);
    }

    @Test
    void process_shouldLogErrorWhenRegistrationNumberNotFound() {
        String carNum = "ECTO-1";
        when(parkingLotMock.leave(carNum)).thenReturn(Optional.empty());

        command.process(parkingLotMock, carNum, "1");

        verify(parkingLotMock).leave(carNum);
        verify(commandLogMock).info(eq("Registration Number {} not found"), eq(carNum));
        verifyNoMoreInteractions(parkingLotMock, commandLogMock);
    }

    @Test
    void getName_shouldReturnLeave() {
        assertEquals("leave", command.getName());
    }

    @Test
    void getParametersCount_shouldReturnTwo() {
        assertEquals(2, command.getParametersCount());
    }
}