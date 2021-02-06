package main.java.model;

import java.util.ArrayList;

public class Station {
    private StationCode stationCode;
    private String name;
    // neighbours includes transferring to same station
    // e.g. Buona Vista (EW) is neighbour of Buona Vista (CC)
    private ArrayList<Station> neighbours = new ArrayList<>();

    public Station(StationCode stationCode, String name) {
        this.stationCode = stationCode;
        this.name = name;
    }

    public Station(String line, String stationNumber, String name) {
        this.stationCode = new StationCode(line, stationNumber);
        this.name = name;
    }

    public StationCode getStationCode() {
        return stationCode;
    }

    public String getName() {
        return name;
    }

    public void addNeighbour(Station station) {
        neighbours.add(station);
    }

    public ArrayList<Station> getNeighbours() {
        return neighbours;
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationCode=" + stationCode.toString() +
                ", name=" + name +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Station
                && ((Station)other).getStationCode().equals(stationCode)
                && ((Station)other).getName().equals(name);
    }
}
