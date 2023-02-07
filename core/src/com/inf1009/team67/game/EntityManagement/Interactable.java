package com.inf1009.team67.game.EntityManagement;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Action;

public interface Interactable {
    public void handleInteraction(Interactable interactable);
    public boolean isInteractingWith(Interactable other);
    public Circle getInteractionCircle();
    public void addReaction(Interactable interactable, Action reaction);
}
