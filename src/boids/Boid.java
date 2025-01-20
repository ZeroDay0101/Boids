package boids;

import java.awt.*;
import java.util.Random;

public class Boid {
    public static final int boidsWidth = 10;
    public static final int boidsHeight = 10;
    private final int speed = 2;
    private int x;
    private int y;
    private double angle;
    private Color boidColor = Color.BLACK;

    public Boid() {
        assignRandomDirection();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Color getBoidColor() {
        return boidColor;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "Boid{" +
                "x=" + x +
                ", y=" + y +
                ", angle=" + angle +
                ", boidColor=" + String.format("#%02x%02x%02x", boidColor.getRed(), boidColor.getGreen(), boidColor.getBlue()) +
                '}';
    }

    private void assignRandomDirection() {
        Random r = new Random();

        this.x = r.nextInt(Boid.boidsWidth, Boids.bonds.x - Boid.boidsWidth);
        this.y = r.nextInt(Boid.boidsHeight, Boids.bonds.y - Boid.boidsHeight);

        this.angle = r.nextDouble() * 2 * Math.PI;

        this.boidColor = new Color((int) (Math.random() * 0x1000000));
    }

    private void assignDebugDirection() {
        Random r = new Random();

        if (r.nextBoolean()) {
            this.x = Boids.bonds.x;
            this.y = Boids.bonds.y / 2;
            this.angle = Math.PI;
        } else {
            this.x = 0;
            this.y = Boids.bonds.y / 2;
            this.angle = Math.PI * 2;
        }


        this.boidColor = new Color((int) (Math.random() * 0x1000000));
    }

    public void move(double angle) {
        x += (int) (speed * Math.cos(angle));
        y += (int) (speed * -Math.sin(angle));
    }


}
