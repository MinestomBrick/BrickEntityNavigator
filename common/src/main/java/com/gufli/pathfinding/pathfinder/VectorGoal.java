package com.gufli.pathfinding.pathfinder;

import com.gufli.pathfinding.astar.AStarGoal;
import com.gufli.pathfinding.pathfinder.math.Vector;

public class VectorGoal<T extends VectorNode> implements AStarGoal<T> {

    final Vector goal;
    private final float leeway;

    public VectorGoal(Vector dest, float range) {
        // TODO
//        if (!MinecraftBlockExaminer.canStandIn(dest.getBlock().getType())) {
//            dest = MinecraftBlockExaminer.findValidLocationAbove(dest, 2);
//        }
        this.leeway = range;
        this.goal = dest;
        //this.goal = new Vector(dest.blockX(), dest.blockY(), dest.blockZ());
    }

    @Override
    public float g(T from, T to) {
        return (float) from.vector.distance(to.vector);
    }

    @Override
    public float initialCost(T node) {
        return (float) node.vector().distance(goal);
    }

    @Override
    public float h(T from) {
        return from.heuristic(goal);
    }

    @Override
    public boolean isFinished(T node) {
        return goal.equals(node.vector) || node.vector.distance(goal) <= leeway;
    }
}
