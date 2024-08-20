package com.dt181g.project.views;

import com.dt181g.project.listeners.SplashScreenListener;
import com.dt181g.project.support.Appconfig;
import com.dt181g.project.support.Util;

import javax.swing.JWindow;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SplashScreen} class displays a splash screen with a progress
 * bar. This was mainly made just to incorporate more swing components and
 * layout managers.
 * @author Daniel Jönsson
 * @version 1.1
 */
public final class SplashScreen extends JWindow {

    /**
     * Instance field variables
     */
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final List<SplashScreenListener> listeners = new ArrayList<>();
    private int val = 0;

    /**
     * Constructs a new {@code SplashScreen} and configures its appearance
     * and layout. It creates a new Thread which updates the "val" instance
     * variable and sets the progressbar value to that variable.
     */
    public SplashScreen() {
        Dimension screenDim = Appconfig.WINDOW_DIMENSION;
        Point screenPos = Appconfig.WINDOW_POSITION;
        Font smallFont= Appconfig.SMALL_TEXT;
        Font bigFont= Appconfig.BIG_TEXT;
        JLabel welcomeLabel=new JLabel(Appconfig.WELCOME_TEXT);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel gameLabel=new JLabel(Appconfig.GAME_TITLE);
        gameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameLabel.setFont(bigFont);

        JLabel copyLabel=new JLabel(Appconfig.GENERAL_INFO_TEXT);
        copyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        copyLabel.setFont(smallFont);

        JLabel loadinLabel=new JLabel(Appconfig.LOADING_TEXT);
        loadinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loadinLabel.setFont(smallFont);

        JLabel perLabel=new JLabel("0");
        perLabel.setHorizontalAlignment(SwingConstants.CENTER);
        perLabel.setFont(Appconfig.PERCENT_TEXT);
        progressBar.setForeground(Util.INSTANCE.generateRandomColor(Appconfig.COLOR_LUMINANCE, Appconfig.COLOR_SATURATION));

        Container c=getContentPane();
        c.setLayout(new GridLayout(7,1,10,10));
        c.add(welcomeLabel);
        c.add(gameLabel);
        c.add(copyLabel);
        c.add(loadinLabel);
        c.add(perLabel);
        c.add(progressBar);
        setBounds(screenPos.x, screenPos.y, screenDim.width, screenDim.height);
        setLocationRelativeTo(null);
        setVisible(true);
        // Create a new Thread not to block EDT
        Thread t = new Thread(() -> {
            try {
                while(!(val==100)) {
                    Thread.sleep(35);
                    val++;
                    perLabel.setText(val+"%");
                    progressBar.setValue(val);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally{
                dispose();
                // Notify controller that the loading is done.
                notifyScreenListeners();
            }

        });
        t.start();
    }

    /**
     * Adds a new listener to the {@code SplashScreen}
     * @param listener The listener to add.
     */
    public void addSplashScreenListener(SplashScreenListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all listeners about a change, in this case that the loading
     * is done.
     */
    private void notifyScreenListeners() {
        for (SplashScreenListener listener : listeners) {
            listener.onComplete();
        }
    }
}

