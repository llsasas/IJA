package GameObjects;

public class Game {

    private Logger logger;
    private Maze maze;

    Game(Logger logger, Maze maze)
    {
        this.logger = logger;
        this.maze = maze;
    }
    private boolean replay = false;

    public Maze getMaze()
    {
        return maze;
    }

    public Logger getLogger()
    {
        return logger;
    }

}
