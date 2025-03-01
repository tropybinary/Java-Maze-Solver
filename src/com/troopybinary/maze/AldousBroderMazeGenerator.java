package com.troopybinary.maze;

public class AldousBroderMazeGenerator extends MazeGenerator{

    public AldousBroderMazeGenerator(int width, int height) {
    	super(width, height);
    }
    
    @Override
    public int[][] generateMaze() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = 1;
            }
        }

        int x = random.nextInt(height);
        int y = random.nextInt(width);
        maze[x][y] = 0;

        int totalCells = (height / 2) * (width / 2);
        int visitedCells = 1;

        while (visitedCells < totalCells) {
            int[][] directions = {{-2, 0}, {2, 0}, {0, -2}, {0, 2}};
            int[] dir = directions[random.nextInt(4)];
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx >= 0 && nx < height && ny >= 0 && ny < width) {
                if (maze[nx][ny] == 1) {
                    maze[nx][ny] = 0;
                    maze[(x + nx) / 2][(y + ny) / 2] = 0;
                    visitedCells++;
                }
                x = nx;
                y = ny;
            }
        }

        return maze;
    }
}