package com.dt181g.project.views;

import com.dt181g.project.support.Appconfig;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * The {@code InfoPanel} class represents the information panel in the Breakout game.
 * This panel displays game information such as current score, time elapsed,
 * and bricks left to destroy
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class InfoPanel extends JPanel {

    /**
     * Instance field variables
     */
    private JLabel scoreText;
    private JLabel score;
    private JLabel timeText;
    private JLabel timeElapsed;
    private JLabel blockText;
    private JLabel blocksLeft;

    /**
     * Constructs a new {@code InfoPanel} and calls methods to config the panel
     */
    public InfoPanel(){
        configPanel();
        initLabels();
        addLabels();
    }

    /**
     * Configures the layout and appearance of the info panel.
     */
    private void configPanel(){
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 30));
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(Appconfig.FRAME_SIZE.width, 100));
    }

    /**
     * Initializes and customizes the various labels used to display game
     * information.
     */
    private void initLabels(){
        this.scoreText = new JLabel(Appconfig.SCORE_TEXT);
        this.score = new JLabel("0");
        this.timeText = new JLabel(Appconfig.TIME_TEXT);
        this.timeElapsed = new JLabel("0");
        this.blockText = new JLabel(Appconfig.BLOCK_TEXT);
        this.blocksLeft = new JLabel(String.valueOf(Appconfig.BLOCK_AMOUNT));
        Font labelFont = Appconfig.LABLE_FONT;
        Color labelColor = Color.WHITE;
        setFontAndColor(scoreText, labelFont, labelColor);
        setFontAndColor(score, labelFont, labelColor);
        setFontAndColor(timeText, labelFont, labelColor);
        setFontAndColor(timeElapsed, labelFont, labelColor);
        setFontAndColor(blockText, labelFont, labelColor);
        setFontAndColor(blocksLeft, labelFont, labelColor);
    }

    /**
     * Sets the font and text color for the given label.
     * @param label The label to customize.
     * @param font  The font to apply to the label's text.
     * @param color The color to set for the label's text.
     */
    private void setFontAndColor(final JLabel label, final Font font,
                                 final Color color) {
        label.setFont(font);
        label.setForeground(color);
    }

    /**
     * Adds the labels to the info panel.
     */
    private void addLabels(){
        add(scoreText);
        add(score);
        add(timeText);
        add(timeElapsed);
        add(blockText);
        add(blocksLeft);
    }

    public void setScore(String score) {
        this.score.setText(score);
    }

    public void setBlocksLeft(String blocksLeft) {
        this.blocksLeft.setText(blocksLeft);
    }

    public void setTimeElapsed(String timeElapsed) {
        this.timeElapsed.setText(timeElapsed);
    }
}
