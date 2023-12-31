package com.inf1009.team67.engine.interactionmanagement;

import java.util.ArrayList;
import java.util.TreeMap;

import com.inf1009.team67.engine.entitymanagement.EntityBase;
import com.inf1009.team67.engine.helpers.HandleEnum;
import com.inf1009.team67.engine.helpers.HelperBase;

public class InteractionHelper extends HelperBase<Interactable> {

    public InteractionHelper() {
        super();
        setHandleEnum(HandleEnum.INTERACTION);
    }

    public void handleInteraction(Interactable first, Interactable second) {
        if (first.isInteractingWith(second)) {
            first.handleInteraction(second);
            second.handleInteraction(first);
        }
    }

    @Override
    public <T extends Interactable, V extends Interactable> void handleAbstractInteraction(T a, V b) {
        handleInteraction(a, b);
    }

    @Override
    public void preLoop(TreeMap<Integer, ArrayList<EntityBase>> entityCollection, float delta) {
        return;
    }

    @Override
    public boolean conditionCheck(Interactable entity, Interactable other) {
        return entity.isInteractingWith(other);
    }
}
