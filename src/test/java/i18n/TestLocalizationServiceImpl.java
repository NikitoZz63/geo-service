package i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

public class TestLocalizationServiceImpl {

    @ParameterizedTest
    @EnumSource(value = Country.class, mode = EnumSource.Mode.EXCLUDE, names = {"RUSSIA"})
    public void testCountryWithoutRussia(Country country) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String actual = localizationService.locale(country);
        String expected = "Welcome";

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @EnumSource(value = Country.class, names = {"RUSSIA"})
    public void testCountryRussia(Country country) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String actual = localizationService.locale(country);
        String expected = "Добро пожаловать";

        Assertions.assertEquals(expected, actual);
    }


}
