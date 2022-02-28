package org.minestombrick.entitynavigator.minestom;

import org.minestombrick.entitynavigator.common.astar.AStarMachine;
import org.minestombrick.entitynavigator.common.astar.AStarState;
import org.minestombrick.entitynavigator.common.astar.SimpleAStarStorage;
import org.minestombrick.entitynavigator.minestom.examiners.MinecraftBlockExaminer;
import org.minestombrick.entitynavigator.common.path.Path;
import org.minestombrick.entitynavigator.common.pathfinder.VectorGoal;
import org.minestombrick.entitynavigator.common.pathfinder.VectorNode;
import org.minestombrick.entitynavigator.common.pathfinder.examiner.BlockExaminer;
import org.minestombrick.entitynavigator.common.pathfinder.math.Vector;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.EntityCreature;

import java.util.Set;

public class MinestomNavigator {

    private final static int RANGE = 50;
    private final static AStarMachine<VectorNode, VectorGoal<VectorNode>, Path> ASTAR =
            AStarMachine.createWithStorage(SimpleAStarStorage.FACTORY);

    private final static int MAX_ITERATIONS = 10000;
    private final static int MAX_ITERATIONS_PER_TICK = 5000;
    private final static float DISTANCE_MARGIN = 1f;

    private final MinestomBlockSource blockSource;
    private final EntityCreature entity;
    private final MinestomAgent agent;

    private AStarState<VectorNode, VectorGoal<VectorNode>> state;
    private Path path;
    private int iterations;

    public MinestomNavigator(EntityCreature entity) {
        this.entity = entity;
        this.agent = new MinestomAgent(entity);
        this.blockSource = new MinestomBlockSource(entity.getInstance());
    }

    private Set<BlockExaminer<?>> createBlockExaminers(MinestomBlockSource blockSource) {
        return Set.of(
                new MinecraftBlockExaminer(blockSource)
        );
    }

    public MinestomAgent agent() {
        return agent;
    }

    public boolean isPathing() {
        return state != null && path != null && !path.isFinished();
    }

    public Path currentPath() {
        return path;
    }

    public void pathTo(Point dest) {
        if ( entity.getInstance() == null ) {
            return;
        }

        Vector start = agent.position();
        if ( path != null && path.currentVector().distance(start) < 1) {
            start = path.currentVector();
        }

        reset();

        Vector vec = new Vector(dest.x(), dest.y(), dest.z());
        VectorGoal<VectorNode> goal = new VectorGoal<>(vec, (float) 1);
        VectorNode startnode = new VectorNode(start);
        state = ASTAR.stateFor(goal, startnode, createBlockExaminers(blockSource));
    }

    public void reset() {
        this.state = null;
        this.path = null;
        this.iterations = 0;
    }

    public void update() {
        if (state == null) {
            return;
        }

        if ( path == null ) {
            iterations += MAX_ITERATIONS_PER_TICK;
            if (iterations > MAX_ITERATIONS) {
                reset(); // no path found
                return;
            }

            path = ASTAR.run(state, MAX_ITERATIONS_PER_TICK);

            if (path == null) {
                return;
            }
        }

        if (state.isEmpty()) {
            return;
        }

        Vector from = agent.position();
        Vector dest = path.currentVector();
        double dX = dest.blockX() + .5 - from.x();
        double dY = dest.blockY() + .5 - from.y();
        double dZ = dest.blockZ() + .5 - from.z();
        double xzDistance = (dX * dX) + (dZ * dZ);

        if ( Math.abs(dY) < 1 && Math.sqrt(xzDistance) <= DISTANCE_MARGIN ) {
            if ( path.isFinished() ) {
                Vector velocity = dest.sub(from);
                entity.setVelocity(new Vec(velocity.x(), velocity.y(), velocity.z()));
                reset();
                return;
            }

            path.next();
        }

        agent.moveTo(path.currentVector());
    }


}
