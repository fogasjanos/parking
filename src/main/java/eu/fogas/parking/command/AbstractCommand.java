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
            throw new InvalidParameterRuntimeException("Invalid parameter: " + parameterName);
        }
    }

    static String parseString(String parameterName, String parameter) throws InvalidParameterRuntimeException {
        isNotNull(parameterName, parameter);
        if (parameter.isBlank()) {
            throw new InvalidParameterRuntimeException("Parameter " + parameterName + " should not be blank!");
        }
        return parameter;
    }

    static void isNotNull(String parameterName, String parameter) {
        if (parameter == null) {
            throw new InvalidParameterRuntimeException("Missing parameter: " + parameterName);
        }
    }
}
