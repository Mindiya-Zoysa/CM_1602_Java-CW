import java.util.Scanner;
import java.util.List;

public class Grid {
    private RobotState.Orientation robotInitialOrientation;

    // Constructor to initialize robot's initial position and orientation
    public Grid(RobotState.Orientation robotInitialOrientation) {
        this.robotInitialOrientation = robotInitialOrientation;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask the user for grid dimensions
        System.out.print("Enter height of the grid: ");
        int length = scanner.nextInt();
        System.out.print("Enter length of the grid: ");
        int height = scanner.nextInt();

        // Ask the user for the robot's initial position
        System.out.print("Enter initial x-coordinate for the robot: ");
        int initialX = scanner.nextInt();
        System.out.print("Enter initial y-coordinate for the robot: ");
        int initialY = scanner.nextInt();

        // Ask the user for the robot's initial orientation
        System.out.print("Enter initial orientation for the robot (NORTH, EAST, SOUTH, WEST): ");
        String initialOrientationStr = scanner.next();
        RobotState.Orientation initialOrientation = RobotState.Orientation.valueOf(initialOrientationStr.toUpperCase());

        // Creating an instance of StopWatch
        TimeKeeper timeKeeper = new TimeKeeper();

        // Starting the stopwatch
        timeKeeper.start();

        AStar astar = new AStar(length, height, initialX, initialY);

        System.out.println("Initial Grid:");
        astar.printInitialGrid();

        List<Node> path = astar.findPath(initialX, initialY, length - 1, height - 1); // Start at (initialX, initialY) and end at (width-1, height-1)
        if (path != null) {
            // Print the path in the desired format
            StringBuilder pathString = new StringBuilder();
            for (int i = 0; i < path.size(); i++) {
                Node node = path.get(i);
                pathString.append("-> (").append(node.x).append(", ").append(node.y).append(")");
            }
            System.out.println("Path: " + pathString.substring(0));

            System.out.println("CLI Grid:");
            // Print grid with path
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < height; j++) {
                    int finalI = i;
                    int finalJ = j;
                    if (path.stream().anyMatch(node -> node.x == finalI && node.y == finalJ)) {
                        System.out.print("* "); // Path
                    } else if (astar.grid[i][j] == 1) {
                        System.out.print("# "); // Obstacle
                    } else {
                        System.out.print(". "); // Empty cell
                    }
                }
                System.out.println();
            }
        } else {
            System.out.println("No path found.");
        }

        // Stopping the stopwatch after executing the code
        timeKeeper.stop();

        // Printing the elapsed time
        System.out.println("Execution Time: " + timeKeeper.executionTime() + " milliseconds.");
    }
}

