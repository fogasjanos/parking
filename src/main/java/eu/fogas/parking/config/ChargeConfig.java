package eu.fogas.parking.config;

import lombok.Data;

@Data
public class ChargeConfig {
    private static final String BASE_HOURS = "base.hours";
    private static final int BASE_HOURS_DEFAULT_VALUE = 2;
    private static final String BASE_PRICE = "base.price";
    private static final int BASE_PRICE_DEFAULT_VALUE = 10;
    private static final String EXTRA_PRICE = "extra.price";
    private static final int EXTRA_PRICE_DEFAULT_VALUE = 10;

    private final int baseHours;
    private final int basePrice;
    private final int extraPrice;

    public ChargeConfig(ConfigProvider config) {
        baseHours = config.getIntProperty(BASE_HOURS, BASE_HOURS_DEFAULT_VALUE);
        basePrice = config.getIntProperty(BASE_PRICE, BASE_PRICE_DEFAULT_VALUE);
        extraPrice = config.getIntProperty(EXTRA_PRICE, EXTRA_PRICE_DEFAULT_VALUE);
    }
}
