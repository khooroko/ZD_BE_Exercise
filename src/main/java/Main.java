package main.java;

import main.java.logic.InitStations;
import main.java.logic.PathGetter;
import main.java.model.Station;
import main.java.model.StationCode;
import main.java.utils.Errors;
import main.java.utils.Pair;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

public class Main {

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
                                System.out.println(String.format("Take %s line from %s to %s",
                                        start.getStationCode().getLine(),
                                        start.toString(),
                                        result.getFirst().get(k - 1).toString()));
                            }
                            System.out.println(String.format("Change from %s line to %s line",
                                    start.getStationCode().getLine(),
                                    current.getStationCode().getLine()));
                            j = --k;
                            break;
                        } else if (k == result.getFirst().size() - 1) {
                            System.out.println(String.format("Take %s line from %s to %s",
                                    start.getStationCode().getLine(),
                                    start.toString(),
                                    current.toString()));
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

    public static void main(String[] args) throws IOException {
        List<Station> stations = InitStations.initStations("data/StationMap.csv");
        PathGetter.init(stations);
        BufferedReader br = new
                BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String[] inputs = br.readLine().split("\\s+");
                if (inputs.length == 1 && inputs[0].toLowerCase(Locale.ROOT).equals("exit")) {
                    break;
                }
                if (inputs.length < 2) {
                    throw new IllegalArgumentException(Errors.ERR_INVALID_ARG_NUMBER);
                }
                StationCode originCode = new StationCode(inputs[0]);
                StationCode destinationCode = new StationCode(inputs[1]);
                DateTime startTime;
                if (inputs.length >= 3) {
                    try {
                        startTime = DateTime.parse(inputs[2]);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException(Errors.ERR_INVALID_DATE_FORMAT);
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
                    throw new IllegalArgumentException(Errors.ERR_SOURCE_NOT_EXIST);
                }
                if (destinationStation == null) {
                    throw new IllegalArgumentException(Errors.ERR_DEST_NOT_EXIST);
                }
                List<Pair<List<Station>, DateTime>> results =
                        PathGetter.getAllPaths(originStation, destinationStation, startTime);
                printResults(results, startTime, originCode, destinationCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
