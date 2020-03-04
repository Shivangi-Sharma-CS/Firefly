import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A firefly has a current phase between 0 and 2π , if 2π it flashes yellow.
 * A firefly has a rate of change of phase. For every firefly, it is 0.785. AKA frequency 8 timeSteps * 0.785 = 2π
 * Rate at which it is flashing its light , it is the same as the rate of change of phase except when it is startled
 * When it is startled, the frequency updates with N, the number of fireflies seen, which increments the current phase
 * where K is 0.1 constant the startling factor
 * N is the number of flies the firefly has seen in the eyesight range M of 100 pixels
 */
public class Firefly2 implements Serializable {

    private Random r;
    private double currentPhase;
    private double frequency;
    private int x;
    private int y;
    private boolean flash;
    private ArrayList<Firefly2> fireflies;
    private int N;
    private final double K = 0.1;
    private Lock stateLock;


    public Firefly2() {

        r = new Random();
        flash = false;
        currentPhase = r.doubles(1, 0, (2 * Math.PI )).findFirst().getAsDouble();
        frequency = 0.785;
        x = (int) (Math.random() * 400);
        y = (int) (Math.random() * 400);
        fireflies = new ArrayList<>();
        N = 0;
        stateLock = new ReentrantLock();
    }

    /**
     * Checks the distance of one firefly with the other
     * @param other firefly
     * @return the distance between the two
     */
    public double distanceTo(Firefly2 other) {
        return Math.sqrt(Math.pow(other.x - x,2) + Math.pow(other.y-y,2));
    }

    /**
     * Adds a neighbor of the firefly
     * @param f another firefly in the neighbor
     */
    public void addNbr(Firefly2 f) {
        fireflies.add(f);
    }

    /**
     * Checks whether the firefly is flashing
     * @return flash true if flashing, false otherwise
*/
    public boolean flashing() {

        return flash;
    }

    /**
     * a method that updates the currentPhase based on N value to get new currentPhase using new frequency
     * @return the current phase
     */
    public double updateCurrentPhase() {
        stateLock.lock();
        try {
            frequency = 0.785 + (K * N * Math.sin((2 * Math.PI) - currentPhase));
            currentPhase = currentPhase + frequency;
            //System.out.println(currentPhase);
            flash = currentPhase >=  (2 * Math.PI);
            if (flash)
                currentPhase %= (2 * Math.PI);
        } finally {
            stateLock.unlock();
        }
        return currentPhase;
    }

    /**
     * if the neighboring fireflies are flashing, increment N
     */
    public void updateN () {
        N = 0;
        stateLock.lock();
        try {
            for (Firefly2 f : fireflies) {
                if (f.flashing()) {
                    N++;
                }
            }
        } finally {
            stateLock.unlock();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
