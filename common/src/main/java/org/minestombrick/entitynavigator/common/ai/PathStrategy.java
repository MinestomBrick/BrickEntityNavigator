package org.minestombrick.entitynavigator.common.ai;

import org.minestombrick.entitynavigator.common.pathfinder.math.Vector;

public interface PathStrategy {

    boolean isPathing();

    Iterable<Vector> path();

    Vector target();

    void reset();

    void tick();

}
