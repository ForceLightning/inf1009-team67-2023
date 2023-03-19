package com.inf1009.team67.engine.collisionmanagement;

import java.util.ArrayList;
import java.util.TreeMap;

import com.inf1009.team67.engine.entitymanagement.EntityBase;
import com.inf1009.team67.engine.helpers.HandleEnum;
import com.inf1009.team67.engine.helpers.HelperBase;

public class CollisionHelper extends HelperBase<RigidBody<?>> {
    
    public CollisionHelper() {
        setHandleEnum(HandleEnum.COLLISION);
    }

    public static <T extends RigidBody<?>, V extends RigidBody<?>> void handleCollision(T a, V b) {
        a.handleCollision(b);
        b.handleCollision(a);
    }

    @Override
    public RigidBody<?> cast(EntityBase entity) throws ClassCastException {
        return (RigidBody<?>) entity;
    }

    @Override
    public <T extends RigidBody<?>, V extends RigidBody<?>> void handleAbstractInteraction(T a, V b) {
        handleCollision(a, b);
    }

    @Override
    public void preLoop(TreeMap<Integer, ArrayList<EntityBase>> entityCollection, float delta) {
        // reest the accumulators
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z)) {
                if (entity instanceof RigidBody) {
                    ((RigidBody<?>) entity).resetAccumulator();
                }
            }
        }
    }

    @Override
    public boolean conditionCheck(RigidBody<?> entity, RigidBody<?> other) {
        return entity.isCollidingWith(other);
    }


}
