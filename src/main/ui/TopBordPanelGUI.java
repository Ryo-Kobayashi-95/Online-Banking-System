package ui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

// Represents the top bord image for both main menu and account menu
public class TopBordPanelGUI extends JPanel {

    private Image backgroundImage;

    // MODIFIES: this
    // EFFECTS: Initialize the background image for either main menu or account menu
    public TopBordPanelGUI(String menu) {
        super();
        if (menu.equals("main")) {
            this.backgroundImage = new ImageIcon("data/mainMenuLogo.png").getImage();
        } else {
            this.backgroundImage = new ImageIcon("data/accountMenuLogo.png").getImage();
        }
    }

    // MODIFIES: this
    // EFFECTS: render the image at the top bord the frame
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}

