package me.kallix.magicswing.utils;

import org.bukkit.util.Vector;

public final class MathUtils {

    private static final int SCALE = 1 << 16;
    private static final float SCALING_FACTOR = (float) (SCALE / (2 * Math.PI));
    private static final float[] SINES = new float[SCALE];

    static {
        for (int i = 0; i < SCALE; ++i) {
            SINES[i] = (float) Math.sin((double) i * Math.PI * 2.0 / SCALE);
        }
    }

    // Refers to: https://quantummechanics.ucsd.edu/ph130a/130_notes/node216.html
    public static Vector getSphericalCoordinate(Vector from, Vector center, float addAngle) {
        double r = center.distance(from);

        float pitch = (float) Math.atan2(from.getZ() - center.getZ(), from.getX() - center.getX());

        float cosYaw = (float) ((from.getY() - center.getY()) / r);
        float sinYaw = (float) Math.sqrt(1 - cosYaw * cosYaw);

        double newX = center.getX() + r * sinYaw * cos(pitch + addAngle);
        double newY = center.getY() + r * cosYaw;
        double newZ = center.getZ() + r * sinYaw * sin(pitch + addAngle);

        return new Vector(newX, newY, newZ);
    }

    // Refers to mojang: net.minecraft.server.<...>.MathHelper.java
    public static float sin(float radians) {
        return SINES[(int) (radians * SCALING_FACTOR) & SCALE - 1];
    }

    // Refers to mojang: net.minecraft.server.<...>.MathHelper.java
    public static float cos(float radians) {
        return SINES[(int) ((radians + Math.PI / 2) * SCALING_FACTOR) & SCALE - 1];
    }
}
