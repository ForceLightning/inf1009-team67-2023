package com.inf1009.team67.engine.interactionmanagement;

import java.util.ArrayList;
import java.util.TreeMap;

import com.inf1009.team67.engine.entitymanagement.EntityBase;

public class InteractionHelper {

    public void handleInteraction(Interactable first, Interactable second) {
        if (first.isInteractingWith(second)) {
            first.handleInteraction(second);
            second.handleInteraction(first);
        }
    }

    public void updateInteractions(TreeMap<Integer, ArrayList<EntityBase>> entityCollection) {
        for (Integer z: entityCollection.keySet()) {
            for (EntityBase entity: entityCollection.get(z)) {
                if (entity instanceof Interactable) {
                    Interactable interactable = (Interactable) entity;
                    for (Integer otherZ: entityCollection.keySet()) {
                        for (EntityBase otherEntity: entityCollection.get(otherZ)) {
                            if (otherEntity instanceof Interactable) {
                                Interactable otherInteractable = (Interactable) otherEntity;
                                if (interactable.isInteractingWith(otherInteractable)) {
                                    interactable.handleInteraction(otherInteractable);
                                    otherInteractable.handleInteraction(interactable);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
