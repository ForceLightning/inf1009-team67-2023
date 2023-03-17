package com.inf1009.team67.engine.helpers;

import java.util.ArrayList;
import java.util.TreeMap;

import com.inf1009.team67.engine.entitymanagement.EntityBase;

public abstract class HelperBase<B> {
    private HandleEnum handleEnum;

    public HandleEnum getHandleEnum() {
        return handleEnum;
    }

    public B cast (EntityBase entity) throws ClassCastException {
        return (B) entity;
    }

    public abstract <T extends B, V extends B> void handleAbstractInteraction(T a, V b);

    public abstract void preLoop(TreeMap<Integer, ArrayList<EntityBase>> entityCollection, float delta);
    
    public abstract boolean conditionCheck(B entity, B other);

    public void setHandleEnum(HandleEnum handleEnum) {
        this.handleEnum = handleEnum;
    }
}
