package com.apu.hms.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class RoleBackgroundPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final String role;

    public RoleBackgroundPanel(String role) {
        this.role = role;
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        boolean doctor = "Doctor".equals(role);
        boolean patient = "Patient".equals(role);
        Color top = patient ? new Color(246, 249, 253) : doctor
                ? new Color(247, 250, 253) : new Color(245, 250, 247);
        Color bottom = patient ? new Color(229, 238, 250) : doctor
                ? new Color(229, 240, 247) : new Color(223, 239, 231);
        g.setPaint(new GradientPaint(0, 0, top, width, height, bottom));
        g.fillRect(0, 0, width, height);
        if (doctor) {
            drawDoctor(g, width, height);
        } else if (patient) {
            drawPatient(g, width, height);
        } else {
            drawGovernance(g, width, height);
        }
        g.dispose();
    }

    private void drawGovernance(Graphics2D g, int width, int height) {
        int centerX = width - 175;
        int centerY = 155;
        GeneralPath shield = new GeneralPath();
        shield.moveTo(centerX - 65, centerY - 60);
        shield.lineTo(centerX + 65, centerY - 60);
        shield.curveTo(centerX + 65, centerY + 20, centerX + 35, centerY + 70, centerX, centerY + 90);
        shield.curveTo(centerX - 35, centerY + 70, centerX - 65, centerY + 20, centerX - 65, centerY - 60);
        shield.closePath();
        g.setColor(new Color(255, 255, 255, 95));
        g.fill(shield);
        g.setColor(new Color(13, 148, 136, 65));
        g.setStroke(new BasicStroke(2.2f));
        g.draw(shield);
        g.setColor(new Color(15, 118, 110, 70));
        g.fillRoundRect(centerX - 10, centerY - 25, 20, 50, 4, 4);
        g.fillRoundRect(centerX - 25, centerY - 10, 50, 20, 4, 4);
        drawChecklist(g, width - 330, 275, new Color(13, 148, 136, 105));
        drawBaseline(g, width, height, new Color(13, 148, 136, 65));
    }

    private void drawDoctor(Graphics2D g, int width, int height) {
        int left = width - 370;
        int top = 120;
        g.setColor(new Color(255, 255, 255, 85));
        g.fill(new RoundRectangle2D.Double(left, top, 320, 460, 18, 18));
        g.setColor(new Color(87, 157, 181, 65));
        g.setStroke(new BasicStroke(2.2f));
        g.draw(new RoundRectangle2D.Double(left, top, 320, 460, 18, 18));
        g.setColor(new Color(98, 145, 169, 55));
        g.fillRoundRect(left + 98, top - 16, 128, 32, 6, 6);
        g.fillOval(left + 156, top - 7, 10, 10);
        g.setStroke(new BasicStroke(3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(67, 157, 190, 85));
        g.drawArc(left - 14, top + 80, 90, 150, 70, 210);
        g.drawArc(left - 8, top + 120, 115, 320, 260, 170);
        g.drawArc(left + 120, top + 260, 105, 120, 180, 250);
        g.fillOval(left - 18, top + 80, 14, 14);
        g.fillOval(left + 50, top + 80, 14, 14);
        g.setColor(new Color(75, 153, 180, 75));
        g.draw(new Ellipse2D.Double(left + 145, top + 375, 58, 58));
        g.draw(new Ellipse2D.Double(left + 155, top + 385, 38, 38));
        g.fillOval(left + 169, top + 399, 10, 10);
        drawLines(g, left + 38, top + 64, 3, 42, 205, new Color(100, 140, 160, 48));
        drawChecklist(g, left + 40, top + 190, new Color(68, 157, 190, 90));
        drawBaseline(g, width, height, new Color(87, 157, 205, 55));
    }

    private void drawPatient(Graphics2D g, int width, int height) {
        int centerX = width - 190;
        int centerY = 370;
        g.setColor(new Color(255, 255, 255, 38));
        g.fill(new Ellipse2D.Double(centerX - 205, centerY - 205, 410, 410));
        g.setColor(new Color(98, 151, 215, 45));
        g.setStroke(new BasicStroke(1.8f));
        g.draw(new Ellipse2D.Double(centerX - 205, centerY - 205, 410, 410));
        g.setColor(new Color(115, 163, 220, 38));
        g.draw(new Ellipse2D.Double(centerX - 145, centerY - 145, 290, 290));
        g.setColor(new Color(99, 144, 214, 55));
        g.fillRoundRect(centerX - 14, centerY - 65, 28, 130, 8, 8);
        g.fillRoundRect(centerX - 65, centerY - 14, 130, 28, 8, 8);
        drawBaseline(g, width, height, new Color(71, 132, 213, 75));
    }

    private void drawChecklist(Graphics2D g, int x, int y, Color checkColor) {
        g.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int index = 0; index < 3; index++) {
            int lineY = y + index * 36;
            g.setColor(checkColor);
            g.drawRoundRect(x, lineY, 18, 18, 4, 4);
            g.drawLine(x + 4, lineY + 9, x + 8, lineY + 13);
            g.drawLine(x + 8, lineY + 13, x + 15, lineY + 5);
            g.setColor(new Color(100, 116, 139, 42));
            g.drawLine(x + 32, lineY + 9, x + 205 - index * 38, lineY + 9);
        }
    }

    private void drawLines(Graphics2D g, int x, int y, int count, int spacing, int length, Color color) {
        g.setColor(color);
        g.setStroke(new BasicStroke(2f));
        for (int index = 0; index < count; index++) {
            g.drawLine(x, y + index * spacing, x + length - index * 28, y + index * spacing);
        }
    }

    private void drawBaseline(Graphics2D g, int width, int height, Color color) {
        int baseline = height - 45;
        int start = Math.max(width - 520, 120);
        int[] x = {start, start + 70, start + 105, start + 125, start + 145, start + 165,
            start + 185, start + 220, start + 290, start + 325, start + 345, width - 25};
        int[] y = {baseline, baseline, baseline, baseline - 22, baseline + 28, baseline - 85,
            baseline + 32, baseline - 18, baseline - 18, baseline + 3, baseline - 32, baseline};
        g.setColor(color);
        g.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawPolyline(x, y, x.length);
    }
}
