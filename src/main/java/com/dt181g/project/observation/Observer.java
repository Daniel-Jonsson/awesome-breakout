package com.dt181g.project.observation;


import com.dt181g.project.controllers.BreakoutController;

/**
 * The {@code Observer} interface is used to implement the Observer pattern,
 * allowing the {@link BreakoutController} to receive updates from
 * {@link com.dt181g.project.models.BreakoutModel}
 * @author Daniel Jönsson
 * @version 1.0
 * @see BreakoutController
 * @see com.dt181g.project.models.BreakoutModel
 */
public interface Observer {
    /**
     * This method is called by the model to notify the controller that an
     * update has occurred. The controller will update the view.
     */
    void update();
}
