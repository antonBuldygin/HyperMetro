package metro;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NodeDij {
    private StationWithLine name;
    private List<NodeDij> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    private Map<NodeDij, Integer> adjacentNodes = new HashMap<>();


    public NodeDij(StationWithLine name) {
        this.name = name;
    }

    public void setAdjacentNodes(Map<NodeDij, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public void addDestination(NodeDij destination, int distance) {
        if (this == destination) throw new IllegalArgumentException("Can't connect node to itself");
        adjacentNodes.put(destination, distance);
        destination.getAdjacentNodes().put(this, distance);
    }


    public StationWithLine getName() {
        return name;
    }

    public void setName(StationWithLine name) {
        this.name = name;
    }

    public List<NodeDij> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<NodeDij> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<NodeDij, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    @Override
    public String toString() {
        return "NP{" +
                "name=" + name +
                ", shortestPath=" + shortestPath +
                ", distance=" + distance+
                "}";
    }
}
