package eu.fogas.parking.charge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChargeCalculatorTest {

    @Test
    void getCharge_shouldReturnEmptyOptional_whenHoursLessThanOne() {
        int hours = 0;
        var calculator = ChargeCalculator.builder().build();

        var charge = calculator.getCharge(hours);

        assertTrue(charge.isEmpty());
    }

    @Test
    void getCharge_shouldReturnBasePrice_whenHoursLessThanBaseHours() {
        int hours = 2;
        int baseHours = 5;
        int basePrice = 10;
        ChargeCalculator calculator = ChargeCalculator.builder()
                .baseHours(baseHours)
                .basePrice(basePrice)
                .build();

        var charge = calculator.getCharge(hours);

        assertTrue(charge.isPresent());
        assertEquals(basePrice, charge.get());
    }

    @Test
    void getCharge_shouldReturnBasePrice_whenHoursEqualsBaseHours() {
        int hours = 2;
        int baseHours = 2;
        int basePrice = 10;
        ChargeCalculator calculator = ChargeCalculator.builder()
                .baseHours(baseHours)
                .basePrice(basePrice)
                .build();

        var charge = calculator.getCharge(hours);

        assertTrue(charge.isPresent());
        assertEquals(basePrice, charge.get());
    }


    @Test
    void getCharge_shouldReturnCalculatedPrice_whenHoursMoreThanBaseHours() {
        int hours = 11;
        int baseHours = 2;
        int basePrice = 10;
        int extraPrice = 20;
        int price = basePrice + (hours - baseHours) * extraPrice;
        ChargeCalculator calculator = ChargeCalculator.builder()
                .baseHours(baseHours)
                .basePrice(basePrice)
                .extraPrice(extraPrice)
                .build();

        var charge = calculator.getCharge(hours);

        assertTrue(charge.isPresent());
        assertEquals(price, charge.get());
    }
}