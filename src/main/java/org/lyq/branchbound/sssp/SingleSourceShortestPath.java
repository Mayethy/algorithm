package org.lyq.branchbound.sssp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * ClassName: SingleSourceShortestPath
 * Package: org.lyq.branchbound.sssp
 * Description:
 *
 * @author 林宁
 * 2024/11/24 20:37
 */
public class SingleSourceShortestPath {

    // Dijkstra Algorithm
    public int[] dijkstra(int[][] graph, int source) {
        int n = graph.length;
        int[] dist = new int[n]; // Store shortest path distances
        boolean[] visited = new boolean[n]; // Mark visited nodes

        // Initialize distances to infinity
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0; // Distance to source is 0

        for (int i = 0; i < n; i++) {
            // Find the unvisited node with the smallest distance
            int u = -1;
            int minDist = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && dist[j] < minDist) {
                    u = j;
                    minDist = dist[j];
                }
            }

            if (u == -1) break; // Graph might be disconnected
            visited[u] = true;

            // Update distances for neighbors of u
            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0 && !visited[v]) { // Edge exists and not visited
                    dist[v] = Math.min(dist[v], dist[u] + graph[u][v]);
                }
            }
        }

        return dist;
    }

    // Branch and Bound Method
    public int[] branchAndBound(int[][] graph, int source) {
        int n = graph.length;
        int[] dist = new int[n]; // Store shortest path distances
        boolean[] visited = new boolean[n]; // Mark visited nodes
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // Priority queue sorted by path cost (shortest first)
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));
        pq.offer(new Node(source, 0)); // Add source to the queue

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            if (visited[u]) continue; // Skip if already visited
            visited[u] = true;

            // Update distances for neighbors of u
            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0 && !visited[v]) { // Edge exists and not visited
                    int newDist = dist[u] + graph[u][v];
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        pq.offer(new Node(v, newDist)); // Add new path to the queue
                    }
                }
            }
        }

        return dist;
    }

    // Node class for Branch and Bound
    static class Node {
        int vertex; // Node index
        int cost;   // Path cost from the source

        public Node(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Adjacency matrix representation of the graph
        int[][] graph = {
                {0, 10, 0, 30, 100},
                {10, 0, 50, 0, 0},
                {0, 50, 0, 20, 10},
                {30, 0, 20, 0, 60},
                {100, 0, 10, 60, 0}
        };
        int source = 0; // Source node

        // Create an instance of the class
        SingleSourceShortestPath sssp = new SingleSourceShortestPath();

        // Solve using Dijkstra's Algorithm
        System.out.println("Shortest paths using Dijkstra's Algorithm:");
        int[] dijkstraDistances = sssp.dijkstra(graph, source);
        for (int i = 0; i < dijkstraDistances.length; i++) {
            System.out.printf("Shortest path from node %d to node %d: %d\n", source, i, dijkstraDistances[i]);
        }

        // Solve using Branch and Bound
        System.out.println("\nShortest paths using Branch and Bound:");
        int[] branchAndBoundDistances = sssp.branchAndBound(graph, source);
        for (int i = 0; i < branchAndBoundDistances.length; i++) {
            System.out.printf("Shortest path from node %d to node %d: %d\n", source, i, branchAndBoundDistances[i]);
        }
    }
}