package com.inf1009.team67.engine.helpers;

import java.util.ArrayList;
import java.util.TreeMap;

import com.inf1009.team67.engine.entitymanagement.EntityBase;

public interface HelperInterface {
    public Class<?> getHandleType();
    public <T, V> void handleAbstractInteraction(T a, V b);
    public void preLoop(TreeMap<Integer, ArrayList<EntityBase>> entityCollection, float delta);
    public void update(TreeMap<Integer, ArrayList<EntityBase>> entityCollection, float delta);
}
