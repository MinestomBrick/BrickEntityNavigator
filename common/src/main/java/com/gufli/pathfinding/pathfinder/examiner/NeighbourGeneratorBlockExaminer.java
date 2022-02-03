package com.gufli.pathfinding.pathfinder.examiner;

import com.gufli.pathfinding.pathfinder.path.PathPoint;
import com.gufli.pathfinding.pathfinder.source.BlockSource;

import java.util.List;

public abstract class NeighbourGeneratorBlockExaminer<T extends BlockSource<?>> extends BlockExaminer<T> {
    protected NeighbourGeneratorBlockExaminer(T blockSource) {
        super(blockSource);
    }

    public abstract List<PathPoint> neighbours(PathPoint point);
}
