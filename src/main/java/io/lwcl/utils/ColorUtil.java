package io.lwcl.utils;


import java.awt.*;

public class ColorUtil {

    private ColorUtil() {
        throw new AssertionError("Constructor is not allowed");
    }

    public static Color hex2Color(String colorHex) {
        if (colorHex == null || colorHex.length() != 7) {
            throw new IllegalArgumentException("Invalid color hexadecimal string");
        }

        return Color.decode(colorHex);
    }
}