package main.java;

import main.java.logic.InitStations;
import main.java.model.Station;

import java.util.ArrayList;

public class Main {
    public static ArrayList<Station> stations = new ArrayList<Station>();

    public static void main(String[] args) {
        // write your code here
        System.out.println("hi");
        InitStations.initStations(stations, "data/StationMap.csv");
        stations.forEach((station -> System.out.println(station.toString())));
    }
}
