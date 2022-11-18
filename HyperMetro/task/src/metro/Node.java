package metro;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Node {
    private StationWithLine value;

    public void setNeighbors(Set<Node> neighbors) {
        this.neighbors = neighbors;
    }

    private Set<Node> neighbors;


    public StationWithLine getValue() {
        return value;
    }

    public void setValue(StationWithLine value) {
        this.value = value;
    }

    public Set<Node> getNeighbors() {
        return neighbors;
    }

    public Node(StationWithLine value) {
        this.value = value;
        this.neighbors = new HashSet<>();
    }

    public void connect(Node node) {
        if (this == node) throw new IllegalArgumentException("Can't connect node to itself");
        this.neighbors.add(node);
        node.neighbors.add(this);
    }

    @Override
    public java.lang.String toString() {
        return "Node{" +
                "value=" + value +neighbors.size()+



                '}';
    }
}