package org.minestombrick.entitynavigator.common.pathfinder.math;

import org.jetbrains.annotations.Contract;

public record Vector(double x, double y, double z) {

    @Contract(pure = true)
    public int blockX() {
        return (int) Math.floor(x);
    }

    /**
     * Gets the floored value of the X component
     *
     * @return the block X
     */
    @Contract(pure = true)
    public int blockY() {
        return (int) Math.floor(y);
    }

    /**
     * Gets the floored value of the X component
     *
     * @return the block X
     */
    @Contract(pure = true)
    public int blockZ() {
        return (int) Math.floor(z);
    }

    public Vector withX(double x) {
        return new Vector(x, y, z);
    }

    public Vector withY(double Y) {
        return new Vector(x, y, z);
    }

    public Vector withZ(double z) {
        return new Vector(x, y, z);
    }

    public Vector add(double x, double y, double z) {
        return new Vector(this.x + x, this.y + y, this.z + z);
    }

    public Vector add(Vector other) {
        return add(other.x, other.y, other.z);
    }

    public Vector sub(double x, double y, double z) {
        return new Vector(this.x - x, this.y - y, this.z - z);
    }

    public Vector sub(Vector other) {
        return sub(other.x, other.y, other.z);
    }

    public double distance(Vector other) {
        return Math.sqrt(square(other.x - x) + square(other.y - y) + square(other.z - z));
    }

    private double square(double val) {
        return val * val;
    }

    @Override
    public String toString() {
        return "Vector[x=" + x + ",y=" + y + ",z=" + z + "]";
    }
}
