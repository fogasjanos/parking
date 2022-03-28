package eu.fogas.parking.command;

import eu.fogas.parking.lot.ParkingLot;
import eu.fogas.parking.lot.model.TicketModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusTest {
    @Mock(name = "commandLog")
    private Logger commandLogMock;
    @Captor
    private ArgumentCaptor<String> argumentCaptor;
    @Mock
    private ParkingLot parkingLotMock;
    @InjectMocks
    private Status command;

    @Test
    void process_shouldLogStatus() {
        var tickets = new TreeSet<>(Comparator.comparingInt(TicketModel::getSlotNumber));
        tickets.addAll(List.of(
                new TicketModel(1, "007 JB"),
                new TicketModel(2, "BAT 1")));
        when(parkingLotMock.status()).thenReturn(tickets);

        command.process(parkingLotMock);

        verify(commandLogMock).info(argumentCaptor.capture());
        verifyNoMoreInteractions(parkingLotMock, commandLogMock);
        assertEquals(
                "Slot No. Registration No." + System.lineSeparator() +
                        "1        007 JB" + System.lineSeparator() +
                        "2        BAT 1",
                argumentCaptor.getValue());
    }

    @Test
    void getName_shouldReturnStatus() {
        assertEquals("status", command.getName());
    }

    @Test
    void getParametersCount_shouldReturnZero() {
        assertEquals(0, command.getParametersCount());
    }
}