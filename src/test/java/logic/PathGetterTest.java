package test.java.logic;

import main.java.logic.PathGetter;
import main.java.model.Station;
import main.java.utils.Constants;
import main.java.utils.Pair;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PathGetterTest {
    private static final ArrayList<Station> stations = new ArrayList<>();
    private static final Station NS1EW3 = new Station(
            "NS", "1", "NS1EW3", DateTime.now().minusDays(2));
    private static final Station NS2 = new Station("NS", "2", "NS2", DateTime.now().minusDays(6));
    private static final Station NS3 = new Station("NS", "3", "NS3", DateTime.now().minusDays(6));
    private static final Station NS4 = new Station("NS", "4", "NS4", DateTime.now().minusDays(6));
    private static final Station NS5 = new Station("NS", "5", "NS5", DateTime.now().minusDays(6));
    private static final Station NS6 = new Station("NS", "6", "NS6", DateTime.now().minusDays(6));
    private static final Station NS7 = new Station("NS", "7", "NS7", DateTime.now().minusDays(6));
    private static final Station NS8CC4 = new Station(
            "NS", "8", "NS8CC4", DateTime.now().minusDays(6));
    private static final Station NS9EW7 = new Station(
            "NS", "9", "NS9EW7", DateTime.now().minusDays(6));
    private static final Station EW1 = new Station("EW", "1", "EW1", DateTime.now().minusDays(4));
    private static final Station EW2 = new Station("EW", "2", "EW2", DateTime.now().minusDays(4));
    private static final Station EW3NS1 = new Station(
            "EW", "3", "NS1EW3", DateTime.now().minusDays(4));
    private static final Station EW4 = new Station("EW", "4", "EW4", DateTime.now().minusDays(4));
    private static final Station EW5CC2 = new Station(
            "EW", "5", "EW5CC2", DateTime.now().minusDays(4));
    private static final Station EW6 = new Station("EW", "6", "EW6", DateTime.now().minusDays(4));
    private static final Station EW7NS9 = new Station(
            "EW", "7", "NS9EW7", DateTime.now().minusDays(4));
    private static final Station EW8 = new Station("EW", "8", "EW8", DateTime.now().minusDays(4));
    private static final Station EW9CC6DT2 = new Station(
            "EW", "9", "EW9CC6DT2", DateTime.now().minusDays(4));
    private static final Station EW10 = new Station("EW", "10", "EW10", DateTime.now().minusDays(4));
    private static final Station EW11 = new Station("EW", "11", "EW11", DateTime.now().minusDays(4));
    private static final Station CC1 = new Station("CC", "1", "CC1", DateTime.now().minusDays(2));
    private static final Station CC2EW5 = new Station(
            "CC", "2", "EW5CC2", DateTime.now().minusDays(2));
    private static final Station CC3 = new Station("CC", "3", "CC3", DateTime.now().minusDays(2));
    private static final Station CC4NS8 = new Station(
            "CC", "4", "NS8CC4", DateTime.now().minusDays(2));
    private static final Station CC5 = new Station("CC", "5", "CC5", DateTime.now().minusDays(2));
    private static final Station CC6EW9DT2 = new Station(
            "CC", "6", "EW9CC6DT2", DateTime.now().minusDays(2));
    private static final Station CC7 = new Station("CC", "7", "CC7", DateTime.now().minusDays(2));
    private static final Station DT1 = new Station("DT", "1", "DT1", DateTime.now().plusDays(2));
    private static final Station DT2EW9CC6 = new Station(
            "DT", "2", "EW9CC6DT2", DateTime.now().plusDays(2));
    private static final Station DT3 = new Station("DT", "3", "DT3", DateTime.now().plusDays(2));

    /**
     *     o-o-o-o-o NS
     *     |       |
     *     o   o---x---o    o DT
     *     |   |   |    \ /
     * o-o-x-o-x-o-x-o--x-o-o EW
     *         |       / |
     *         o     o   o CC
     */
    @BeforeAll
    static void init() {
        NS1EW3.addNeighbour(NS2);
        NS2.addNeighbour(NS1EW3);
        NS2.addNeighbour(NS3);
        NS3.addNeighbour(NS2);
        NS3.addNeighbour(NS4);
        NS4.addNeighbour(NS3);
        NS4.addNeighbour(NS5);
        NS5.addNeighbour(NS4);
        NS5.addNeighbour(NS6);
        NS6.addNeighbour(NS5);
        NS6.addNeighbour(NS7);
        NS7.addNeighbour(NS6);
        NS7.addNeighbour(NS8CC4);
        NS8CC4.addNeighbour(NS7);
        NS8CC4.addNeighbour(NS9EW7);
        NS9EW7.addNeighbour(NS8CC4);

        EW1.addNeighbour(EW2);
        EW2.addNeighbour(EW1);
        EW2.addNeighbour(EW3NS1);
        EW3NS1.addNeighbour(EW2);
        EW3NS1.addNeighbour(EW4);
        EW3NS1.addNeighbour(NS1EW3);
        NS1EW3.addNeighbour(EW3NS1);
        EW4.addNeighbour(EW3NS1);
        EW4.addNeighbour(EW5CC2);
        EW5CC2.addNeighbour(EW4);
        EW5CC2.addNeighbour(EW6);
        EW6.addNeighbour(EW5CC2);
        EW6.addNeighbour(EW7NS9);
        EW7NS9.addNeighbour(EW6);
        EW7NS9.addNeighbour(EW8);
        EW7NS9.addNeighbour(NS9EW7);
        NS9EW7.addNeighbour(EW7NS9);
        EW8.addNeighbour(EW7NS9);
        EW8.addNeighbour(EW9CC6DT2);
        EW9CC6DT2.addNeighbour(EW8);
        EW9CC6DT2.addNeighbour(EW10);
        EW10.addNeighbour(EW9CC6DT2);
        EW10.addNeighbour(EW11);
        EW11.addNeighbour(EW10);

        CC1.addNeighbour(CC2EW5);
        CC2EW5.addNeighbour(CC1);
        CC2EW5.addNeighbour(CC3);
        CC2EW5.addNeighbour(EW5CC2);
        EW5CC2.addNeighbour(CC2EW5);
        CC3.addNeighbour(CC2EW5);
        CC3.addNeighbour(CC4NS8);
        CC4NS8.addNeighbour(CC3);
        CC4NS8.addNeighbour(CC5);
        CC4NS8.addNeighbour(NS8CC4);
        NS8CC4.addNeighbour(CC4NS8);
        CC5.addNeighbour(CC4NS8);
        CC5.addNeighbour(CC6EW9DT2);
        CC6EW9DT2.addNeighbour(CC5);
        CC6EW9DT2.addNeighbour(CC7);
        CC6EW9DT2.addNeighbour(EW9CC6DT2);
        EW9CC6DT2.addNeighbour(CC6EW9DT2);
        CC7.addNeighbour(CC6EW9DT2);

        DT1.addNeighbour(DT2EW9CC6);
        DT2EW9CC6.addNeighbour(DT1);
        DT2EW9CC6.addNeighbour(DT3);
        DT2EW9CC6.addNeighbour(EW9CC6DT2);
        EW9CC6DT2.addNeighbour(DT2EW9CC6);
        DT2EW9CC6.addNeighbour(CC6EW9DT2);
        CC6EW9DT2.addNeighbour(DT2EW9CC6);
        DT3.addNeighbour(DT2EW9CC6);

        stations.add(NS1EW3);
        stations.add(NS2);
        stations.add(NS3);
        stations.add(NS4);
        stations.add(NS5);
        stations.add(NS6);
        stations.add(NS7);
        stations.add(NS8CC4);
        stations.add(NS9EW7);

        stations.add(EW1);
        stations.add(EW2);
        stations.add(EW3NS1);
        stations.add(EW4);
        stations.add(EW5CC2);
        stations.add(EW6);
        stations.add(EW7NS9);
        stations.add(EW8);
        stations.add(EW9CC6DT2);
        stations.add(EW10);
        stations.add(EW11);

        stations.add(CC1);
        stations.add(CC2EW5);
        stations.add(CC3);
        stations.add(CC4NS8);
        stations.add(CC5);
        stations.add(CC6EW9DT2);
        stations.add(CC7);

        stations.add(DT1);
        stations.add(DT2EW9CC6);
        stations.add(DT3);

        PathGetter.init(stations);
    }

    @Test
    void getAllPaths_immediateNeighbour_shouldReturn1Path() {
        int expectedNumberOfPaths = 1;
        int actualNumberOfPaths = PathGetter.getAllPaths(EW1, EW2, DateTime.now().withTimeAtStartOfDay()).size();
        assertEquals(expectedNumberOfPaths, actualNumberOfPaths);
    }

    @Test
    void getAllPaths_EW2_to_CC4() {
        // There should be 2 paths with similar time taken
        int expectedNumberOfPaths = 2;
        List<Pair<List<Station>, DateTime>> actualResults = PathGetter.getAllPaths(
                EW3NS1, CC4NS8, DateTime.now().withTimeAtStartOfDay());
        assertEquals(expectedNumberOfPaths, actualResults.size());
        // Check routes ordered by time taken (ascending)
        assertTrue(!actualResults.get(0).getSecond().isAfter(actualResults.get(1).getSecond()));
        // Check that shorter path transfers at EW5CC2
        assertTrue(actualResults.get(0).getFirst().contains(EW5CC2)
                && actualResults.get(0).getFirst().indexOf(CC2EW5)
                == actualResults.get(0).getFirst().indexOf(EW5CC2) + 1);
        // Check that longer path transfers at NS9EW7 and NS8CC4
        assertTrue(actualResults.get(1).getFirst().contains(EW7NS9)
                && actualResults.get(1).getFirst().indexOf(NS9EW7)
                == actualResults.get(1).getFirst().indexOf(EW7NS9) + 1);
        assertTrue(actualResults.get(1).getFirst().contains(NS8CC4)
                && actualResults.get(1).getFirst().indexOf(CC4NS8)
                == actualResults.get(1).getFirst().indexOf(NS8CC4) + 1);
    }

    @Test
    void getAllPaths_EW2_to_CC4_CC_notOpen_noPaths() {
        // There should be 0 paths as CC is closed
        int expectedNumberOfPaths = 0;
        List<Pair<List<Station>, DateTime>> actualResults = PathGetter.getAllPaths(
                EW3NS1, CC4NS8, DateTime.now().minusDays(3).withTimeAtStartOfDay());
        assertEquals(expectedNumberOfPaths, actualResults.size());
    }

    @Test
    void getAllPaths_DT1_to_EW3() {
        // There should be 3 paths
        // Peak hour
        int hourOfDay = 6;
        int expectedNumberOfPaths = 3;
        List<Pair<List<Station>, DateTime>> actualResults = PathGetter.getAllPaths(
                DT1, EW3NS1, DateTime.now().plusDays(3).withHourOfDay(hourOfDay));
        assertEquals(expectedNumberOfPaths, actualResults.size());
        // Check routes ordered by time taken (ascending)
        assertTrue(!actualResults.get(0).getSecond().isAfter(actualResults.get(1).getSecond())
            && !actualResults.get(1).getSecond().isAfter(actualResults.get(2).getSecond()));
        // Check path 1 transfers to EW without transferring to CC
        assertTrue(actualResults.get(0).getFirst().contains(DT2EW9CC6)
                && actualResults.get(0).getFirst().indexOf(EW9CC6DT2)
                == actualResults.get(0).getFirst().indexOf(DT2EW9CC6) + 1);
        assertFalse(actualResults.get(0).getFirst().contains(CC6EW9DT2));
        // Check path 2 transfers to CC before transferring to EW at a later station
        assertTrue(actualResults.get(1).getFirst().contains(DT2EW9CC6)
                && actualResults.get(1).getFirst().indexOf(CC6EW9DT2)
                == actualResults.get(1).getFirst().indexOf(DT2EW9CC6) + 1);
        assertFalse(actualResults.get(1).getFirst().contains(EW9CC6DT2));
        assertTrue(actualResults.get(1).getFirst().contains(CC2EW5)
                && actualResults.get(1).getFirst().indexOf(EW5CC2)
                == actualResults.get(1).getFirst().indexOf(CC2EW5) + 1);
        // Check path 3 transfers to CC, then NS, then EW
        assertTrue(actualResults.get(2).getFirst().contains(DT2EW9CC6)
                && actualResults.get(2).getFirst().indexOf(CC6EW9DT2)
                == actualResults.get(2).getFirst().indexOf(DT2EW9CC6) + 1);
        assertFalse(actualResults.get(2).getFirst().contains(EW9CC6DT2));
        assertTrue(actualResults.get(2).getFirst().contains(CC4NS8)
                && actualResults.get(2).getFirst().indexOf(NS8CC4)
                == actualResults.get(2).getFirst().indexOf(CC4NS8) + 1);
        assertTrue(actualResults.get(2).getFirst().contains(NS9EW7)
                && actualResults.get(2).getFirst().indexOf(EW7NS9)
                == actualResults.get(2).getFirst().indexOf(NS9EW7) + 1);
    }

    @Test
    void getAllPaths_DT1_to_EW3_atNight_noPaths() {
        // There should be 0 paths as DT line does not operate at night
        // Night hour
        int hourOfDay = 1;
        int expectedNumberOfPaths = 0;
        List<Pair<List<Station>, DateTime>> actualResults = PathGetter.getAllPaths(
                DT1, EW3NS1, DateTime.now().plusDays(3).withHourOfDay(hourOfDay));
        assertEquals(expectedNumberOfPaths, actualResults.size());
    }

    @Test
    void getAllPaths_DT1_to_DT3_timeChangeDuringJourney() {
        // time change during journey
        int hourOfDay = 17;
        int minuteOfHour = 55;
        DateTime startTime = DateTime.now().plusDays(4).withHourOfDay(hourOfDay).withMinuteOfHour(minuteOfHour);
        List<Pair<List<Station>, DateTime>> actualResults = PathGetter.getAllPaths(
                DT1, DT3, startTime);

        // assertions to make the test make sense
        assertTrue(Constants.TravelTimes.NonPeak.DT != Constants.TravelTimes.Peak.OTHERS);
        assertTrue(hourOfDay >= Constants.TravelTimes.Peak.morningEnd.getHour()
            && hourOfDay < Constants.TravelTimes.Peak.eveningStart.getHour());
        assertTrue(Constants.TravelTimes.getTravelTime(
                DT1.getStationCode(),
                DT2EW9CC6.getStationCode(),
                startTime) + minuteOfHour > 60);
        assertEquals(Constants.TravelTimes.Peak.OTHERS, Constants.TravelTimes.getTravelTime(
                DT2EW9CC6.getStationCode(),
                DT3.getStationCode(),
                startTime.plusMinutes(Constants.TravelTimes.NonPeak.DT)));

        assertEquals(startTime.plusMinutes(Constants.TravelTimes.NonPeak.DT
                + Constants.TravelTimes.Peak.OTHERS),
                actualResults.get(0).getSecond());

    }
}