package com.troopybinary.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AStarAlgorithm implements PathfindingAlgorithm {
	
    @Override
    public int[][] findPath(Node start, Node end, int[][] maze) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getFCost));
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Double> gCost = new HashMap<>();
        Map<Node, Double> fCost = new HashMap<>();

        gCost.put(start, 0.0);
        fCost.put(start, heuristic(start, end));
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(end)) {
                return reconstructPath(cameFrom, current);
            }

            for (Node neighbor : getNeighbors(current, maze)) {
                double tentativeGCost = gCost.getOrDefault(current, Double.MAX_VALUE) + 1;

                if (tentativeGCost < gCost.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gCost.put(neighbor, tentativeGCost);
                    fCost.put(neighbor, tentativeGCost + heuristic(neighbor, end));

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null;
    }

    private double heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private int[][] reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<int[]> path = new ArrayList<>();
        while (current != null) {
            path.add(new int[]{current.x, current.y});
            current = cameFrom.get(current);
        }
        Collections.reverse(path);

        int[][] result = new int[path.size()][2];
        for (int i = 0; i < path.size(); i++) {
            result[i] = path.get(i);
        }
        return result;
    }

    private List<Node> getNeighbors(Node node, int[][] maze) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int nx = node.x + dir[0];
            int ny = node.y + dir[1];

            if (nx >= 0 && nx < maze.length && ny >= 0 && ny < maze[0].length && maze[nx][ny] != 1) {
                neighbors.add(new Node(nx, ny));
            }
        }

        return neighbors;
    }
}
