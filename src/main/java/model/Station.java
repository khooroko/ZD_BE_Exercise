package main.java.model;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class Station {
    private StationCode stationCode;
    private String name;
    // neighbours includes transferring to same station
    // e.g. Buona Vista (EW) is neighbour of Buona Vista (CC)
    private ArrayList<Station> neighbours = new ArrayList<>();
    private DateTime openDateTime;

    public Station(StationCode stationCode, String name) {
        this.stationCode = stationCode;
        this.name = name;
    }

    public Station(String line, String stationNumber, String name, DateTime openDateTime) {
        this.stationCode = new StationCode(line, stationNumber);
        this.name = name;
        this.openDateTime = openDateTime;
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

    public DateTime getOpenDateTime() {
        return openDateTime;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", stationCode.toString(), name);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Station
                && ((Station)other).getStationCode().equals(stationCode)
                && ((Station)other).getName().equals(name);
    }
}
