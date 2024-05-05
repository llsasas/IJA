/**
 * @file MapHandler.java
 * @brief Class used for loading and storing map
 * @author Samuel Čus, xcussa00
 */
package Robots.Controllers;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import Robots.GameObjects.Maze;
import Robots.GameObjects.ControlledRobot;
import Robots.GameObjects.AutonomousRobot;
import Robots.GameObjects.Obstacle;
public class MapHandler {

    public static void saveMap(String fileName, Maze maze) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write("MapWidth: " + maze.WIDTH + "\n");
            writer.write("MapHeight: " + maze.HEIGHT + "\n");

            writer.write("NumObstacles: " + maze.obstacles.length + "\n");
            for (Obstacle obstacle: maze.obstacles) {
                writer.write("Obstacle: " + obstacle.CenterX() + "," + obstacle.CenterY() +"\n");
            }
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
            int height;
            int num = 0;
            int cnt = 0;
            int num1 = 0;
            int cnt1 = 0;
            AutonomousRobot[] robots = new AutonomousRobot[0];
            Obstacle[] obstacles = new Obstacle[0];
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("MapWidth:")) {
                    width = Integer.parseInt(line.substring(9).trim());
                } else if (line.startsWith("MapHeight:")) {
                    height = Integer.parseInt(line.substring(10).trim());
                    maze = new Maze(width, height);
                } else if (line.startsWith("NumObstacles:")) {
                    num1 = Integer.parseInt(line.substring(13).trim());
                    obstacles = new Obstacle[num1];
                } else if (line.startsWith("Obstacle:")) {
                    String[] parts = line.substring(9).split(",");
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    if(cnt1 <= num1-1)
                    {
                        obstacles[cnt1] = new Obstacle(x,y);
                        cnt1 ++;
                    }
                }else if (line.startsWith("NumAutonomousRobots:")) {
                    num = Integer.parseInt(line.substring(20).trim());
                    robots = new AutonomousRobot[num];
                } else if (line.startsWith("AutonomousRobot:")) {
                    String[] parts = line.substring(17).split(",");
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    int angle = Integer.parseInt(parts[2]);
                    int rangle = Integer.parseInt(parts[3]);
                    double distance = Double.parseDouble(parts[4]);
                    if(cnt <= num-1)
                    {
                        robots[cnt] = new AutonomousRobot(x,y,angle,rangle,maze,distance);
                        cnt ++;
                    }
                } else if (line.startsWith("ControlledRobot:")) {
                    String[] parts = line.substring(16).split(",");
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    int angle = Integer.parseInt(parts[2]);
                    int rangle = Integer.parseInt(parts[3]);
                    double distance = Double.parseDouble(parts[4]);
                    assert maze != null;
                    maze.crobot = new ControlledRobot(x, y, angle, rangle, maze, distance);
                }
            }
            if (maze != null) {
                maze.obstacles = obstacles;
            }
            assert maze != null;
            maze.robots = robots;

        } catch (IOException e) {
            System.err.println("Error loading map from file: " + e.getMessage());
        }
        return maze;
    }
}
