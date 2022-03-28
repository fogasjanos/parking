package eu.fogas.parking.command;

import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public abstract class AbstractCommand implements Command {
    protected Logger commandLog = LoggerFactory.getLogger("CommandLogger");

    static int parseInt(String parameterName, String parameter) throws InvalidParameterRuntimeException {
        isNotNull(parameterName, parameter);
        try {
            return Integer.parseInt(parameter);
        } catch (NumberFormatException nfe) {
            log.error("Invalid parameter! '{}' is not a valid number!", parameter, nfe);
            throw new InvalidParameterRuntimeException("Invalid parameter: " + parameterName);
        }
    }

    static String parseString(String parameterName, String parameter) throws InvalidParameterRuntimeException {
        isNotNull(parameterName, parameter);
        if (parameter.isBlank()) {
            throw createAndLogException("Parameter " + parameterName + " should not be blank!");
        }
        return parameter;
    }

    static void isNotNull(String parameterName, String parameter) {
        if (parameter == null) {
            throw createAndLogException("Missing parameter: " + parameterName);
        }
    }

    private static InvalidParameterRuntimeException createAndLogException(String message) {
        var e = new InvalidParameterRuntimeException(message);
        log.error("{}", e.getMessage(), e);
        return e;
    }
}
