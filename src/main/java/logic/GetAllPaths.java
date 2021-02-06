package main.java.logic;

import main.java.model.Station;
import main.java.utils.Constants;
import main.java.utils.Pair;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class GetAllPaths {
    private static int shortest = (int) (Integer.MAX_VALUE / Constants.minLengthMultiplier);

    private static final GetAllPaths Instance = new GetAllPaths();
    private GetAllPaths() {}

    private Station origin;
    private Station destination;
    private DateTime startTime;

    public static GetAllPaths getInstance() {
        return Instance;
    }

    public static void init(Station origin, Station destination, DateTime startTime) {
        Instance.origin = origin;
        Instance.destination = destination;
        Instance.startTime = startTime;
    }

    public static List<Pair<List<Station>, DateTime>> getAllPaths(
            List<Station> allStations) {
        boolean[] visited = new boolean[allStations.size()];
        Stack<Station> currentPath = new Stack<>();
        List<Pair<List<Station>, DateTime>>simplePaths = new ArrayList<>();
        DFS(allStations, currentPath, simplePaths, visited, null, Instance.origin, Instance.startTime);
        return simplePaths
                .stream()
                .filter(path -> Minutes.minutesBetween(getInstance().startTime, path.getSecond()).getMinutes()
                        < shortest * Constants.minLengthMultiplier)
                .collect(Collectors.toList());
    }

    private static void DFS(List<Station> allStations,
                            Stack<Station> currentPath,
                            List<Pair<List<Station>, DateTime>> simplePaths,
                            boolean[] visited,
                            Station previous,
                            Station current,
                            DateTime currentTime) {

        // quick return heuristic: double length of current shortest path
        if (Minutes.minutesBetween(Instance.startTime, currentTime).getMinutes()
                > shortest * Constants.minLengthMultiplier) {
            return;
        }

        int indexOfCurrent = allStations.indexOf(current);

        visited[indexOfCurrent] = true;
        currentPath.push(current);

        if (current.equals(Instance.destination)) {
            simplePaths.add(new Pair<>(new ArrayList<>(currentPath), currentTime));
            shortest = Math.min(Minutes.minutesBetween(Instance.startTime, currentTime).getMinutes(), shortest);
            visited[indexOfCurrent] = false;
            currentPath.pop();
            return;
        }

        for (Station station : current.getNeighbours()) {
            if (!visited[allStations.indexOf(station)]
                    && !currentPath.contains(station)
                    && !station.equals(previous)
                    // prevent switching more than once
                    && !station.getNeighbours().contains(previous)) {
                int travelTime = Constants.TravelTimes.getTravelTime(current.getStationCode(),
                        station.getStationCode(),
                        currentTime);
                // Invalid movement at this time
                if (travelTime == -1) {
                    continue;
                }
                DateTime nextTime = currentTime.plusMinutes(travelTime);
                DFS(allStations, currentPath, simplePaths, visited, current, station, nextTime);
            }
        }
        currentPath.pop();
        visited[indexOfCurrent] = false;
    }
}
