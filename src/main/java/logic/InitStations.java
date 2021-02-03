package main.java.logic;

import main.java.model.Station;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InitStations {
    //Delimiter used in the CSV file
    private static final String COMMA_DELIMITER = ",";

    public static void initStations(ArrayList<Station> stations, String dir) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dir));
            String line = "";
            br.readLine();
            String lastLine = "";
            while ((line = br.readLine()) != null)
            {
                String[] stationDetails = line.split(COMMA_DELIMITER);
                if (stationDetails.length > 0)
                {
                    String[] splitDetails = stationDetails[0].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
                    String trainLine = splitDetails[0];
                    String stationNumber = splitDetails[1];
                    String stationName = stationDetails[1];

                    Station station = new Station(trainLine, stationNumber, stationName);

                    // add neighbours based on name
                    for (Station prevStation: stations) {
                        if (prevStation.getName().equals(stationName)) {
                            prevStation.addNeighbour(station, 10);
                            station.addNeighbour(prevStation, 10);
                        }
                    }

                    // add neighbours based on line
                    if (trainLine.equals(lastLine)) {
                        // TODO: add time properly
                        Station prevStation = stations.get(stations.size() - 1);
                        prevStation.addNeighbour(station, 10);
                        station.addNeighbour(prevStation, 10);
                    } else {
                        lastLine = trainLine;
                    }
                    stations.add(station);
                }
            }
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
        }
        finally
        {
            try
            {
                br.close();
            }
            catch(IOException ie)
            {
                System.out.println("Error occured while closing the BufferedReader");
                ie.printStackTrace();
            }
        }
        return;
    }
}
