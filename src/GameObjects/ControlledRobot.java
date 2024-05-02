package GameObjects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class ControlledRobot extends ImageView{

    public  int angle;
    public int rotangle;
    public ControlledRobot(String path, double x, double y, double radius,int angle)
    {
        super(new Image(path));
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.angle = 0;
        this.rotangle = angle;
    }
    public void updatePosition()
    {

    }

    public void rotateC() {
        angle = (angle + rotangle) % 360;
    }

    public void rotateAc() {
        angle = (angle - rotangle + 360) % 360;
    }

    boolean canMove()
    {
        return true;
    }
}
