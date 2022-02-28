package org.minestombrick.entitynavigator.common.pathfinder.source;

import org.minestombrick.entitynavigator.common.pathfinder.math.Vector;
import org.minestombrick.entitynavigator.common.pathfinder.math.BoundingBox;

public interface BlockSource<T> {

    boolean isAvailable(int x, int y, int z);

    BoundingBox collisionBox(int x, int y, int z);

    default BoundingBox collisionBox(Vector pos) {
        return collisionBox(pos.blockX(), pos.blockY(), pos.blockZ());
    }

    T type(int x, int y, int z);

    default T type(Vector pos) {
        return type(pos.blockX(), pos.blockY(), pos.blockZ());
    }

}
