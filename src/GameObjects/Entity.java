package GameObjects;

import javafx.scene.shape.Circle;

public class Entity {

    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private Circle circle;

    private OnUpdateListener onUpdateListener;

    public Entity(double x, double y, double velocityX, double velocityY, double radius) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.circle = new Circle(x, y, radius);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Circle getCircle() {
        return circle;
    }

    public void update() {
        x += velocityX;
        y += velocityY;

        if (onUpdateListener != null) {
            onUpdateListener.onUpdate();
        }
    }

    public void setOnUpdate(OnUpdateListener listener) {
        this.onUpdateListener = listener;
    }

    public interface OnUpdateListener {
        void onUpdate();
    }
}