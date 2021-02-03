package main.java.model;

public class StationCode {
    private String line;
    private String stationNumber;

    public StationCode(String line, String stationNumber) {
        this.line = line;
        this.stationNumber = stationNumber;
    }

    public String getLine() {
        return line;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    @Override
    public String toString() {
        return line + stationNumber;
    }
}
