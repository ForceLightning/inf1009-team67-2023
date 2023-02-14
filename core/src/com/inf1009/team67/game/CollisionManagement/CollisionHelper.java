package com.inf1009.team67.game.CollisionManagement;

import java.util.ArrayList;
import java.util.TreeMap;

import com.inf1009.team67.game.EntityManagement.EntityBase;

public class CollisionHelper {
    public static <T extends RigidBody<?>> void handleCollision(T a, T b) {
        a.handleCollision(b);
        // b.handleCollision(a);
    }

    public void updateCollisions(TreeMap<Integer, ArrayList<EntityBase>> entityCollection, float delta) {
        // reest the accumulators
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z)) {
                if (entity instanceof RigidBody) {
                    ((RigidBody<?>) entity).resetAccumulator();
                }
            }
        }
        // update the accumulators
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z).subList(0, entityCollection.get(z).size())) {
                if (entity instanceof RigidBody) {
                    Integer index = entityCollection.get(z).indexOf(entity);
                    for (EntityBase other : entityCollection.get(z).subList(index + 1, entityCollection.get(z).size())) {
                        if (entity != other && entity instanceof RigidBody) {
                            if (((RigidBody<?>) entity).isCollidingWith((RigidBody<?>) other)) {
                                handleCollision((RigidBody<?>) entity, (RigidBody<?>) other);
                            }
                        }
                    }
                }
            }
        }
        // apply the accumulators
        delta = Math.max(delta, 1/60);
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z)) {
                if (entity instanceof RigidBody) {
                    ((RigidBody<?>) entity).applyFromAccumulator(delta);
                }
            }
        }
    }
}
