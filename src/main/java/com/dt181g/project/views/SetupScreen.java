package com.dt181g.project.views;

import com.dt181g.project.listeners.SetupScreenListener;
import com.dt181g.project.support.Appconfig;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.Component;
import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SetupScreen} class represents the game setup screen. It allows
 * the player to choose the game difficulty and start the game.
 * @author Daniel Jönsson
 * @version 1.1
 */
public final class SetupScreen extends JFrame {

    /**
     * Instance field variables
     */
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JButton startButton;
    private JButton infoButton;
    private JCheckBox agreeButton;
    private JButton selectedDifficultyButton = null;
    private JRadioButton radioButton;
    private final List<SetupScreenListener> listeners = new ArrayList<>();
    private JSlider jSlider;
    private JLabel volumeLabel;

    private JLabel gameTitle;
    private JPanel buttonPanel;

    /**
     * Constructs a new {@code SetupScreem} which configures the frame and
     * display it with the help of some methods.
     */
    public SetupScreen() {
        initFrameConfig();
        createButtons();
        addComponents();
        setButtonListener();
        showFrame();
    }

    /**
     * Initializes the configuration for the frame, such as title, size, and location.
     */
    private void initFrameConfig(){
        setTitle(Appconfig.GAME_TITLE);
        setPreferredSize(new Dimension(Appconfig.FRAME_SIZE.width,
                Appconfig.FRAME_SIZE.height+100));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (screenSize.width - getWidth()) / 2;
        int centerY = (screenSize.height - getHeight()) / 2;
        setLocation(centerX, centerY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    /**
     * Creates buttons, labels, and sets their styles, fonts, and alignments.
     */
    private void createButtons() {
        gameTitle = new JLabel(Appconfig.GAME_TITLE);
        gameTitle.setFont(Appconfig.TITLE_FONT);
        easyButton = new JButton(Appconfig.EASY_BUTTON_TEXT);
        mediumButton = new JButton(Appconfig.MEDIUM_BUTTON_TEXT);
        hardButton = new JButton(Appconfig.IMPOSSIBLE_BUTTON_TEXT);
        infoButton = new JButton(Appconfig.GAME_CONTROL_BUTTON_TEXT);
        startButton = new JButton(Appconfig.START_BUTTON_TEXT);
        agreeButton = new JCheckBox(Appconfig.CHECKBOX_TEXT);
        radioButton = new JRadioButton("I do not like hotdogs!");
        jSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeLabel = new JLabel("Select the music volume: ");
        startButton.setEnabled(false);
        addButtonStyle(easyButton);
        addButtonStyle(mediumButton);
        addButtonStyle(hardButton);
        addButtonStyle(infoButton);
        addButtonStyle(startButton);
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        agreeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        radioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Adds styling to the buttons
     * @param button The button to style
     */
    private void addButtonStyle(JButton button){
        button.setFont(Appconfig.BUTTON_FONT);
        button.setPreferredSize(new Dimension(40, 35));
    }

    /**
     * Adds components to the frame, setting their spacing.
     */
    private void addComponents() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(gameTitle);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonPanel.add(easyButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonPanel.add(mediumButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonPanel.add(hardButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonPanel.add(infoButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonPanel.add(agreeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(radioButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(volumeLabel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(jSlider);
        buttonPanel.add(Box.createVerticalGlue());
        add(buttonPanel);
    }


    /**
     * Sets up action listeners for each button and calls handleButtonClick
     * when they are pressed.
     */
    private void setButtonListener() {
        ActionListener listener = this::handleButtonClick;
        easyButton.addActionListener(listener);
        mediumButton.addActionListener(listener);
        hardButton.addActionListener(listener);
        infoButton.addActionListener(listener);
        startButton.addActionListener(listener);
    }

    /**
     * Handles button click events by selecting the game difficulty or
     * starting the game.
     * @param e The action event that triggered the method.
     */
    private void handleButtonClick(final ActionEvent e) {
        if (e.getSource() == easyButton) {
            selectDifficulty(easyButton);
        } else if (e.getSource() == hardButton) {
            selectDifficulty(hardButton);
        } else if (e.getSource() == mediumButton) {
            selectDifficulty(mediumButton);
        } else if (e.getSource() == startButton) {
            notifyListeners();
        } else if (e.getSource() == infoButton) {
            JOptionPane.showMessageDialog(this, Appconfig.GAME_INFO,
                    "Game Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Selects the difficulty level when a difficulty button is clicked and
     * updates the start button's state.
     * @param selectedButton The button representing the selected difficulty.
     */
    private void selectDifficulty(final JButton selectedButton) {

        if (selectedDifficultyButton != null) {
            selectedDifficultyButton.setBackground(UIManager.getColor("Button.background"));
        }
        if (selectedDifficultyButton == selectedButton) {
            selectedDifficultyButton = null;
            startButton.setEnabled(false);
        } else {
            selectedButton.setBackground(Color.LIGHT_GRAY);
            selectedDifficultyButton = selectedButton;
            selectedDifficultyButton.setEnabled(true);
            startButton.setEnabled(true);
        }
    }

    /**
     * Displays the frame with the configured settings.
     */
    private void showFrame(){
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Adds a new listener to the {@code SplashScreen}
     * @param listener The listener to add.
     */
    public void addListener(SetupScreenListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all listeners about a change, in this case that the start
     * button was pressed.
     */
    private void notifyListeners() {
        for (SetupScreenListener listener : listeners) {
            listener.onStart();
        }
    }

    /**
     * Gets the selected Difficulty button
     * @return The selected difficulty button
     */
    public JButton getSelectedDifficultyButton() {
        return selectedDifficultyButton;
    }

    /**
     * Gets the JSlider which is used for music volume
     * @return The JSlider for music volume
     */
    public JSlider getjSlider() {
        return jSlider;
    }

    /**
     * Gets the JPanel containing various buttons
     * @return The JPanel containing buttons
     */
    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    /**
     * Gets the agree checkbox.
     * @return The agree JCheckBox
     */
    public JCheckBox getAgreeButton() {
        return agreeButton;
    }

    /**
     * Gets the radio button
     * @return the JRadioButton
     */
    public JRadioButton getRadioButton() {
        return radioButton;
    }
}
