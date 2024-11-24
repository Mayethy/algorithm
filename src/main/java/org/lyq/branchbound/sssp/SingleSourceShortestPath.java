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
        int[] dist = new int[n]; // 存储最短路径
        boolean[] visited = new boolean[n]; // 标记是否访问过

        // 初始化距离为正无穷
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0; // 源点到自身的距离为 0

        for (int i = 0; i < n; i++) {
            // 找到未访问中距离最小的顶点
            int u = -1;
            int minDist = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && dist[j] < minDist) {
                    u = j;
                    minDist = dist[j];
                }
            }

            if (u == -1) break; // 图可能不连通
            visited[u] = true;

            // 更新 u 的邻居节点的距离
            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0 && !visited[v]) { // 有边且未访问
                    dist[v] = Math.min(dist[v], dist[u] + graph[u][v]);
                }
            }
        }

        return dist;
    }

    // Branch and Bound Method
    public int[] branchAndBound(int[][] graph, int source) {
        int n = graph.length;
        int[] dist = new int[n]; // 存储最短路径
        boolean[] visited = new boolean[n]; // 标记是否访问过
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // 优先队列，按路径长度排序（最短路径优先）
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));
        pq.offer(new Node(source, 0)); // 将源点加入队列

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            if (visited[u]) continue; // 如果已经访问过，跳过
            visited[u] = true;

            // 更新 u 的邻居节点的距离
            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0 && !visited[v]) { // 有边且未访问
                    int newDist = dist[u] + graph[u][v];
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        pq.offer(new Node(v, newDist)); // 将新路径加入队列
                    }
                }
            }
        }

        return dist;
    }

    // 定义节点类
    static class Node {
        int vertex; // 节点编号
        int cost;   // 距离源点的路径长度

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