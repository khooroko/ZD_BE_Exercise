package main.java.logic;

import main.java.model.Station;
import main.java.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class GetAllPaths {
    static int shortest = Integer.MAX_VALUE / 2;

    public static List<List<Station>> getAllPaths(
            List<Station> allStations, Station origin, Station destination) {
        boolean[] visited = new boolean[allStations.size()];
        Stack<Station> currentPath = new Stack<>();
        ArrayList<ArrayList<Station>> simplePaths = new ArrayList<>();
        DFS(allStations, currentPath, simplePaths, visited, null, origin, destination);
        return simplePaths.stream().filter(path -> path.size() < shortest * Constants.minLengthMultiplier)
                .collect(Collectors.toList());
    }

    private static void DFS(List<Station> allStations,
                            Stack<Station> currentPath,
                            ArrayList<ArrayList<Station>> simplePaths,
                            boolean[] visited,
                            Station previous,
                            Station current,
                            Station destination) {

        // quick return heuristic: double length of current shortest path
        if (currentPath.size() > shortest * Constants.minLengthMultiplier) {
            return;
        }

        int indexOfCurrent = allStations.indexOf(current);

        visited[indexOfCurrent] = true;
        currentPath.push(current);

        if (current.equals(destination)) {
            simplePaths.add(new ArrayList(currentPath));
            shortest = Math.min(currentPath.size(), shortest);
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
                DFS(allStations, currentPath, simplePaths, visited, current, station, destination);
            }
        }
        currentPath.pop();
        visited[indexOfCurrent] = false;
    }
}
