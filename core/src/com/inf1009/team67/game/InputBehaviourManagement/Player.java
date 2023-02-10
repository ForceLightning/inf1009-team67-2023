package com.inf1009.team67.game.InputBehaviourManagement;

import com.inf1009.team67.game.EntityManagement.Entity;

public class Player extends Entity implements State{
    // Constructor
    public Player(float x, float y, int z, float width, float height, String texturePath, float movementSpeed, int colorHex) {
        super(x, y, z, width, height, texturePath, movementSpeed, colorHex);
    }

    //State variables
    private playerStates current_state;
//    playerStates idle = playerStates.idle;
//    playerStates walking = playerStates.walking;
//    playerStates falling = playerStates.falling;
//    playerStates jumping = playerStates.jumping;
//    playerStates dead = playerStates.dead;
//
//    //Setters
//    public void setIdle(playerStates idle) {
//        this.idle = idle;
//    }
//    public void setWalking(playerStates walking) {
//        this.walking = walking;
//    }
//    public void setFalling(playerStates falling) {
//        this.falling = falling;
//    }
//    public void setJumping(playerStates jumping) {
//        this.jumping = jumping;
//    }
//    public void setDead(playerStates dead) {
//        this.dead = dead;
//    }
//
//    //Getters
//    public playerStates getIdle() {
//        return idle;
//    }
//    public playerStates getWalking() {
//        return walking;
//    }
//    public playerStates getFalling() {
//        return falling;
//    }
//    public playerStates getJumping() {
//        return jumping;
//    }
//    public playerStates getDead() {
//        return dead;
//    }


    // Put in new class file of its own?
    public void stateChange(){
        switch(current_state)
        {
            case idle:
                break;

            case walking:
                //

            case falling:
                // Check for collision with ground/ landing
                // If landed, set speed to 0 and set current state to idle

            case jumping:
                // If in air, check falling state;

            case dead:
                // If dead, cease all movements. Wait for respawn
                this.setMovementSpeed(0);
        }
    }


    // State Interface functions
    public void enter(){// Enter new state}
    public void exit(){// Exit current state}
    public void input(event){
        return State //based on input event;
    }
    public void update(event){
        return State // If state changes, update current state based on latest input;
    }
}