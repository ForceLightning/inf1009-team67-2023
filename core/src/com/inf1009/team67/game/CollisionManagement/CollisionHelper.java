package com.inf1009.team67.game.CollisionManagement;

import java.util.ArrayList;
import java.util.TreeMap;

import com.inf1009.team67.game.EntityManagement.EntityBase;

public class CollisionHelper {
    public static <T extends DynamicBody<?>> void handleCollision(T a, T b) {
        a.handleCollision(b);
        b.handleCollision(a);
    }

    public void updateCollisions(TreeMap<Integer, ArrayList<EntityBase>> entityCollection) {
        // reest the accumulators
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z)) {
                if (entity instanceof DynamicBody) {
                    ((DynamicBody<?>) entity).resetAccumulator();
                }
            }
        }
        // update the accumulators
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z)) {
                if (entity instanceof DynamicBody) {
                    DynamicBody<?> dynamicEntity = (DynamicBody<?>) entity;
                    for (Integer z2 : entityCollection.keySet()) {
                        for (EntityBase entity2 : entityCollection.get(z2)) {
                            if (entity2 instanceof DynamicBody) {
                                DynamicBody<?> dynamicEntity2 = (DynamicBody<?>) entity2;
                                if (dynamicEntity.isCollidingWith(dynamicEntity2)) {
                                    dynamicEntity.handleCollision(dynamicEntity2);
                                }
                            }
                        }
                    }
                }
            }
        }
        // apply the accumulators
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z)) {
                if (entity instanceof DynamicBody) {
                    ((DynamicBody<?>) entity).applyFromAccumulator();
                }
            }
        }
    }
}
