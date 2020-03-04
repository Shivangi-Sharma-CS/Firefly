import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Firefly2Viewer {

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();

        final int FRAME_WIDTH = 400;
        final int FRAME_HEIGHT = 400;

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Firefly2Component component = new Firefly2Component();
        component.setOpaque(true);
        component.setBackground(Color.DARK_GRAY);
        frame.add(component, BorderLayout.CENTER);

        component.startAnimation();

        JButton saveState = new JButton("Save State to File");
        saveState.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event) {
                try {
                    component.writeDataToFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        JButton loadState = new JButton("Load State From File");
        loadState.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    component.readDataAndPaint();
                    component.startAnimation();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        JPanel panel = new JPanel();
        panel.add(saveState);
        panel.add(loadState);
        frame.add(panel, BorderLayout.NORTH);

        frame.setVisible(true);


    }
}
