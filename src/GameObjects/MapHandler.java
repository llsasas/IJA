package GameObjects;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MapHandler {

    public static void saveMap(String fileName, Maze maze) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write("MapWidth: " + maze.WIDTH + "\n");
            writer.write("MapHeight: " + maze.HEIGHT + "\n");


            writer.write("NumAutonomousRobots: " + maze.robots.length + "\n");
            for (AutonomousRobot robot : maze.robots) {
                writer.write("AutonomousRobot: " + robot.getX() + "," + robot.getY() + "," + robot.angle + "," + robot.rangle + "," + robot.distance + "\n");
            }

            if (maze.crobot != null) {
                ControlledRobot crobot = maze.crobot;
                writer.write("ControlledRobot: " + crobot.getX() + "," + crobot.getY() + "," + crobot.angle + "," + crobot.rangle + "," + crobot.distance + "\n");
            }

            System.out.println("Map successfully saved to: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving map to file: " + e.getMessage());
        }
    }

    public static Maze loadMap(String fileName) {
        Maze maze = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int width = 0;
            int height = 0;
            int num = 0;
            int cnt = 0;
            AutonomousRobot[] robots = new AutonomousRobot[0];
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("MapWidth:")) {
                    width = Integer.parseInt(line.substring(9).trim());
                } else if (line.startsWith("MapHeight:")) {
                    height = Integer.parseInt(line.substring(10).trim());
                    maze = new Maze(height, width);
                } else if (line.startsWith("NumAutonomousRobots:")) {
                    num = Integer.parseInt(line.substring(20).trim());
                    robots = new AutonomousRobot[num];
                } else if (line.startsWith("AutonomousRobot:")) {
                    String[] parts = line.substring(17).split(",");
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    int angle = Integer.parseInt(parts[2]);
                    int rangle = Integer.parseInt(parts[3]);
                    double distance = Double.parseDouble(parts[4]);
                    if(cnt != num)
                    {
                        robots[cnt] = new AutonomousRobot(x,y,angle,rangle,maze,distance);
                    }
                } else if (line.startsWith("ControlledRobot:")) {
                    String[] parts = line.substring(16).split(",");
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    int angle = Integer.parseInt(parts[2]);
                    int rangle = Integer.parseInt(parts[3]);
                    double distance = Double.parseDouble(parts[4]);
                    maze.crobot = new ControlledRobot(x, y, angle, rangle, maze, distance);
                }
            }
            maze.robots = robots;

        } catch (IOException e) {
            System.err.println("Error loading map from file: " + e.getMessage());
        }
        return maze;
    }
}
