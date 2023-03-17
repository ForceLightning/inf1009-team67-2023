package com.inf1009.team67.engine.helpers;

import com.inf1009.team67.engine.collisionmanagement.RigidBody;
import com.inf1009.team67.engine.controllables.ControllableCharacter;
import com.inf1009.team67.engine.interactionmanagement.Interactable;

public enum HandleEnum {
    COLLISION {
        public Class<?> getHandleType() {
            return RigidBody.class;
        }
    },
    INTERACTION {
        public Class<?> getHandleType() {
            return Interactable.class;
        }
    },
    COMBAT {
        public Class<?> getHandleType() {
            return ControllableCharacter.class;
        }
    };
}
