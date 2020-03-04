import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Firefly2Component extends JComponent implements Serializable {

    private Firefly2[] flies;
    ObjectOutputStream out;

    private Firefly2Updater updater;

    public Firefly2Component() throws IOException {
        flies = new Firefly2[100];
        for(int i=0;i<100;i++) {
            flies[i] = new Firefly2();
        }

        for(Firefly2 f1 : flies) {
            for(Firefly2 f2 : flies) {
                if(f2.distanceTo(f1) <= 100)
                    f1.addNbr(f2);
            }
        }

        updater = new Firefly2Updater(flies, this);

    }



    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        try {
            updater.draw(g);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void startAnimation() {
        class Firefly2Runnable implements Runnable {
            public void run() {
                try {
                    updater.updater();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Runnable r = new Firefly2Runnable();
        Thread t = new Thread(r);
        t.start();
    }

    public Firefly2[] getFlies() {
        return flies;
    }

    public void writeDataToFile() throws IOException {
        out = new ObjectOutputStream(new FileOutputStream ("/Users/shivangisharma/Documents/GitHub/cs2910-a4-Shivangi58/src/firefly.txt"));
        out.writeObject(flies);
    }

    public void readDataAndPaint() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("/Users/shivangisharma/Documents/GitHub/cs2910-a4-Shivangi58/src/firefly.txt"));
        Firefly2[] a = (Firefly2[]) in.readObject();
        updater = new Firefly2Updater(a, this);
        repaint();

    }
}
