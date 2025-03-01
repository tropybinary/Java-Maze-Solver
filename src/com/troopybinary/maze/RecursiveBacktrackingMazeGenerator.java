package com.troopybinary.maze;

import java.util.Stack;

public class RecursiveBacktrackingMazeGenerator extends MazeGenerator{

    public RecursiveBacktrackingMazeGenerator(int width, int height) {
    	super(width, height);
    }

    @Override
    public int[][] generateMaze() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = 1;
            }
        }
        int startX = random.nextInt(height);
        int startY = random.nextInt(width);
        maze[startX][startY] = 0;

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});

        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int x = current[0];
            int y = current[1];

   
            int[][] neighbors = getUnvisitedNeighbors(x, y);

            if (neighbors.length > 0) {
          
                int[] next = neighbors[random.nextInt(neighbors.length)];
                int nx = next[0];
                int ny = next[1];


                maze[nx][ny] = 0;
                maze[(x + nx) / 2][(y + ny) / 2] = 0;

                stack.push(new int[]{nx, ny});
            } else {
                stack.pop();
            }
        }

        return maze;
    }

    private int[][] getUnvisitedNeighbors(int x, int y) {
        int[][] directions = {{-2, 0}, {2, 0}, {0, -2}, {0, 2}};
        int[][] neighbors = new int[4][2];
        int count = 0;

        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx >= 0 && nx < height && ny >= 0 && ny < width && maze[nx][ny] == 1) {
                neighbors[count++] = new int[]{nx, ny};
            }
        }


        int[][] result = new int[count][2];
        System.arraycopy(neighbors, 0, result, 0, count);
        return result;
    }
}