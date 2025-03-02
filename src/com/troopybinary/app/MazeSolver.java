package com.troopybinary.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.troopybinary.maze.AldousBroderMazeGenerator;
import com.troopybinary.maze.KruskalsMazeGenerator;
import com.troopybinary.maze.MazeGenerator;
import com.troopybinary.maze.RecursiveBacktrackingMazeGenerator;
import com.troopybinary.path.AStarAlgorithm;
import com.troopybinary.path.Node;
import com.troopybinary.path.PathfindingAlgorithm;

public class MazeSolver extends JFrame {
	
    private static final long serialVersionUID = 1L;
    private int[][] maze;
    private int[][] orgMaze;
    private int[][] solutionPath;
    private JPanel mazePanel;
    private JComboBox<String> mazeGeneratorComboBox;
    private JComboBox<String> pathfinderComboBox;
    private JButton generateButton;
    private JButton solveButton;
    private JTextField widthTextField;
    private JTextField heightTextField;
    private JLabel widthLabel;
    private JLabel heightLabel;
    private int width;
    private int height;
    
    public MazeSolver() {
    	this.setTitle("Maze Generator-Solver(Pathfinding)");
    	this.setSize(900, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        this.mazeGeneratorComboBox = new JComboBox<>(new String[]{
            "Recursive Backtracking", "Kruskal's Algorithm", "Aldous-Broder"
        });

        this.pathfinderComboBox = new JComboBox<>(new String[]{"A*"});

        this.generateButton = new JButton("Generate Maze");
        this.solveButton = new JButton("Solve Maze");
        this.widthLabel = new JLabel("Width:");
        this.widthTextField = new JTextField("50", 5);
        this.widthTextField.setToolTipText("Width");
        this.heightLabel = new JLabel("Height:");
        this.heightTextField = new JTextField("50", 5);
        this.heightTextField.setToolTipText("Height");
        this.generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMaze();
            }
        });

        this.solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveMaze();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Maze Generator:"));
        controlPanel.add(mazeGeneratorComboBox);
        controlPanel.add(new JLabel("Pathfinder:"));
        controlPanel.add(pathfinderComboBox);
        controlPanel.add(generateButton);
        controlPanel.add(solveButton);
        controlPanel.add(widthLabel);
        controlPanel.add(widthTextField);
        controlPanel.add(heightLabel);
        controlPanel.add(heightTextField);
        this.add(controlPanel, BorderLayout.NORTH);

        this.mazePanel = new JPanel() {
	    private static final long serialVersionUID = 1L;

	    @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (maze != null) {
                    int cellSize = Math.min(getWidth() / maze[0].length, getHeight() / maze.length );
                    for (int i = 0; i < maze.length; i++) {
                        for (int j = 0; j < maze[i].length; j++) {
                            if (maze[i][j] == 1) {
                                g.setColor(Color.BLACK); //Wall
                            } else if (maze[i][j] == 0) {
                                g.setColor(Color.DARK_GRAY); //Path
                            } else if (maze[i][j] == 2) {
                                g.setColor(Color.BLUE); //Start Point
                            } else if (maze[i][j] == 3) {
                                g.setColor(Color.BLUE); //End Point
                            } else if (maze[i][j] == 4) {
                                g.setColor(new Color(40, 117, 30)); //Solution
                            }
                            g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                        }
                    }
                }
            }
        };
        this.add(this.mazePanel, BorderLayout.CENTER);
    }

    private void generateMaze() {
        String selectedGenerator = (String) mazeGeneratorComboBox.getSelectedItem();
        try {
             int fWidth = Integer.parseInt(widthTextField.getText());
             int fHeight = Integer.parseInt(heightTextField.getText());
             if (fWidth <= 4 || fHeight <= 4 && fWidth > 600 || fHeight > 600) {
                JOptionPane.showMessageDialog(this, "Width and height must be at least 5 and at most 600.");
                return;
             }else {
                width = fWidth;
                height = fHeight;
             }
        }catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(this, "Please enter valid numbers for width and height!");
             return;
        }
        
        MazeGenerator generator = null;
        switch (selectedGenerator) {
            case "Recursive Backtracking":
                generator = new RecursiveBacktrackingMazeGenerator(width, height);
            	break;
            case "Kruskal's Algorithm":
                generator = new KruskalsMazeGenerator(width, height);
        	break;
            case "Aldous-Broder":
        	generator = new AldousBroderMazeGenerator(width, height);
        	break;
            default:
            	JOptionPane.showMessageDialog(this, "Invalid algorithm selected!");
            	return;
        }
        maze = generator.generateMaze();

        maze[1][1] = 2; //Start Point
        maze[height - 2][width - 2] = 3; //End Point

        solutionPath = null;
        orgMaze = maze.clone();
        mazePanel.repaint();
    }

    private void solveMaze() {
    	mazePanel.repaint();
        if (maze == null) {
            JOptionPane.showMessageDialog(this, "Please generate a maze first!");
            return;
        }

        for (int i = 0; i < maze.length; i++) {
            System.arraycopy(orgMaze[i], 0, maze[i], 0, maze[i].length);
        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 4) {
                    maze[i][j] = 0;
                }
            }
        }

        int startX = -1, startY = -1;
        int endX = -1, endY = -1;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 2) {
                    startX = i;
                    startY = j;
                } else if (maze[i][j] == 3) {
                    endX = i;
                    endY = j;
                }
            }
        }

        if (startX == -1 || startY == -1 || endX == -1 || endY == -1) {
            JOptionPane.showMessageDialog(this, "Start or end point not found!");
            return;
        }

        String selectedAlgorithm = (String) pathfinderComboBox.getSelectedItem();
        PathfindingAlgorithm solver;

        switch (selectedAlgorithm) {
            case "A*":
                solver = new AStarAlgorithm();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid algorithm selected!");
                return;
        }


        solutionPath = solver.findPath(new Node(startX, startY), new Node(endX, endY), maze);

        if (solutionPath == null) {
            JOptionPane.showMessageDialog(this, "No solution found!");
        } else {

            for (int[] point : solutionPath) {
                if (maze[point[0]][point[1]] != 2 && maze[point[0]][point[1]] != 3) {
                    maze[point[0]][point[1]] = 4;
                }
            }
            mazePanel.repaint(); 
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MazeSolver app = new MazeSolver();
            app.setVisible(true);
        });
    }
}
