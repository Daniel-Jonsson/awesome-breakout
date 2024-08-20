package com.dt181g.project;

import com.dt181g.project.controllers.SplashScreenController;

import javax.swing.SwingUtilities;

/**
 * The main starting point for Project Assignment.
 * @author Daniel Jönsson
 */
final class EpicBreakout {
    private EpicBreakout() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * Entry point for the Epic Breakout Application, using method reference
     * to instantiate a new {@link SplashScreenController} running on the EDT.
     * @param args Command line arguments passed to the application.
     */
    public static void main(final String... args) {
        SwingUtilities.invokeLater(SplashScreenController::new);
    }
}
