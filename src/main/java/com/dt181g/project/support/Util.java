package com.dt181g.project.support;

import java.awt.Color;
import java.util.Random;

/**
 * The {@code Util} enum provides utility methods for generating a random
 * color, it is used for more customization on blocks and makes the game more
 * fun for users.
 * @author Daniel Jönsson
 * @version 1.0
 * @see Color
 * @see Random
 */
public enum Util {

    /**
     * The instance of the {@code Util} class.
     */
    INSTANCE;
    private final Random random = new Random();

    /**
     * Generates a random color with a specified luminance and saturation.
     * The generated color is based on a random hue and saturation.
     * @param luminance The desired luminance of the color.
     * @param sat The saturation factor used to adjust color saturation.
     * @return A color with the specified luminance and saturation.
     */
    public Color generateRandomColor(final float luminance, final float sat) {
        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(20) + 10) / sat;
        return Color.getHSBColor(hue, saturation, luminance);
    }
}
