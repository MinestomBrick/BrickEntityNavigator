package org.minestombrick.entitynavigator.minestom;

import org.minestombrick.entitynavigator.common.path.Agent;
import org.minestombrick.entitynavigator.common.pathfinder.math.Vector;
import net.minestom.server.collision.CollisionUtils;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.utils.position.PositionUtils;

public class MinestomAgent implements Agent {

    private final LivingEntity entity;

    public MinestomAgent(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity entity() {
        return entity;
    }

    @Override
    public Vector position() {
        return new Vector(entity.getPosition().x(), entity.getPosition().y(), entity.getPosition().z());
    }

    @Override
    public void moveTo(Vector target) {
        Vec vec = new Vec(target.blockX() + .5, target.blockY(), target.blockZ() + .5);
        double speed = .15d; //entity.getAttributeValue(Attribute.MOVEMENT_SPEED);

        final Pos position = entity.getPosition();
        final double dx = vec.x() - position.x();
        final double dy = vec.y() - position.y();
        final double dz = vec.z() - position.z();

        // the purpose of these few lines is to slow down entities when they reach their destination
        final double distSquared = dx * dx + dy * dy + dz * dz;
        if (speed > distSquared) {
            speed = distSquared;
        }

        final double radians = Math.atan2(dz, dx);
        final double speedX = Math.cos(radians) * speed;
        final double speedY = dy * speed;
        final double speedZ = Math.sin(radians) * speed;

        final float yaw = PositionUtils.getLookYaw(dx, dz);
        final float pitch = PositionUtils.getLookPitch(dx, dy, dz);

        // Prevent ghosting
        final var physicsResult = CollisionUtils.handlePhysics(entity, new Vec(speedX, speedY, speedZ));

        // move entity
        Pos finalpos = physicsResult.newPosition().withView(yaw, pitch); //.withView(entity.getPosition().yaw(), entity.getPosition().pitch());
        this.entity.refreshPosition(finalpos);

        if (position.y() < vec.y()) {
            this.jump(1);
        }
    }

    public void jump(float height) {
        this.entity.setVelocity(entity.getVelocity().withY(2.5f * height));
    }

}
