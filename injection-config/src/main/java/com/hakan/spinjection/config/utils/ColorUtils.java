package com.hakan.spinjection.config.utils;

import com.hakan.spinjection.utils.ProtocolVersion;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ColorUtil class to convert color
 * codes in messages.
 */
public class ColorUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F\\d]{6}");
    private static final Pattern COLOR_PATTERN = Pattern.compile("(?<color>(([§&][A-Fa-f\\d|rR])|(#[A-Fa-f\\d]{6})))");
    private static final Pattern FORMAT_PATTERN = Pattern.compile("(?<format>[§&][k-oK-OrR])");

    /**
     * Convert a message to a colored message.
     *
     * @param message message to convert
     * @return colored message
     */
    public static @Nonnull String colored(@Nonnull String message) {
        if (ProtocolVersion.isNewerOrEqual(ProtocolVersion.v1_16)) {
            Matcher matcher = HEX_PATTERN.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, String.valueOf(net.md_5.bungee.api.ChatColor.of(color)));
                matcher = HEX_PATTERN.matcher(message);
            }
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Convert hex color code to chat color.
     *
     * @param hex hex color code
     * @return chat color
     */
    public static @Nonnull String color(@Nonnull String hex) {
        return colored(hex);
    }

    /**
     * Convert rgb to chat color.
     *
     * @param r red value
     * @param b blue value
     * @param g green value
     * @return chat color
     */
    public static @Nonnull String color(float r, float b, float g) {
        return color(new Color(r, b, g));
    }

    /**
     * Convert color to chat color.
     *
     * @param r red value
     * @param b blue value
     * @param g green value
     * @param a alpha value
     * @return chat color
     */
    public static @Nonnull String color(float r, float b, float g, float a) {
        return color(new Color(r, b, g, a));
    }

    /**
     * Convert color to chat color.
     *
     * @param color color
     * @return chat color
     */
    public static @Nonnull String color(@Nonnull Color color) {
        String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        return color(hex);
    }

    /**
     * Get the last chat color
     * from the given text.
     *
     * @param text given text
     * @return last chat color codes
     */
    public static @Nonnull String getLastColors(@Nonnull String text) {
        Matcher colorMatcher = COLOR_PATTERN.matcher(text);
        Matcher formatMatcher = FORMAT_PATTERN.matcher(text);

        StringBuilder format = new StringBuilder();
        while (formatMatcher.find()) {
            String firstColor = formatMatcher.group("format");
            if (firstColor.equalsIgnoreCase("§r") || firstColor.equalsIgnoreCase("&r")) {
                format = new StringBuilder();
                continue;
            }
            format.append(firstColor);
        }

        String lastColor = "";
        while (colorMatcher.find()) {
            String firstColor = colorMatcher.group("color");
            if (firstColor.equalsIgnoreCase("§r") || firstColor.equalsIgnoreCase("&r")) {
                lastColor = "";
                continue;
            }
            lastColor = firstColor;
        }

        return lastColor + format;
    }
}
