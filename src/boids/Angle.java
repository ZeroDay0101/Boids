package boids;

public class Angle {
    private final double cohesionAngle;
    private final double separationAngle;
    private final double alignmentAngle;


    public Angle(double cohesionAngle, double separationAngle, double alignmentAngle) {
        this.cohesionAngle = cohesionAngle;
        this.separationAngle = separationAngle;
        this.alignmentAngle = alignmentAngle;
    }

    public double getCohesionAngle() {
        return cohesionAngle;
    }

    public double getSeparationAngle() {
        return separationAngle;
    }

    public double getAlignmentAngle() {
        return alignmentAngle;
    }

    @Override
    public String toString() {
        return "Angle{" +
                "cohesionAngle=" + cohesionAngle +
                ", separationAngle=" + separationAngle +
                ", alignmentAngle=" + alignmentAngle +
                '}';
    }
}
