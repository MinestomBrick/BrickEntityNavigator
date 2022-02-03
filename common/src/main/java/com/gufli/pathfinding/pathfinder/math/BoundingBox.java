package com.gufli.pathfinding.pathfinder.math;

public record BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {

    public static final BoundingBox EMPTY = new BoundingBox(0, 0, 0, 0, 0, 0);

    public BoundingBox add(int x, int y, int z) {
        return new BoundingBox(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z);
    }

}
