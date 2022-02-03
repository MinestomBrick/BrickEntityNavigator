package com.gufli.pathfinding.minestom.examiners;

import com.gufli.pathfinding.minestom.MinestomBlockSource;
import com.gufli.pathfinding.pathfinder.examiner.BlockExaminer;

public abstract class MinestomExaminer extends BlockExaminer<MinestomBlockSource> {
    protected MinestomExaminer(MinestomBlockSource blockSource) {
        super(blockSource);
    }
}
