package org.minestombrick.entitynavigator.minestom.strategy;

import org.minestombrick.entitynavigator.common.ai.PathStrategy;
import org.minestombrick.entitynavigator.common.pathfinder.math.Vector;

public abstract class AbstractPathStrategy implements PathStrategy {

    @Override
    public boolean isPathing() {
        return false;
    }

    @Override
    public Iterable<Vector> path() {
        return null;
    }

    @Override
    public Vector target() {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public void tick() {

    }

}
