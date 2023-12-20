package com.hakan.spinjection.utils;

import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * ProtocolVersion class to get the current
 * protocol version of the server and compare
 * protocol versions.
 */
public enum ProtocolVersion {

    v1_8_R3("v1_8_R3"),
    v1_9_R1("v1_9_R1"),
    v1_9_R2("v1_9_R2"),
    v1_10_R1("v1_10_R1"),
    v1_11_R1("v1_11_R1"),
    v1_12_R1("v1_12_R1"),
    v1_13_R1("v1_13_R1"),
    v1_13_R2("v1_13_R2"),
    v1_14_R1("v1_14_R1"),
    v1_15_R1("v1_15_R1"),
    v1_16_R1("v1_16_R1"),
    v1_16_R2("v1_16_R2"),
    v1_16_R3("v1_16_R3"),
    v1_17_R1("v1_17_R1"),
    v1_18_R1("v1_18_R1"),
    v1_18_R2("v1_18_R2"),
    v1_19_R1("v1_19_R1"),
    v1_19_1_R1("v1_19_1_R1"),
    v1_19_2_R1("v1_19_2_R1"),
    v1_19_R2("v1_19_R2"),
    v1_19_R3("v1_19_R3"),
    v1_20_R1("v1_20_R1"),
    v1_20_R2("v1_20_R2"),
    v1_20_R3("v1_20_R3"),
    ;


    public static ProtocolVersion CURRENT;

    static {
        String nmsVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
        String bukkitVersion = Bukkit.getBukkitVersion();

        if (bukkitVersion.contains("1.19.1"))
            CURRENT = v1_19_1_R1;
        else if (bukkitVersion.contains("1.19.2"))
            CURRENT = v1_19_2_R1;

        Arrays.stream(ProtocolVersion.values())
                .filter(protocolVersion -> nmsVersion.equals(protocolVersion.getKey()))
                .forEach(protocolVersion -> CURRENT = protocolVersion);
    }

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
