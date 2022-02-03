package com.gufli.pathfinding.ai;

import com.gufli.pathfinding.pathfinder.math.Vector;

public interface PathStrategy {

    boolean isPathing();

    Iterable<Vector> path();

    Vector target();

    void reset();

    void tick();

}
