package org.minestombrick.entitynavigator.common.pathfinder.examiner;

import org.minestombrick.entitynavigator.common.pathfinder.path.PathPoint;
import org.minestombrick.entitynavigator.common.pathfinder.source.BlockSource;

import java.util.List;

public abstract class NeighbourGeneratorBlockExaminer<T extends BlockSource<?>> extends BlockExaminer<T> {
    protected NeighbourGeneratorBlockExaminer(T blockSource) {
        super(blockSource);
    }

    public abstract List<PathPoint> neighbours(PathPoint point);
}
