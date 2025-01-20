package boids.gui;

import boids.Boid;
import boids.Boids;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private final Boids boids;

    public Panel(Boids boids) {
        this.boids = boids;
        this.setBackground(Color.WHITE);
        this.setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        for (Boid boid : boids.getBoids()) {
            g.setColor(boid.getBoidColor());
            g.fillRect(boid.getX(), boid.getY(), Boid.boidsWidth, Boid.boidsHeight);

            repaint();

//            g.setColor(Color.GREEN);
//            g.drawOval((int) (boid.getX() - Math.sqrt(Boids.cohesionRadius)/2 + Boid.boidsWidth/2), (int) (boid.getY() - Math.sqrt(Boids.cohesionRadius)/2 + Boid.boidsHeight/2), (int) Math.sqrt(Boids.cohesionRadius), (int) Math.sqrt(Boids.cohesionRadius));
//
//            g.setColor(Color.BLACK);
//            g.drawOval((int) (boid.getX() - Math.sqrt(Boids.separationRadius)/2 + Boid.boidsWidth/2), (int) (boid.getY() - Math.sqrt(Boids.separationRadius)/2 + Boid.boidsHeight/2), (int) Math.sqrt(Boids.separationRadius), (int) Math.sqrt(Boids.separationRadius));

//            g.setColor(Color.PINK);
//            g.drawOval((int) (boid.getX() - Math.sqrt(Boids.alignmentRadius)/2 + Boid.boidsWidth/2), (int) (boid.getY() - Math.sqrt(Boids.alignmentRadius)/2 + Boid.boidsHeight/2), (int) Math.sqrt(Boids.alignmentRadius), (int) Math.sqrt(Boids.alignmentRadius));

            g.setColor(Color.RED);
            g.drawLine(boid.getX() + Boid.boidsWidth / 2, boid.getY() + Boid.boidsHeight / 2, (int) (boid.getX() + 50 * Math.cos(boid.getAngle())) + Boid.boidsWidth / 2, (int) (boid.getY() + (double) Boid.boidsHeight / 2 + 50 * -Math.sin(boid.getAngle())));
        }
    }
}
