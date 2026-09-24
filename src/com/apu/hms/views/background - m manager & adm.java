import java.awt.*;
import java.awt.geom.GeneralPath;
import javax.swing.*;

// Dedicated background panel component for Medical Manager (Clinical Governance & Facilities)
class MedicalManagerBackgroundPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // 1. Render soft clinical governance gradient (#F5FAF7 -> #DFEFE7)
        Color topColor = new Color(245, 250, 247);
        Color bottomColor = new Color(223, 239, 231);
        GradientPaint bgGradient = new GradientPaint(0, 0, topColor, panelWidth, panelHeight, bottomColor);
        g2d.setPaint(bgGradient);
        g2d.fillRect(0, 0, panelWidth, panelHeight);

        // 2. Render Clinical Oversight Shield and Facility Crest in the right zone
        if (panelWidth > 580) {
            int shieldCenterX = panelWidth - 170;
            int shieldCenterY = 160;

            // Oversight Protective Shield outline
            GeneralPath shield = new GeneralPath();
            shield.moveTo(shieldCenterX - 65, shieldCenterY - 60);
            shield.lineTo(shieldCenterX + 65, shieldCenterY - 60);
            shield.curveTo(shieldCenterX + 65, shieldCenterY + 20, shieldCenterX + 35, shieldCenterY + 70, shieldCenterX, shieldCenterY + 90);
            shield.curveTo(shieldCenterX - 35, shieldCenterY + 70, shieldCenterX - 65, shieldCenterY + 20, shieldCenterX - 65, shieldCenterY - 60);
            shield.closePath();

            g2d.setColor(new Color(255, 255, 255, 90));
            g2d.fill(shield);

            g2d.setColor(new Color(13, 148, 136, 60)); // Emerald teal tone
            g2d.setStroke(new BasicStroke(2.2f));
            g2d.draw(shield);

            // Healthcare Cross emblem inside the shield
            g2d.setColor(new Color(15, 118, 110, 75));
            int crossX = shieldCenterX - 10;
            int crossY = shieldCenterY - 25;
            g2d.fillRect(crossX, crossY - 15, 20, 50);
            g2d.fillRect(crossX - 15, crossY, 50, 20);

            // Audit checklist lines representing quality management
            int listOriginX = panelWidth - 270;
            int listOriginY = 275;
            g2d.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            for (int i = 0; i < 3; i++) {
                int lineY = listOriginY + (i * 24);
                // Checkmark
                g2d.setColor(new Color(13, 148, 136, 120));
                g2d.drawLine(listOriginX, lineY + 5, listOriginX + 4, lineY + 9);
                g2d.drawLine(listOriginX + 4, lineY + 9, listOriginX + 11, lineY);

                // Entry bar
                g2d.setColor(new Color(100, 116, 139, 45));
                g2d.drawLine(listOriginX + 22, lineY + 5, listOriginX + 180, lineY + 5);
            }
        }

        // 3. Geometric clinical bed occupancy / hospital baseline line at the bottom
        int baselineY = panelHeight - 40;
        int lineStartX = Math.max(panelWidth - 420, 180);
        g2d.setColor(new Color(13, 148, 136, 65));
        g2d.setStroke(new BasicStroke(2.0f));

        // Stepped facility flow line
        int[] xSteps = {lineStartX, lineStartX + 80, lineStartX + 120, lineStartX + 180, lineStartX + 230, lineStartX + 300, panelWidth - 25};
        int[] ySteps = {baselineY, baselineY, baselineY - 18, baselineY - 18, baselineY, baselineY, baselineY};
        g2d.drawPolyline(xSteps, ySteps, xSteps.length);
    }
}