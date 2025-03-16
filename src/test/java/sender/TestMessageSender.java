package sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class TestMessageSender {

    static Stream<Arguments> testSet() {
        return Stream.of(
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15), Country.RUSSIA, "Добро пожаловать"),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32), Country.USA, "Welcome"),
                Arguments.of("96.1.2.3", new Location("New York", Country.USA, " 10th Avenue", 32), Country.USA, "Welcome"),
                Arguments.of("172.5.3.66", new Location("Moscow", Country.RUSSIA, "Lenina", 15), Country.RUSSIA, "Добро пожаловать")
        );
    }

    @ParameterizedTest
    @MethodSource("testSet")
    public void testMessageSentBasedOnIp(String ip, Location location, Country country, String greeting) {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip)).thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(country)).thenReturn(greeting);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        String actual = messageSender.send(headers);

        Assertions.assertEquals(actual, greeting);
    }

}
