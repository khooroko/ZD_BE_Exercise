package main.java;

import main.java.logic.GetAllPaths;
import main.java.logic.InitStations;
import main.java.model.Station;
import main.java.model.StationCode;

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
//        stations.forEach((station -> System.out.println(station.toString())));
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
            List<List<Station>> paths = GetAllPaths.getAllPaths(stations, originStation, destinationStation);
            for (List<Station> path: paths) {
                String toPrint = "";
                for (Station station: path) {
                    toPrint += ", " + station.getStationCode();
                }
                toPrint = toPrint.replaceFirst(", ", "");
                System.out.println(toPrint);
            }
        } catch (IOException e) {
            System.out.println("err");
        }
    }
}
