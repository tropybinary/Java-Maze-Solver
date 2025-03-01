package com.troopybinary.maze;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class KruskalsMazeGenerator extends MazeGenerator{

    public KruskalsMazeGenerator(int width, int height) {
    	super(width, height);
    }

    @Override
    public int[][] generateMaze() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = 1;
            }
        }

        List<int[]> edges = new ArrayList<>();
        for (int i = 1; i < height - 1; i += 2) {
            for (int j = 1; j < width - 1; j += 2) {
                if (i < height - 2) edges.add(new int[]{i, j, i + 2, j});
                if (j < width - 2) edges.add(new int[]{i, j, i, j + 2});
            }
        }


        Collections.shuffle(edges, random);

      
        int[][] parent = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                parent[i][j] = i * width + j; 
            }
        }


        for (int[] edge : edges) {
            int x1 = edge[0], y1 = edge[1];
            int x2 = edge[2], y2 = edge[3];

            int root1 = find(parent, x1, y1);
            int root2 = find(parent, x2, y2);

            if (root1 != root2) {
                maze[x1][y1] = 0;
                maze[x2][y2] = 0;
                maze[(x1 + x2) / 2][(y1 + y2) / 2] = 0; 
                union(parent, x1, y1, x2, y2);
            }
        }

        return maze;
    }

    private int find(int[][] parent, int x, int y) {
        if (parent[x][y] != x * width + y) {
            parent[x][y] = find(parent, parent[x][y] / width, parent[x][y] % width);
        }
        return parent[x][y];
    }

    private void union(int[][] parent, int x1, int y1, int x2, int y2) {
        int root1 = find(parent, x1, y1);
        int root2 = find(parent, x2, y2);
        parent[root2 / width][root2 % width] = root1;
    }
}