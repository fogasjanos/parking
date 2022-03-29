package eu.fogas.parking.command;

import eu.fogas.parking.exception.InvalidParameterRuntimeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AbstractCommandTest {

    @Test
    void parseInt_shouldThrowInvalidParameterRuntimeException_whenParameterIsNull() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> AbstractCommand.parseInt("paramName", null));

        assertEquals("Missing parameter: paramName", e.getMessage());
    }

    @Test
    void parseInt_shouldThrowInvalidParameterRuntimeException_whenParameterIsNotANumber() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> AbstractCommand.parseInt("paramName", "five"));

        assertEquals("Invalid parameter: paramName", e.getMessage());
    }

    @Test
    void parseInt_shouldReturnIntValueOfParameter_whenParameterIsValidNumberAsString() {
        var result = AbstractCommand.parseInt("paramName", "5");

        assertEquals(5, result);
    }

    @Test
    void parseString_shouldThrowInvalidParameterRuntimeException_whenParameterIsNull() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> AbstractCommand.parseString("paramName", null));

        assertEquals("Missing parameter: paramName", e.getMessage());
    }

    @Test
    void parseString_shouldThrowInvalidParameterRuntimeException_whenParameterIsEmpty() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> AbstractCommand.parseString("paramName", ""));

        assertEquals("Parameter paramName should not be blank!", e.getMessage());
    }


    @Test
    void parseString_shouldThrowInvalidParameterRuntimeException_whenParameterContainsWhiteSpacesOnly() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> AbstractCommand.parseString("paramName", " \t \n \r  "));

        assertEquals("Parameter paramName should not be blank!", e.getMessage());
    }

    @Test
    void parseString_shouldReturnSameValue_whenParameterIsNotBlank() {
        String parameter = "Land of sand";

        var result = AbstractCommand.parseString("paramName", parameter);

        assertEquals(parameter, result);
    }

    @Test
    void isNotNull_shouldThrowInvalidParameterRuntimeException_whenParameterIsNull() {
        var e = assertThrows(InvalidParameterRuntimeException.class,
                () -> AbstractCommand.isNotNull("paramName", null));

        assertEquals("Missing parameter: paramName", e.getMessage());
    }

    @Test
    void isNotNull_shouldNotThrowAnything_whenParameterIsNotNull() {
        AbstractCommand.isNotNull("emptyParameter", "");
        AbstractCommand.isNotNull("whitespaceParameter", " \t \r \n ");
        AbstractCommand.isNotNull("normalParameter", "parameterValue");
    }
}