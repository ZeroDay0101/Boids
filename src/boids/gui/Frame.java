package boids.gui;

import boids.Boids;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private final Boids boids = new Boids(this);
    private final JPanel panel = new Panel(boids);
    private final SettingsPanel settingsPanel = new SettingsPanel();

    public Frame() throws HeadlessException, InterruptedException {
        this.setTitle("Boids");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        this.setUndecorated(true);
        this.setSize(Boids.bonds.x, Boids.bonds.y);
        this.add(panel, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.EAST);
        this.setVisible(true);

        boids.startSimulation();
    }

    public static void main(String[] args) throws InterruptedException {
        new Frame();


        System.out.println(Math.toDegrees(Math.atan2((Math.sin(Math.toRadians(45)) + Math.sin(Math.toRadians(225)) / 2), (Math.cos(Math.toRadians(45)) + Math.cos(Math.toRadians(225))) / 2)));
    }

    public JPanel getPanel() {
        return panel;
    }

    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }
}


