package com.inf1009.team67.game.Util;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.team67.game.EntityManagement.EntityBase;

public class Renderer {
    private SpriteBatch batch;
    public Renderer(SpriteBatch batch) {
        this.batch = batch;
    }

    public void render(ArrayList<EntityBase> entities) {
        // not sure if necessary if we are using stage to draw
        if (!batch.isDrawing()) {
            batch.begin();
        }
        for (EntityBase entity : entities) {
            entity.draw(batch, 1.0f);
        }
    }

    public void render(EntityBase entity) {
        // not sure if necessary if we are using stage to draw
        if (!batch.isDrawing()) {
            batch.begin();
        }
        Color color = entity.getColor();
        batch.setColor(color);
        entity.draw(batch, 1.0f);
    }
}
