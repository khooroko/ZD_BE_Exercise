package test.java.logic;

import main.java.logic.InitStations;
import main.java.model.Station;
import main.java.utils.Errors;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InitStationsTest {

    @Test
    void initStations_validCSV() throws IOException {
        List<Station> stations = InitStations.initStations(
                new FileInputStream(new File("src/test/java/logic/1.csv")));
        assertEquals(1, stations.size());
    }

    @Test
    void initStations_invalidNumColumns_throwsException() {
        String thrown =
                assertThrows(IllegalArgumentException.class,
                        () -> InitStations.initStations(
                                new FileInputStream(new File("src/test/java/logic/2.csv")))).getMessage();
        assertEquals(Errors.ERR_FAILED_TO_PARSE, thrown);
    }

    @Test
    void initStations_headersOnly_throwsNoException() throws IOException {
        List<Station> stations = InitStations.initStations(
                new FileInputStream(new File("src/test/java/logic/3.csv")));
        assertEquals(0, stations.size());
    }
}