package com.gufli.pathfinding.astar;

import com.gufli.pathfinding.pathfinder.examiner.BlockExaminer;

import java.util.Set;

public record AStarState<N extends AStarNode, G extends AStarGoal<N>>(G goal, N start, AStarStorage storage, Set<BlockExaminer<?>> examiners) {

    public AStarNode bestNode() {
        return storage.bestNode();
    }

    public boolean isEmpty() {
        return storage.bestNode() == null;
    }

}