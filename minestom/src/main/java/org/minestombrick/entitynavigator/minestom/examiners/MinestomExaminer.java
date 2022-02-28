package org.minestombrick.entitynavigator.minestom.examiners;

import org.minestombrick.entitynavigator.minestom.MinestomBlockSource;
import org.minestombrick.entitynavigator.common.pathfinder.examiner.BlockExaminer;

public abstract class MinestomExaminer extends BlockExaminer<MinestomBlockSource> {
    protected MinestomExaminer(MinestomBlockSource blockSource) {
        super(blockSource);
    }
}
