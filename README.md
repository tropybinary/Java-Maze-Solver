# Java-Maze-Solver
Java Maze Generator and Solver with Pathfinding  
Maze Generators: Recursive Backtracking, Kruskal's Algorithm, Aldous-Broder  
Pathfinders (Maze Solvers): A* Search Algorithm  

You can use it this way:
```
int[][] maze1 = new RecursiveBacktrackingMazeGenerator(width, height).generateMaze();  
int[][] maze2 = new KruskalsMazeGenerator(width, height).generateMaze();  
int[][] maze3 = new AldousBroderMazeGenerator(width, height).generateMaze();
AStarAlgorithm algorithm = new AStarAlgorithm();
int[][] solve1 = algorithm.findPath(new Node(startX, startY), new Node(endX, endY), maze1);
int[][] solve2 = algorithm.findPath(new Node(startX, startY), new Node(endX, endY), maze2);
int[][] solve3 = algorithm.findPath(new Node(startX, startY), new Node(endX, endY), maze3);
```


![Maze Solver](./images/mazesolver.PNG)

