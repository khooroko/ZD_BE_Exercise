package main.java.model;

// Class to keep track of neighbours with time taken
public class NeighbouringStation {
    private Station neighbour;
    private int time;

    public NeighbouringStation(Station neighbour, int time) {
        this.neighbour = neighbour;
        this.time = time;
    }

    public Station getNeighbour() {
        return neighbour;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "{" + neighbour.getStationCode().toString() + ", " + time + "}" ;
    }
}
