package com.inf1009.team67.engine.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import com.inf1009.team67.engine.collisionmanagement.CollisionHelper;
import com.inf1009.team67.engine.collisionmanagement.RigidBody;
import com.inf1009.team67.engine.entitymanagement.EntityBase;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatHelper;
import com.inf1009.team67.engine.interactionmanagement.InteractionHelper;

public class SuperHelper {
    private static SuperHelper instance = new SuperHelper();
    private CollisionHelper collisionHelper;
    private BasicCombatHelper basicCombatHelper;
    private InteractionHelper interactionHelper;
    private HelperBase[] helpers = new HelperBase[3];

    private SuperHelper() {

    }

    public static synchronized SuperHelper getInstance() {
        if (instance == null) {
            instance = new SuperHelper();
        }
        return instance;
    }

    public CollisionHelper getCollisionHelper() {
        return collisionHelper;
    }

    public void setCollisionHelper(CollisionHelper collisionHelper) {
        this.collisionHelper = collisionHelper;
    }

    public BasicCombatHelper getBasicCombatHelper() {
        return basicCombatHelper;
    }

    public void setBasicCombatHelper(BasicCombatHelper basicCombatHelper) {
        this.basicCombatHelper = basicCombatHelper;
    }

    public InteractionHelper getInteractionHelper() {
        return interactionHelper;
    }

    public void setInteractionHelper(InteractionHelper interactionHelper) {
        this.interactionHelper = interactionHelper;
    }

    public void setAllHelpers(CollisionHelper collisionHelper, BasicCombatHelper basicCombatHelper, InteractionHelper interactionHelper) {
        this.collisionHelper = collisionHelper;
        this.basicCombatHelper = basicCombatHelper;
        this.interactionHelper = interactionHelper;
        helpers[0] = collisionHelper;
        helpers[1] = basicCombatHelper;
        helpers[2] = interactionHelper;
    }

    public void updateHelpers(TreeMap<Integer, ArrayList<EntityBase>> entityCollection, float delta) {
        for (HelperBase helper: helpers) {
            helper.preLoop(entityCollection, delta);
        }
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z).subList(0, entityCollection.get(z).size())) {

                // if (entity instanceof RigidBody) {
                //     Integer index = entityCollection.get(z).indexOf(entity);
                //     for (EntityBase other : entityCollection.get(z).subList(index + 1, entityCollection.get(z).size())) {
                //         if (entity != other && other instanceof RigidBody) {
                //             if (((RigidBody<?>) entity).isCollidingWith((RigidBody<?>) other)) {
                //                 handleCollision((RigidBody<?>) entity, (RigidBody<?>) other);
                //             }
                //         }
                //     }
                // }
                Integer entityIndex = entityCollection.get(z).indexOf(entity);
                for (EntityBase other: entityCollection.get(z).subList(entityIndex + 1, entityCollection.get(z).size())) {
                    for (HelperBase helper: helpers) {
                        if (entity != other && entity.getRequiredHandles().contains(helper.getHandleEnum()) && other.getRequiredHandles().contains(helper.getHandleEnum())) {
                            try {
                                if (helper.conditionCheck(helper.cast(entity), helper.cast(other))) {
                                    helper.handleAbstractInteraction(helper.cast(entity), helper.cast(other));
                                }
                            } catch (ClassCastException e) {
                                System.out.println("ClassCastException in SuperHelper.updateHelpers()");
                                continue;
                            }
                        }
                    }
                }
            }
        }
    }

}
