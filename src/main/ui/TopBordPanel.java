package ui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TopBordPanel extends JPanel {

    private Image backgroundImage;

    public TopBordPanel() {
        super();
        this.backgroundImage = new ImageIcon("data/logo.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}

