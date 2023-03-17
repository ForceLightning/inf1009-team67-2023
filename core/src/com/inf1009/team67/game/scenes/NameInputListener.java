package com.inf1009.team67.game.scenes;

import com.badlogic.gdx.Input.TextInputListener;

public class NameInputListener implements TextInputListener {
    private String name;
    @Override
    public void input (String text) {
        // TODO: Process and save the name
    }

    @Override
    public void canceled () {
        // TODO: Handle the cancel
    }

    public String getName() {
        return name;
    }
}
