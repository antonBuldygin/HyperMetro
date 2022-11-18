package metro;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {


    // minimum distance between start and finish nodes using BFS
    public int BFS(int start, int finish) {
        // array to hold vertices already visited
        boolean[] visited = new boolean[adjacencyList.length];
        //parent array holds value of vertex which introduced this vertex
        int[] parent = new int[adjacencyList.length];
        Queue<Integer> q = new LinkedList<>();

        q.add(start);
        parent[start] = -1;
        visited[start] = true;
        while (!q.isEmpty()) {
            // pull out the first vertex in the queue
            int current = q.poll();
            // if the vertex pulled is the last one, break loop
            if (current == finish) break;

            for (int neighbour: adjacencyList[current]) {
                // if neighbour hasn't been visited
                if(!visited[neighbour]) {
                    visited[neighbour] = true;
                    // add to the queue
                    q.add(neighbour);
                    // since current vertex introduced us
                    parent[neighbour] = current;
                }
            }
        }
        //traverse through the parent array
        int current = finish;
        int distance = 0;
        while (parent[current] != -1) {
            System.out.print(current + "-> ");
            current = parent[current];
            distance++;
        }
        System.out.print(start);
        return distance;
    }
    private LinkedList<Integer> adjacencyList[];
    public BFS (int vertex) {
        adjacencyList = new LinkedList[vertex];
        for (int i = 0; i < vertex; i++) {
            adjacencyList[i] = new LinkedList<Integer>();
        }
    }
    public void addEdge(int start, int finish) {
        adjacencyList[start].add(finish);
        adjacencyList[finish].add(start);
    }
}
