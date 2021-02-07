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

public class PathGetter {
    private static int shortest = (int) (Integer.MAX_VALUE / Constants.minLengthMultiplier);

    private static final PathGetter Instance = new PathGetter();
    private PathGetter() {}

    private static List<Station> allStations;
    private static Station origin;
    private static Station destination;
    private static DateTime startTime;

    public static PathGetter getInstance() {
        return Instance;
    }

    public static void init(List<Station> allStations) {
        PathGetter.allStations = allStations;
    }

    public static List<Pair<List<Station>, DateTime>> getAllPaths(
            Station origin, Station destination, DateTime startTime) {
        PathGetter.origin = origin;
        PathGetter.destination = destination;
        PathGetter.startTime = startTime;
        boolean[] visited = new boolean[allStations.size()];
        Stack<Station> currentPath = new Stack<>();
        List<Pair<List<Station>, DateTime>>simplePaths = new ArrayList<>();
        DFS(currentPath, simplePaths, visited, null, origin, startTime);
        return simplePaths
                .stream()
                .filter(path -> Minutes.minutesBetween(PathGetter.startTime, path.getSecond()).getMinutes()
                        < shortest * Constants.minLengthMultiplier)
                .collect(Collectors.toList());
    }

    private static void DFS(Stack<Station> currentPath,
                            List<Pair<List<Station>, DateTime>> simplePaths,
                            boolean[] visited,
                            Station previous,
                            Station current,
                            DateTime currentTime) {

        // quick return heuristic: multiplier of current shortest path
        if (Minutes.minutesBetween(startTime, currentTime).getMinutes()
                > shortest * Constants.minLengthMultiplier) {
            return;
        }

        int indexOfCurrent = allStations.indexOf(current);

        visited[indexOfCurrent] = true;
        currentPath.push(current);

        if (current.equals(destination)) {
            simplePaths.add(new Pair<>(new ArrayList<>(currentPath), currentTime));
            shortest = Math.min(Minutes.minutesBetween(startTime, currentTime).getMinutes(), shortest);
            visited[indexOfCurrent] = false;
            currentPath.pop();
            return;
        }

        for (Station station : current.getNeighbours()) {
            if (!visited[allStations.indexOf(station)]
                    && !currentPath.contains(station)
                    && !station.equals(previous)
                    // prevent switching more than once
                    && !station.getNeighbours().contains(previous)
                    // do not add stations that yet not opened
                    && station.getOpenDateTime().isBefore(currentTime)) {
                int travelTime = Constants.TravelTimes.getTravelTime(current.getStationCode(),
                        station.getStationCode(),
                        currentTime);
                // Invalid movement at this time
                if (travelTime == -1) {
                    continue;
                }
                DateTime nextTime = currentTime.plusMinutes(travelTime);
                DFS(currentPath, simplePaths, visited, current, station, nextTime);
            }
        }
        currentPath.pop();
        visited[indexOfCurrent] = false;
    }
}
