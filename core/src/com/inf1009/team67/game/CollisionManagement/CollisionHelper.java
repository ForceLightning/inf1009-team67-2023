package com.inf1009.team67.game.CollisionManagement;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
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
            for (EntityBase entity : entityCollection.get(z).subList(0, entityCollection.get(z).size())) {
                if (entity instanceof DynamicBody) {
                    Integer index = entityCollection.get(z).indexOf(entity);
                    for (EntityBase other : entityCollection.get(z).subList(index + 1, entityCollection.get(z).size())) {
                        if (entity != other && entity instanceof DynamicBody) {
                            if (((DynamicBody<?>) entity).isCollidingWith((DynamicBody<?>) other)) {
                                handleCollision((DynamicBody<?>) entity, (DynamicBody<?>) other);
                            }
                        }
                    }
                }
            }
        }
        // apply the accumulators and update the entities
        float delta = Math.max(Gdx.graphics.getDeltaTime(), 1/60);
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z)) {
                if (entity instanceof DynamicBody) {
                    ((DynamicBody<?>) entity).applyFromAccumulator(delta);
                    entity.update(delta);
                }
            }
        }
    }
}
