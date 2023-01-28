package com.inf1009.team67.game.EntityManagement;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Action;

public class InteractableEntity extends EntityBase implements Interactable {
    public InteractableEntity() {
        super();
    }
    public boolean isInteractingWith(Interactable other) {
        return this.getInteractionCircle().overlaps(other.getInteractionCircle());
    }

    public Circle getInteractionCircle() {
        return super.getInteractionCircle();
    }

    public void handleInteraction(Interactable other) {
        // TODO Auto-generated method stub
    }

    public void addReaction(Interactable other, Action reaction) {
        // TODO Auto-generated method stub
    }
}
