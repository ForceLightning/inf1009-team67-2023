package com.inf1009.team67.engine.interactionmanagement;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Action;

public interface Interactable {
    public void handleInteraction(Interactable other);
    public boolean isInteractingWith(Interactable other);
    public Circle getInteractionCircle();
    public void addReaction(Interactable other, Action reaction);
    public void setInteractionCircle(Circle circle);
    public void setInteractionCircle(float x, float y, float radius);
    public void setInteractionCircle(float x, float y);
    public void setInteractionCircle(float radius);
    public void setInteractable(boolean isInteractable);
    public boolean isInteractable();
}
