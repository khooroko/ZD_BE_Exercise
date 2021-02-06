package main.java.model;

public class StationCode {
    private String line;
    private String stationNumber;

    public StationCode(String fullCode) {
        String[] splitDetails = fullCode.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        this.line = splitDetails[0];
        this.stationNumber = splitDetails[1];
    }

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

    @Override
    public boolean equals(Object other) {
        return other instanceof StationCode
                && ((StationCode)other).getLine().equals(line)
                && ((StationCode)other).getStationNumber().equals(stationNumber);
    }
}
