package metro;

import java.util.*;

public class Graph {

    public Graph() {
    }

    public static void calculateShortestPathFromSource( NodeDij source) {
        source.setDistance(0);

        Set<NodeDij> settledNodes = new HashSet<>();
        Set<NodeDij> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            NodeDij currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry< NodeDij, Integer> adjacencyPair:
                    currentNode.getAdjacentNodes().entrySet()) {
                NodeDij adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {

                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }

    }

    private static NodeDij getLowestDistanceNode(Set < NodeDij > unsettledNodes) {
        NodeDij lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (NodeDij node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(NodeDij evaluationNode,
                                                 Integer edgeWeigh, NodeDij sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<NodeDij> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}