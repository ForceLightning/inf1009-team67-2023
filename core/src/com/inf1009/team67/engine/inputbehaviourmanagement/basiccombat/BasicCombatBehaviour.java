package com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat;

import java.util.EnumSet;

public enum BasicCombatBehaviour {
    IDLE {
        @Override
        public BasicCombatBehaviour updateState(BasicCombatBehaviour behaviour, EnumSet<BasicCombatState> states) {
            if (behaviour == KILLED || states.contains(BasicCombatState.KILLED)) {
                return DEAD;
            } else if (behaviour == DEAD || states.contains(BasicCombatState.DEAD)) {
                return DEAD;
            } else if (states.contains(BasicCombatState.IN_AGGRO_RANGE) && !states.contains(BasicCombatState.HURT)) {
                return ATTACK;
            } else {
                return IDLE;
            }
        }
    },
    ATTACK {
        @Override
        public BasicCombatBehaviour updateState(BasicCombatBehaviour behaviour, EnumSet<BasicCombatState> states) {
            if (behaviour == KILLED || states.contains(BasicCombatState.KILLED)) {
                return DEAD;
            } else if (behaviour == DEAD || states.contains(BasicCombatState.DEAD)) {
                return DEAD;
            } else if (states.contains(BasicCombatState.HURT)) {
                return FLEE;
            } else if (states.contains(BasicCombatState.IN_AGGRO_RANGE)) {
                return ATTACK;
            } else {
                return IDLE;
            }
        }
    },
    FLEE {
        @Override
        public BasicCombatBehaviour updateState(BasicCombatBehaviour behaviour, EnumSet<BasicCombatState> states) {
            if (behaviour == KILLED || states.contains(BasicCombatState.KILLED)) {
                return DEAD;
            } else if (behaviour == DEAD || states.contains(BasicCombatState.DEAD)) {
                return DEAD;
            } else if (states.contains(BasicCombatState.HURT) && states.contains(BasicCombatState.IN_AGGRO_RANGE)) {
                return FLEE;
            } else if (states.contains(BasicCombatState.IN_AGGRO_RANGE) || states.contains(BasicCombatState.ATTACKING)) {
                return ATTACK;
            } else {
                return IDLE;
            }
        }
    },

    KILLED {
        @Override
        public BasicCombatBehaviour updateState(BasicCombatBehaviour behaviour, EnumSet<BasicCombatState> states) {
            return DEAD;
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
