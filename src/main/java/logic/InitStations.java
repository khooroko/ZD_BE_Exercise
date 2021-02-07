package main.java.logic;

import main.java.model.Station;
import main.java.utils.Errors;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InitStations {
    //Delimiter used in the CSV file
    private static final String COMMA_DELIMITER = ",";

    public static List<Station> initStations(InputStream is) throws IOException {
        List<Station> stations = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";
            br.readLine();
            String lastLine = "";
            while ((line = br.readLine()) != null)
            {
                String[] stationDetails = line.split(COMMA_DELIMITER);
                if (stationDetails.length != 3) {
                    throw new IllegalArgumentException(Errors.ERR_FAILED_TO_PARSE);
                }
                String[] splitDetails = stationDetails[0].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
                String trainLine = splitDetails[0];
                String stationNumber = splitDetails[1];
                String stationName = stationDetails[1];
                DateTime openDateTime = DateTime.now();
                try {
                    // date month year
                    DateTimeFormatter dateTimeFormatter =
                            DateTimeFormat.forPattern("dd MMMM yyyy").withLocale(Locale.ENGLISH);
                    openDateTime =
                            dateTimeFormatter.parseLocalDate(stationDetails[2]).toDateTimeAtStartOfDay();
                } catch (IllegalArgumentException e) {
                    try {
                        // date month
                        DateTimeFormatter dateTimeFormatter =
                                DateTimeFormat.forPattern("MMMM yyyy").withLocale(Locale.ENGLISH);
                        openDateTime =
                                dateTimeFormatter.parseLocalDate(stationDetails[2]).toDateTimeAtStartOfDay();
                    } catch (IllegalArgumentException e2) {
                        System.out.println(String.format("Unable to parse date for %s%s." +
                                        " Using current time as date opened.",
                                trainLine, stationNumber));
                    }
                }

                Station station = new Station(trainLine, stationNumber, stationName, openDateTime);

                // add neighbours based on name
                for (Station prevStation: stations) {
                    if (prevStation.getName().equals(stationName)) {
                        prevStation.addNeighbour(station);
                        station.addNeighbour(prevStation);
                    }
                }

                // add neighbours based on line
                if (trainLine.equals(lastLine)) {
                    Station prevStation = stations.get(stations.size() - 1);
                    prevStation.addNeighbour(station);
                    station.addNeighbour(prevStation);
                } else {
                    lastLine = trainLine;
                }
                stations.add(station);
            }
        }
        catch(Exception ee)
        {
            throw(ee);
        }
        finally
        {
            try
            {
                br.close();
            }
            catch(IOException ie)
            {
                System.out.println("Error occurred while closing the BufferedReader");
                ie.printStackTrace();
            }
        }
        return stations;
    }
}
