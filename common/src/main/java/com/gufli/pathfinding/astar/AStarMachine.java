package com.gufli.pathfinding.astar;

import com.gufli.pathfinding.path.Path;
import com.gufli.pathfinding.pathfinder.examiner.BlockExaminer;

import java.util.Set;
import java.util.function.Supplier;

public class AStarMachine<N extends AStarNode, G extends AStarGoal<N>, P extends Path> {

    private Supplier<AStarStorage> storageSupplier;

    private AStarMachine(Supplier<AStarStorage> storage) {
        this.storageSupplier = storage;
    }

    private void f(AStarGoal<N> goal, N node, N neighbour) {
        float g = node.g + goal.g(node, neighbour); // calculate the cost from the start additively
        float h = goal.h(neighbour);

        neighbour.g = g;
        neighbour.h = h;
    }

    private AStarStorage initialiseStorage(AStarGoal<N> goal, N start) {
        AStarStorage storage = storageSupplier.get();
        storage.open(start);
        start.g = goal.initialCost(start);
        start.h = 0;
        return storage;
    }

    /**
     * Creates an {@link AStarState} that can be reused across multiple invocations of {{@link #run(AStarState, int)}.
     *
     * @see #run(AStarState, int)
     * @param goal
     *            The {@link AStarGoal} state
     * @param start
     *            The starting {@link AStarNode}
     * @return The created state
     */
    public AStarState<N,G> stateFor(G goal, N start, Set<BlockExaminer<?>> examiners) {
        return new AStarState<N,G>(goal, start, initialiseStorage(goal, start), examiners);
    }

    /**
     * Runs the {@link AStarState} until a plan is found.
     *
     * @see #run(AStarState)
     * @param state
     *            The state to use
     * @return The generated {@link Path}, or <code>null</code>
     */
    public P run(AStarState<N,G> state) {
        return run(state, -1);
    }

    /**
     * Runs the machine using the given {@link AStarState}'s {@link AStarStorage}. Can be used to provide a continuation
     * style usage of the A* algorithm.
     *
     * @param state
     *            The state to use
     * @param maxIterations
     *            The maximum number of iterations
     * @return The generated {@link Path}, or <code>null</code> if not found
     */
    public P run(AStarState<N,G> state, int maxIterations) {
        return run(state.storage(), state.goal(), state.examiners(), maxIterations);
    }

    @SuppressWarnings("unchecked")
    private P run(AStarStorage storage, AStarGoal<N> goal, Set<BlockExaminer<?>> examiners, int maxIterations) {
        N node;
        int iterations = 0;
        while (true) {
            node = (N) storage.removeBestNode();
            if (node == null) {
                return null;
            }
            if (goal.isFinished(node)) {
                return (P) node.buildPath();
            }
            storage.close(node);
            for (AStarNode neighbour : node.neighbours(examiners)) {
                f(goal, node, (N) neighbour);
                if (!storage.shouldExamine((N) neighbour))
                    continue;
                storage.open((N) neighbour);
                neighbour.parent = node;
            }
            if (maxIterations >= 0 && iterations++ >= maxIterations) {
                node = (N) storage.allTimeBestNode();
                if ( node == null ) {
                    return null;
                }
                return (P) node.buildPath();
            }
        }
    }

    /**
     * Runs the machine until a plan is either found or cannot be generated.
     *
     * @see #runFully(AStarGoal, AStarNode, Set, int)
     */
    public P runFully(AStarGoal<N> goal, N start, Set<BlockExaminer<?>> examiners) {
        return runFully(goal, start, examiners, -1);
    }

    /**
     * Runs the machine fully until the iteration limit has been exceeded. This will use the supplied goal and start to
     * generate neighbours until the goal state has been reached using the A* algorithm.
     *
     * @param goal
     *            The {@link AStarGoal} state
     * @param start
     *            The starting {@link AStarNode}
     * @param iterations
     *            The number of iterations to run the machine for
     * @return The generated {@link Path}, or <code>null</code> if it was not found
     */
    public P runFully(AStarGoal<N> goal, N start, Set<BlockExaminer<?>> examiners, int iterations) {
        return run(initialiseStorage(goal, start), goal, examiners, iterations);
    }

    /**
     * Sets the {@link Supplier} to use to generate instances of {@link AStarStorage} for use while searching.
     *
     * @param newSupplier
     *            The new supplier to use
     */
    public void setStorageSupplier(Supplier<AStarStorage> newSupplier) {
        storageSupplier = newSupplier;
    }

    /**
     * Creates an AStarMachine that uses the given {@link Supplier <AStarStorage>} to create {@link AStarStorage}
     * instances.
     *
     * @param storageSupplier
     *            The storage supplier
     * @return The created instance
     */
    public static <N extends AStarNode, G extends AStarGoal<N>, P extends Path> AStarMachine<N, G, P> createWithStorage(Supplier<AStarStorage> storageSupplier) {
        return new AStarMachine<N,G,  P>(storageSupplier);
    }
}
