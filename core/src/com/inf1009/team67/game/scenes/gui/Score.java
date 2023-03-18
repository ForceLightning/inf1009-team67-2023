package com.inf1009.team67.game.scenes.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class Score {
    private Label scoreLabel;

    public Score(Skin skin) {
        scoreLabel = new Label("Score: 0", skin, "font", "white");
        scoreLabel.scaleBy(5f);
        scoreLabel.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 5f, Align.top);
    }

    public void drawLabel(SpriteBatch batch, int score) {
        scoreLabel.setText("Score: " + score);
        scoreLabel.draw(batch, 1f);
    }
}
