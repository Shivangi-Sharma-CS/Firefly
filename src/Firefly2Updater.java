import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Firefly2Updater {

    private Firefly2[] flies;
    private Lock stateLock;
    private JComponent component;
    private final int DELAY = 100;

    public Firefly2Updater(Firefly2[] flies, Firefly2Component component) {
        this.flies = flies;
        this.component = component;
        stateLock = new ReentrantLock();
    }

    public void updater() throws InterruptedException {

        while(true) {
            stateLock.lock();
            try {
                for (Firefly2 f : flies) {
                    f.updateN();
                    f.updateCurrentPhase();
                }
            } finally {
                stateLock.unlock();
            }
            pause(2);
        }
    }



    public void draw(Graphics g) throws InterruptedException {
        stateLock.lock();
        try {
            for (Firefly2 fly : flies) {
                if((fly.flashing()))  {
                    g.setColor(Color.YELLOW);
                    g.fillRect(fly.getX(), fly.getY(), 10, 10);
                }
                else {
                    g.setColor(Color.MAGENTA);
                    g.drawRect(fly.getX(), fly.getY(), 10, 10);
                }

            }
        } finally {
            stateLock.unlock();

        }
        pause(2);
    }


    public void pause(int steps)
            throws InterruptedException {
        component.repaint();
        Thread.sleep(steps * DELAY);
    }

}
