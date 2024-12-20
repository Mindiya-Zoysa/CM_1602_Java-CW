import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Random;

class Node implements Comparable<Node> {
    int x, y;
    int g, h;
    Node parent;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(g + h, o.g + o.h);
    }
}

public class AStar {
    int[][] grid;
    private int length;
    private int height;
    private int robotInitialX;
    private int robotInitialY;
    private Node[][] nodes;

    // Constructor to initialize the grid with given dimensions and robot's initial position
    public AStar(int length, int height, int robotInitialX, int robotInitialY) {
        this.length = length;
        this.height = height;
        this.robotInitialX = robotInitialX;
        this.robotInitialY = robotInitialY;
        this.grid = new int[length][height];
        this.nodes = new Node[length][height];
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                nodes[x][y] = new Node(x, y);
            }
        }
        generateRandomBlockPaths();
    }

    // Method to generate obstacles randomly by using the density
    private void generateRandomBlockPaths() {
        Random random = new Random();
        int numBlockPaths = (int) (length * height * 0.2); // The density of block paths
        for (int i = 0; i < numBlockPaths; i++) {
            int x = random.nextInt(length);
            int y = random.nextInt(height);
            grid[x][y] = 1; // Mark as block path
        }
    }

    // Method to display the grid with initial robot position marked as "S" and destination marked as "D"
    public void printInitialGrid() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                if (i == robotInitialX && j == robotInitialY) {
                    System.out.print("S "); // "S" is to show the starting point of the robot
                } else if (j == height - 1 && i == length - 1) {
                    System.out.print("D "); // "D" is to show the destination of the robot
                } else if (grid[i][j] == 1) {
                    System.out.print("# ");// #  for obstacle
                } else {
                    System.out.print(". "); // . for empty cell
                }
            }
            System.out.println();
        }
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int newX = node.x + dir[0];
            int newY = node.y + dir[1];
            if (newX >= 0 && newX < length && newY >= 0 && newY < height && grid[newX][newY] == 0) {
                neighbors.add(nodes[newX][newY]);
            }
        }
        return neighbors;
    }

    public List<Node> findPath(int startX, int startY, int endX, int endY) {
        Node startNode = nodes[startX][startY];
        Node endNode = nodes[endX][endY];
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current == endNode) {
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                reverseList(path); // Reversing the path
                return path;
            }

            closedSet.add(current);
            for (Node neighbor : getNeighbors(current)) {
                if (closedSet.contains(neighbor))
                    continue;

                int tentativeGScore = current.g + 1;
                if (!openSet.contains(neighbor) || tentativeGScore < neighbor.g) {
                    neighbor.parent = current;
                    neighbor.g = tentativeGScore;
                    neighbor.h = Math.abs(neighbor.x - endX) + Math.abs(neighbor.y - endY);
                    if (!openSet.contains(neighbor))
                        openSet.add(neighbor);
                }
            }
        }
        return null;
    }

    // Method to reverse a list
    private <T> void reverseList(List<T> list) {
        int size = list.size();
        int mid = size / 2;
        for (int i = 0; i < mid; i++) {
            swap(list, i, size - i - 1);
        }
    }

    private <T> void swap(List<T> list, int i, int j) {
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }
}
