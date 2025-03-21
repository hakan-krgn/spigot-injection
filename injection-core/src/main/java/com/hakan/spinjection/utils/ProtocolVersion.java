package com.hakan.spinjection.utils;

import org.bukkit.Bukkit;

import javax.annotation.Nonnull;

/**
 * ProtocolVersion class to get the current
 * protocol version of the server and compare
 * protocol versions.
 */
public enum ProtocolVersion {

    v1_8("v1_8_R1"),
    v1_8_1("v1_8_R1"),
    v1_8_2("v1_8_R1"),
    v1_8_3("v1_8_R2"),
    v1_8_4("v1_8_R3"),
    v1_8_5("v1_8_R3"),
    v1_8_6("v1_8_R3"),
    v1_8_7("v1_8_R3"),
    v1_8_8("v1_8_R3"),
    v1_8_9("v1_8_R3"),

    v1_9("v1_9_R1"),
    v1_9_1("v1_9_R1"),
    v1_9_2("v1_9_R1"),
    v1_9_4("v1_9_R2"),

    v1_10("v1_10_R1"),
    v1_10_1("v1_10_R1"),
    v1_10_2("v1_10_R1"),

    v1_11("v1_11_R1"),
    v1_11_1("v1_11_R1"),
    v1_11_2("v1_11_R1"),

    v1_12("v1_12_R1"),
    v1_12_1("v1_12_R1"),
    v1_12_2("v1_12_R1"),

    v1_13("v1_13_R1"),
    v1_13_1("v1_13_R2"),
    v1_13_2("v1_13_R2"),

    v1_14("v1_14_R1"),
    v1_14_1("v1_14_R1"),
    v1_14_2("v1_14_R1"),
    v1_14_3("v1_14_R1"),
    v1_14_4("v1_14_R1"),

    v1_15("v1_15_R1"),
    v1_15_1("v1_15_R1"),
    v1_15_2("v1_15_R1"),

    v1_16("v1_16_R1"),
    v1_16_1("v1_16_R1"),
    v1_16_2("v1_16_R2"),
    v1_16_3("v1_16_R2"),
    v1_16_4("v1_16_R3"),
    v1_16_5("v1_16_R3"),

    v1_17("v1_17_R1"),
    v1_17_1("v1_17_R1"),

    v1_18("v1_18_R1"),
    v1_18_1("v1_18_R1"),
    v1_18_2("v1_18_R2"),

    v1_19("v1_19_R1"),
    v1_19_1("v1_19_R1"),
    v1_19_2("v1_19_R1"),
    v1_19_3("v1_19_R2"),
    v1_19_4("v1_19_R3"),

    v1_20("v1_20_R1"),
    v1_20_1("v1_20_R1"),
    v1_20_2("v1_20_R2"),
    v1_20_3("v1_20_R3"),
    v1_20_4("v1_20_R3"),
    v1_20_5("v1_20_R4"),
    v1_20_6("v1_20_R4"),

    v1_21("v1_21_R1"),
    v1_21_1("v1_21_R1"),
    v1_21_2("v1_21_R2"),
    v1_21_3("v1_21_R2"),
    v1_21_4("v1_21_R3");


    public static final ProtocolVersion CURRENT = ProtocolVersion.valueOf(
            "v" + Bukkit.getBukkitVersion().split("-")[0].replace(".", "_"));

    /**
     * Checks if the current version is newer than the given version.
     *
     * @param version The version to check.
     * @return True if the current version is newer or equal to the given version.
     */
    public static boolean isNewer(@Nonnull ProtocolVersion version) {
        return CURRENT.compareNewer(version);
    }

    /**
     * Checks if the current version is newer than or equal to the given version.
     *
     * @param version The version to check.
     * @return True if the current version is older or equal to the given version.
     */
    public static boolean isNewerOrEqual(@Nonnull ProtocolVersion version) {
        return CURRENT.compareNewerOrEqual(version);
    }

    /**
     * Checks if the current version is older the given version.
     *
     * @param version The version to check.
     * @return True if the current version is older or equal to the given version.
     */
    public static boolean isOlder(@Nonnull ProtocolVersion version) {
        return CURRENT.compareOlder(version);
    }

    /**
     * Checks if the current version is older or equal to the given version.
     *
     * @param version The version to check.
     * @return True if the current version is older or equal to the given version.
     */
    public static boolean isOlderOrEqual(@Nonnull ProtocolVersion version) {
        return CURRENT.compareOlderOrEqual(version);
    }



    private final String key;

    /**
     * Constructor.
     *
     * @param key The protocol version key.
     */
    ProtocolVersion(@Nonnull String key) {
        this.key = key;
    }

    /**
     * Gets the protocol version key.
     *
     * @return The protocol version key.
     */
    @Nonnull
    public String getKey() {
        return this.key;
    }

    /**
     * Checks if the protocol version is newer than the given version.
     *
     * @param version The version to check.
     * @return True if the protocol version is newer or equal to the given version.
     */
    public boolean compareNewer(@Nonnull ProtocolVersion version) {
        return this.ordinal() > version.ordinal();
    }

    /**
     * Checks if the protocol version is newer than or equal to the given version.
     *
     * @param version The version to check.
     * @return True if the protocol version is older or equal to the given version.
     */
    public boolean compareNewerOrEqual(@Nonnull ProtocolVersion version) {
        return this.ordinal() >= version.ordinal();
    }

    /**
     * Checks if the protocol version is older the given version.
     *
     * @param version The version to check.
     * @return True if the protocol version is older or equal to the given version.
     */
    public boolean compareOlder(@Nonnull ProtocolVersion version) {
        return this.ordinal() < version.ordinal();
    }

    /**
     * Checks if the protocol version is older or equal to the given version.
     *
     * @param version The version to check.
     * @return True if the protocol version is older or equal to the given version.
     */
    public boolean compareOlderOrEqual(@Nonnull ProtocolVersion version) {
        return this.ordinal() <= version.ordinal();
    }
}
