package boids;

import boids.gui.Frame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Boids {
    public static final Point bonds = new Point(1800, 1500);
    public static final int cohesionRadius = (int) Math.pow(200, 2);
    public static final int separationRadius = (int) Math.pow(50, 2);
    public static final int alignmentRadius = (int) Math.pow(300, 2);
    public static final int repealingDistance = 30;
    private final ArrayList<Boid> boids = new ArrayList<>();
    private final Timer timer = new Timer();
    private final Frame frame;
    private TimerTask game;


    public Boids(boids.gui.Frame frame) {
        this.frame = frame;
    }

    public void startSimulation() {

        spawnBoids(50);

        game = new TimerTask() {
            public void run() {
                ArrayList<Boid> newBoids = new ArrayList<>();
                for (Boid boid : getBoids()) {
                    double currentAngle = boid.getAngle();
                    double finalAngle = currentAngle; // Start with the current angle

                    Angle angle = calculateBoidAngle(boid);

                    // Weights for combining the different angles
                    double separationWeight = (double) frame.getSettingsPanel().getSeparationSlider().getValue() / 10;
                    double alignmentWeight = (double) frame.getSettingsPanel().getAlignmentSlider().getValue() / 10;

                    double cohesionWeight = (double) frame.getSettingsPanel().getCohesionSlider().getValue() / 10;

                    double maxTurnRate = Math.toRadians(8); // Limit the turn rate per frame
                    double smoothingFactor = 0.1; // Factor for smooth transitions

                    // Combine angles based on their weights
                    double combinedSin = 0;
                    double combinedCos = 0;

                    if (angle.getSeparationAngle() < Math.PI * 2) {
                        combinedSin += separationWeight * Math.sin(angle.getSeparationAngle());
                        combinedCos += separationWeight * Math.cos(angle.getSeparationAngle());
                    }

                    if (angle.getAlignmentAngle() < Math.PI * 2) {
                        combinedSin += alignmentWeight * Math.sin(angle.getAlignmentAngle());
                        combinedCos += alignmentWeight * Math.cos(angle.getAlignmentAngle());
                    }

                    if (angle.getCohesionAngle() < Math.PI * 2) {
                        combinedSin += cohesionWeight * Math.sin(angle.getCohesionAngle());
                        combinedCos += cohesionWeight * Math.cos(angle.getCohesionAngle());
                    }

                    if (combinedSin != 0 || combinedCos != 0) {
                        finalAngle = Math.atan2(combinedSin, combinedCos);
                    }

                    // Limit the change in angle to avoid sharp turns
                    double angleDifference = finalAngle - currentAngle;
                    angleDifference = Math.atan2(Math.sin(angleDifference), Math.cos(angleDifference)); // Normalize difference

                    if (Math.abs(angleDifference) > maxTurnRate) {
                        angleDifference = Math.signum(angleDifference) * maxTurnRate;
                    }

                    finalAngle = currentAngle + angleDifference;

                    // Smooth the transition by interpolating with the previous angle
                    finalAngle = (1 - smoothingFactor) * currentAngle + smoothingFactor * finalAngle;

                    // Set the updated angle
                    boid.setAngle(finalAngle);
                }

                for (Boid boid : getBoids()) {
                    boid.move(boid.getAngle());
                    teleportIfOutOfBounds(boid);
                }
            }

            private Angle calculateBoidAngle(Boid boid) {
                double cohesionAngle = (Math.PI * 2) + 0.1;
                double separationAngle = (Math.PI * 2) + 0.1;
                double alignmentAngle = (Math.PI * 2) + 0.1;

                int boidsInCohesionDistance = 0;

                int cohesionSumx = 0;
                int cohesionSumY = 0;
                Point centerMass = new Point(0, 0);

                ArrayList<Double> cohesionMasses = new ArrayList<>();
                ArrayList<Boid> separationAngles = new ArrayList<>();
                ArrayList<Boid> alignmentAngles = new ArrayList<>();

                double separationX = 0, separationY = 0;

                for (Boid otherBoid : boids) {
                    if (otherBoid == boid) continue;
                    double distanceToOtherBoid = getDistanceToOtherBoid(boid, otherBoid);
                    if (distanceToOtherBoid <= cohesionRadius) {
                        cohesionSumx += otherBoid.getX();
                        cohesionSumY += otherBoid.getY();
                        boidsInCohesionDistance++;
                    }
                    if (distanceToOtherBoid <= separationRadius) {
                        double dx = boid.getX() - otherBoid.getX();
                        double dy = otherBoid.getY() - boid.getY();

                        separationX += dx;
                        separationY += dy;
                    }
                    if (distanceToOtherBoid <= alignmentRadius) {
                        alignmentAngles.add(otherBoid);
                    }

                }
                //cohesion
                if (boidsInCohesionDistance > 0) {
                    centerMass = new Point(cohesionSumx / boidsInCohesionDistance, cohesionSumY / boidsInCohesionDistance);
                    cohesionAngle = Math.atan2(boid.getY() - centerMass.y, centerMass.x - boid.getX());
                }

                //Separation
                if (separationX != 0 || separationY != 0) {
                    separationAngle = Math.atan2(separationY, separationX);
                }
                //Alignment
                if (!alignmentAngles.isEmpty()) {
                    double alignmentSumX = 0, alignmentSumY = 0;

                    for (Boid otherBoid : alignmentAngles) {
                        alignmentSumY += Math.sin(otherBoid.getAngle() % Math.PI * 2);
                        alignmentSumX += Math.cos(otherBoid.getAngle() % Math.PI * 2);

                    }
                    alignmentSumY /= alignmentAngles.size();
                    alignmentSumX /= alignmentAngles.size();

                    alignmentAngle = Math.atan2(-alignmentSumY, alignmentSumX);
                }


                return new Angle(cohesionAngle, separationAngle, alignmentAngle);
            }
        };

        timer.scheduleAtFixedRate(game, 0, 10);

    }


    public void spawnBoids(int number) {
        for (int i = 0; i < number; i++) {
            Boid boid = new Boid();

            boids.add(boid);
        }
    }

    public ArrayList<Boid> getBoids() {
        return boids;
    }


    public ArrayList<Boid> getClosestBoids(Boid boid, ArrayList<Boid> boids) {
        ArrayList<Boid> sortedBoids = new ArrayList<>(boids);
        sortedBoids.remove(boid);

        sortedBoids.removeIf(b -> getDistanceToOtherBoid(boid, b) > Boids.cohesionRadius);//Remove boids that exceed the radius 300

//        sortedBoids.sort(Comparator.comparingDouble(b -> getDistanceToOtherBoid(boid, b)));
        return sortedBoids;
    }

    public double getDistanceToOtherBoid(Boid boid, Boid otherBoid) {
        double dx = boid.getX() - otherBoid.getX();
        double dy = boid.getY() - otherBoid.getY();
        return dx * dx + dy * dy;  // Use squared distance to avoid the square root return new Point(boid.getX(),boid.getY()).distance(otherBoid.getX(),otherBoid.getY());
    }

    public double getAverageAngle(ArrayList<Double> angles) {
        double y = 0, x = 0;
        for (Double angle : angles) {
            angle = (angle + 2 * Math.PI) % (2 * Math.PI);
            y += (Math.sin(angle));
            x += (Math.cos(angle));
        }
        y /= angles.size();
        x /= angles.size();

        return Math.atan2(y, x);
    }

    public void teleportIfOutOfBounds(Boid boid) {
        if (boid.getX() < 0)
            boid.setX(frame.getPanel().getWidth());
        if (boid.getY() < 0)
            boid.setY(frame.getPanel().getHeight());
        if (boid.getX() > frame.getPanel().getWidth())
            boid.setX(0);
        if (boid.getY() > frame.getPanel().getHeight())
            boid.setY(0);
    }


}





