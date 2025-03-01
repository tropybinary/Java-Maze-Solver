package com.troopybinary.maze;

import java.util.Random;

public abstract class MazeGenerator {
	
    protected int width;
    protected int height;
    protected int[][] maze;
    protected Random random;

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new int[height][width];
        this.random = new Random();
    }

    public abstract int[][] generateMaze();
}