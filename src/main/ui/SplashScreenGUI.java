package ui;
import javax.swing.*;

// Represents the online banking system's splash screen window frame
public class SplashScreenGUI extends JFrame {

    // EFFECTS: Constructor sets up the splash screen with an image before the main menu appears
    public SplashScreenGUI() {
        setUndecorated(true);
        setSize(700, 394);
        setLocationRelativeTo(null);
        setContentPane(new JLabel(new ImageIcon("/Users/ryo/Library/Mobile " +
                "Documents/com~apple~CloudDocs/CPSC 210/project_a7g2f/data/UBC_picture.jpg")));
        setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dispose();
    }
}