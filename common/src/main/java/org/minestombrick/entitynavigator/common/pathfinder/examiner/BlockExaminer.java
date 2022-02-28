package org.minestombrick.entitynavigator.common.pathfinder.examiner;

import org.minestombrick.entitynavigator.common.pathfinder.path.PathPoint;
import org.minestombrick.entitynavigator.common.pathfinder.source.BlockSource;

public abstract class BlockExaminer<T extends BlockSource<?>> {

    protected final T blockSource;

    protected BlockExaminer(T blockSource) {
        this.blockSource = blockSource;
    }

    public abstract float cost(PathPoint point);

    public abstract PassableState isPassable(PathPoint point);

    public enum PassableState {
        IGNORE,
        PASSABLE,
        UNPASSABLE;

        public static PassableState fromBoolean(boolean b) {
            return b ? PASSABLE : UNPASSABLE;
        }
    }
}