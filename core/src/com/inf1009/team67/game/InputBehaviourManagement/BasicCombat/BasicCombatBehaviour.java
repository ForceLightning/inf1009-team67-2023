package com.inf1009.team67.game.InputBehaviourManagement.BasicCombat;

import java.util.EnumSet;

public enum BasicCombatBehaviour {
    IDLE {
        @Override
        public BasicCombatBehaviour updateState(BasicCombatBehaviour behaviour, EnumSet<BasicCombatState> states) {
            if (behaviour == DEAD || states.contains(BasicCombatState.DEAD)) {
                return DEAD;
            } else if (states.contains(BasicCombatState.IN_RANGE) && !states.contains(BasicCombatState.HURT)) {
                return ATTACK;
            } else {
                return IDLE;
            }
        }
    },
    ATTACK {
        @Override
        public BasicCombatBehaviour updateState(BasicCombatBehaviour behaviour, EnumSet<BasicCombatState> states) {
            if (behaviour == DEAD || states.contains(BasicCombatState.DEAD)) {
                return DEAD;
            } else if (states.contains(BasicCombatState.HURT)) {
                return FLEE;
            } else if (states.contains(BasicCombatState.IN_RANGE)) {
                return ATTACK;
            } else {
                return IDLE;
            }
        }
    },
    FLEE {
        @Override
        public BasicCombatBehaviour updateState(BasicCombatBehaviour behaviour, EnumSet<BasicCombatState> states) {
            if (behaviour == DEAD || states.contains(BasicCombatState.DEAD)) {
                return DEAD;
            } else if (states.contains(BasicCombatState.HURT) && states.contains(BasicCombatState.IN_RANGE)) {
                return FLEE;
            } else if (states.contains(BasicCombatState.IN_RANGE) || states.contains(BasicCombatState.ATTACKING)) {
                return ATTACK;
            } else {
                return IDLE;
            }
        }
    },
    DEAD {
        @Override
        public BasicCombatBehaviour updateState(BasicCombatBehaviour behaviour, EnumSet<BasicCombatState> states) {
            return DEAD;
        }
    };
    
    public abstract BasicCombatBehaviour updateState(BasicCombatBehaviour behaviour, EnumSet<BasicCombatState> states);
}
