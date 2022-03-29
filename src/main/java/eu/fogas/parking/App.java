package eu.fogas.parking;

import eu.fogas.parking.command.*;
import eu.fogas.parking.config.ConfigProvider;
import eu.fogas.parking.exception.ParkingRuntimeException;
import eu.fogas.parking.lot.InMemoryParkingLot;
import eu.fogas.parking.lot.ParkingLot;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Slf4j
public class App {
    private final Logger commandLog = LoggerFactory.getLogger("CommandLogger");
    private final Map<String, Command> registeredCommands = new HashMap<>();
    private final ParkingLot parkingLot;

    public static void main(String[] args) {
        new App().start();
    }

    public App() {
        parkingLot = new InMemoryParkingLot(new ConfigProvider());
        init();
    }

    public void start() {
        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNext()) {
                var line = in.nextLine();
                var commands = line.split(" ");
                Optional<Command> commandOptional = findCommand(commands[0]);
                if (commandOptional.isPresent()) {
                    var params = skipFirstElement(commands);
                    Command command = commandOptional.get();
                    executeCommand(command, params);
                } else {
                    commandLog.info("Unknown command '{}'", line);
                }
            }
        }
    }

    private Optional<Command> findCommand(String text) {
        return registeredCommands.keySet()
                .stream()
                .filter(key -> key.equals(text))
                .map(registeredCommands::get)
                .findFirst();
    }

    private void executeCommand(Command command, String[] params) {
        log.debug("Command: {} {}", command, params);
        try {
            switch (params.length) {
                case 0 -> command.process(parkingLot);
                case 1 -> command.process(parkingLot, params[0]);
                case 2 -> command.process(parkingLot, params[0], params[1]);
                default -> command.process(parkingLot, params);
            }
        } catch (ParkingRuntimeException pre) {
            commandLog.info(pre.getMessage());
            log.error("Exception was thrown: {}", pre.getMessage(), pre);
        }
    }

    private <T> T[] skipFirstElement(T[] array) {
        return Arrays.copyOfRange(array, 1, array.length);
    }

    private void init() {
        registerCommand(new Create());
        registerCommand(new Leave());
        registerCommand(new Park());
        registerCommand(new Status());
    }

    private void registerCommand(Command command) {
        registeredCommands.put(command.getName(), command);
    }
}
