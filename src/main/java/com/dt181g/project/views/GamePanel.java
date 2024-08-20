package com.dt181g.project.views;

import com.dt181g.project.support.Appconfig;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

/**
 * The {@code GamePanel} class represents game being displayed. It extends
 * the {@link JPanel} and is responsible for rendering the games visual
 * components such as the ball, paddle, bricks, and power-ups.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class GamePanel extends JPanel {

    /**
     * Instance field variables
     */
    private final BreakoutView view;
    private final JLabel outcomeLabel;

    /**
     * Constructs a new {@code GamePanel} associated with the specified
     * {@link BreakoutView}.
     * @param view The BreakoutView to which this game panel belongs.
     */
    public GamePanel(final BreakoutView view){
        this.view = view;
        setPreferredSize(view.getGameDim());
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        outcomeLabel = new JLabel("");
        outcomeLabel.setFont(Appconfig.LABLE_FONT);
        outcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(outcomeLabel, BorderLayout.CENTER);
    }

    /**
     * Overrides the paintComponent method to render game objects.
     * @param g The Graphics context in which to paint.
     */
    @Override
    protected void paintComponent(final Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Rectangle ball = view.getBall();
        Rectangle paddle = view.getPaddle();
        List<Rectangle> bricks = view.getBricks();
        List<Color> brickColors = view.getBrickColors();
        List<Rectangle> powerups = view.getPowerups();
        List<Color> powerupColors = view.getPowerupcolors();
        Color paddleColor = view.getPaddleColor();
        Color ballColor = view.getBallColor();
        int ballX = ball.x;
        int ballY = ball.y;
        int ballWidth = ball.width;
        int ballHeight = ball.height;
        int paddleX = paddle.x;
        int paddleY = paddle.y;
        int paddleWidth = paddle.width;
        int paddleHeight = paddle.height;
        g2.setColor(ballColor);
        g2.fillOval(ballX,ballY,ballWidth,ballHeight);
        g2.setColor(paddleColor);
        g2.fillRect(paddleX,paddleY,paddleWidth,paddleHeight);
        for (int i = 0; i < bricks.size(); i++){
            Rectangle brick = bricks.get(i);
            g2.setColor(brickColors.get(i));
            g2.fillRect(brick.x, brick.y, brick.width, brick.height);
        }
        for (int i = 0; i < powerups.size(); i++) {
            Rectangle powerup = powerups.get(i);
            g2.setColor(powerupColors.get(i));
            g2.fillOval(powerup.x, powerup.y, powerup.width, powerup.height);
        }
    }

    /**
     * Sets the outcome label text to be displayed on the game panel.
     * @param outcomeLabel The outcome message to display.
     */
    public void setOutcomeLabel(final String outcomeLabel) {
        this.outcomeLabel.setText(outcomeLabel);
    }
}
