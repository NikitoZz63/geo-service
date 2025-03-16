package geo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class TestGeoService {

    static Stream<Arguments> stringIp() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.2.44.12", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.33.1.45", new Location("New York", Country.USA, null, 0)),
                Arguments.of("1.1.1.1", null)

        );
    }

    @AfterAll
    public static void afterAllFinish() {
        System.out.println("Test finish");
    }

    @ParameterizedTest
    @MethodSource("stringIp")
    public void testsByIp(String ip, Location expected) {
        GeoService geoService = new GeoServiceImpl();
        Location location = geoService.byIp(ip);
        Assertions.assertEquals(expected, location);
    }

    @Test
    public void testByCoordinates() {
        GeoService geoService = new GeoServiceImpl();
        Assertions.assertThrows(RuntimeException.class, () -> geoService.byCoordinates(1, 2));
    }


}
