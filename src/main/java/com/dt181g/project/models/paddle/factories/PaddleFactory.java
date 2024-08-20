package com.dt181g.project.models.paddle.factories;

import com.dt181g.project.models.paddle.PaddleModel;

/**
 * The {@code PaddleFactory} interface defines a factory for creating instances
 * of the {@link PaddleModel} class. Factories implementing this interface are
 * responsible for creating concrete paddle objects with specific
 * configurations.
 * @author Daniel Jönsson
 * @version 1.0
 */
public interface PaddleFactory {

    /**
     * Creates a new instance of the {@link PaddleModel}.
     * @return A newly created {@link PaddleModel} object.
     */
    PaddleModel createPaddle();
}
