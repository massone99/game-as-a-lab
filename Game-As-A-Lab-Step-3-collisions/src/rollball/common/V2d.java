package rollball.common;

/**
 * A class representing a 2D vector with x and y components.
 */
public class V2d {

    public double x, y;

    public V2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public V2d(P2d to, P2d from) {
        this(to.x - from.x, to.y - from.y); // Use the other constructor to reduce code duplication
    }

    public V2d sum(V2d v) {
        return new V2d(x + v.x, y + v.y); // Return a new vector that is the sum of this vector and v
    }

    public double module() {
        return Math.sqrt(x * x + y * y); // Return the magnitude of this vector
    }

    public V2d getNormalized() {
        double mod = module(); // Calculate the magnitude once to avoid duplicate calculations
        return new V2d(x / mod, y / mod); // Return a new vector that is the normalized version of this vector
    }

    public V2d mul(double fact) {
        return new V2d(x * fact, y * fact); // Return a new vector that is this vector multiplied by fact
    }

    @Override
    public String toString() {
        return "V2d(" + x + "," + y + ")"; // Return a string representation of this vector
    }
}