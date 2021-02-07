package main.java;

import main.java.logic.PathGetter;
import main.java.logic.InitStations;
import main.java.model.Station;
import main.java.model.StationCode;
import main.java.utils.Pair;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static ArrayList<Station> stations = new ArrayList<>();

    private static void printResults(List<Pair<List<Station>, DateTime>> results,
                                     DateTime startTime,
                                     StationCode originCode,
                                     StationCode destinationCode) {
        if (results.size() > 0) {
            System.out.println("Routes sorted by travel time");
            for (int i = 0; i < results.size(); i++) {
                System.out.println(String.format("Route %d:", i));
                Pair<List<Station>, DateTime> result = results.get(i);
                for (int j = 0; j < result.getFirst().size() - 1; j++) {
                    Station start = result.getFirst().get(j);
                    for (int k = j + 1; k < result.getFirst().size(); k++) {
                        Station current = result.getFirst().get(k);
                        if (!current.getStationCode().getLine().equals(start.getStationCode().getLine())) {
                            if (k - j > 1) {
                                System.out.println(String.format("Take %s from %s to %s",
                                        start.getStationCode().getLine(),
                                        start.getStationCode().toString(),
                                        result.getFirst().get(k - 1).getStationCode().toString()));
                            }
                            System.out.println(String.format("Change to %s", current.getStationCode().getLine()));
                            j = --k;
                            break;
                        } else if (k == result.getFirst().size() - 1) {
                            System.out.println(String.format("Take %s from %s to %s",
                                    start.getStationCode().getLine(),
                                    start.getStationCode().toString(),
                                    current.getStationCode().toString()));
                            j = --k;
                            break;
                        }
                    }
                }
                System.out.println(String.format("Time taken: %d minutes",
                        Minutes.minutesBetween(startTime, result.getSecond()).getMinutes()));
                System.out.println();
            }
        } else {
            System.out.println(String.format("No routes available between %s and %s at this time.",
                    originCode, destinationCode));
        }
    }

    public static void main(String[] args) {
        InitStations.initStations(stations, "data/StationMap.csv");
        PathGetter.init(stations);
        BufferedReader br = new
                BufferedReader(new InputStreamReader(System.in));
        try {
            String[] inputs = br.readLine().split("\\s+");
            if (inputs.length < 2) {
                throw new IllegalArgumentException("Invalid number of arguments.");
            }
            StationCode originCode = new StationCode(inputs[0]);
            StationCode destinationCode = new StationCode(inputs[1]);
            DateTime startTime;
            if (inputs.length >= 3) {
                try {
                    startTime = DateTime.parse(inputs[2]);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "Invalid date format. Please use \"YYYY-MM-DDThh:mm\" format, e.g. '2019-01-31T16:00'"
                    );
                }
            } else {
                startTime = DateTime.now();
            }
            Station originStation = stations.stream()
                    .filter(station -> station.getStationCode().equals(originCode))
                    .findAny()
                    .orElse(null);
            Station destinationStation = stations.stream()
                    .filter(station -> station.getStationCode().equals(destinationCode))
                    .findAny()
                    .orElse(null);
            if (originStation == null) {
                throw new NullPointerException("Source station does not exist!");
            }
            if (destinationStation == null) {
                throw new NullPointerException("Destination station does not exist!");
            }
            List<Pair<List<Station>, DateTime>> results = PathGetter.getAllPaths(originStation, destinationStation, startTime);
            printResults(results, startTime, originCode, destinationCode);
        } catch (IOException e) {
            System.out.println("err");
        }
    }
}
