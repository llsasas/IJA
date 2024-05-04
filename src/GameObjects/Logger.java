package GameObjects;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private List<GameAction> moves;
    private int currentMove = 0;

    private int lastMove;

    Maze maze;
    private int robotNum;
    boolean forward = false;

    boolean backward = false;
    private final File file;

    public Logger(String filename) {
        //String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + "logs" + File.separator;
        file = new File(filename);

        try {
            if (file.createNewFile()) {
                System.getLogger(Logger.class.getName()).log(System.Logger.Level.INFO, "Created new log file: " + file.getName());
            }
        } catch (Exception e) {
            System.getLogger(Logger.class.getName()).log(System.Logger.Level.ERROR, "Failed to create log file: " + filename + "\n" + e.getMessage());
        }
    }

    public Logger(String log, Maze maze) {
        this.maze = maze;
        moves = new ArrayList<>();
        this.file = new File(log);
        System.getLogger(Logger.class.getName()).log(System.Logger.Level.INFO, "Replaying log file: " + file.getName());
    }

    public void log(String s) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            pw.print(s);
        } catch (Exception e) {
            System.getLogger(Logger.class.getName()).log(System.Logger.Level.ERROR, "Failed to write to log file: " + file.getName() + "\n" + e.getMessage());
        }
    }

    public void parseLog() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            AutonomousRobot robots[] = new AutonomousRobot[0];
            int count = 0;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("N:"))
                {
                    String ns = line.substring(2);
                    int number = Integer.parseInt(ns);
                    robots = new AutonomousRobot[number];
                }
                else if (line.startsWith("Ai:")) {
                    String[] parts = line.substring(3).split(",");
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    int angle = Integer.parseInt(parts[2]);
                    int rangle = Integer.parseInt(parts[3]);
                    double distance = Double.parseDouble(parts[4]);
                    robots[count] = new AutonomousRobot(x,y,angle,rangle, maze,distance);
                    count++;
                } else if (line.startsWith("Ci:")) {
                    String[] parts = line.substring(3).split(",");
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    int angle = Integer.parseInt(parts[2]);
                    int rangle = Integer.parseInt(parts[3]);
                    double distance = Double.parseDouble(parts[4]);
                    maze.crobot = new ControlledRobot(x,y,angle,rangle,maze,distance);
                    count++;
                } else {
                    break;
                }
            }

            robotNum = count;
            count = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("A:")) {
                    String[] parts = line.substring(2).split(",");
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    int angle = Integer.parseInt(parts[2]);
                    addMove(x,y,angle,count);
                    count++;
                } else if (line.startsWith("C:")) {
                    String[] parts = line.substring(2).split(",");
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    int angle = Integer.parseInt(parts[2]);
                    addMoveC(x,y,angle);
                    count++;
                }
                else if (line.equals("")) {
                    count = 0;
                }
            }
        } catch (IOException e) {
            System.getLogger(Logger.class.getName()).log(System.Logger.Level.ERROR, "Failed to parse log file: " + file.getName() + "\n" + e.getMessage());
        }
    }

    public void setMaze(Maze maze)
    {
        this.maze = maze;
    }
    public void setForward()
    {
        backward = false;
        forward = true;

    }

    public void setBackward()
    {
        forward = false;
        backward = true;
    }

    public void setPause()
    {
        forward = false;
        backward = false;
    }
    public void addMove(double x, double y, int angle, int id)
    {
        GameAction move = new GameAction(maze.robots[id], x,y,angle);
        moves.add(move);
    }

    public void addMoveC(double x, double y, int angle)
    {
    GameAction move = new GameAction(maze.crobot, x,y,angle);
    moves.add(move);

    }

    public void updateObjects()
    {
        if(forward) {
            for (int i = 0; i < robotNum; i++) {
                GameAction move = moves.get(currentMove);
                Robot r = move.getObject();
                r.updateLog(move.getX(),move.getY());
                r.setCenterX(move.getX());
                r.setCenterY(move.getY());
                r.setAngle(move.getAngle());
                currentMove++;
                if(currentMove == moves.size())
                {
                    forward = false;
                    currentMove --;
                }
            }
        }
        else if (backward)
        {
            for (int i = 0; i < robotNum; i++) {
                GameAction move = moves.get(currentMove);
                Robot r = move.getObject();
                r.updateLog(move.getX(),move.getY());
                r.setCenterX(move.getX());
                r.setCenterY(move.getY());
                r.setAngle(move.getAngle());
                currentMove--;
                if(currentMove == 0)
                {
                    backward = false;
                }
            }
        }
    }
}
