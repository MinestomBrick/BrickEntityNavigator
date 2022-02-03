package com.gufli.pathfinding.minestom.strategy;

import com.gufli.pathfinding.ai.PathStrategy;
import com.gufli.pathfinding.pathfinder.math.Vector;

public class FollowEntityPathStrategy implements PathStrategy {

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
