package main.java;

import main.java.logic.GetAllPaths;
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

    public static void main(String[] args) {
        // write your code here
        System.out.println("hi");
        InitStations.initStations(stations, "data/StationMap.csv");
        BufferedReader br = new
                BufferedReader(new InputStreamReader(System.in));
        try {
            String[] inputs = br.readLine().split("\\s+");
            StationCode originCode = new StationCode(inputs[0]);
            StationCode destinationCode = new StationCode(inputs[1]);
            Station originStation = stations.stream()
                    .filter(station -> station.getStationCode().equals(originCode))
                    .findAny()
                    .orElse(null);
            Station destinationStation = stations.stream()
                    .filter(station -> station.getStationCode().equals(destinationCode))
                    .findAny()
                    .orElse(null);
            DateTime startTime = DateTime.now();
            GetAllPaths.init(originStation, destinationStation, startTime);
            List<Pair<List<Station>, DateTime>> results = GetAllPaths.getAllPaths(stations);
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
        } catch (IOException e) {
            System.out.println("err");
        }
    }
}
