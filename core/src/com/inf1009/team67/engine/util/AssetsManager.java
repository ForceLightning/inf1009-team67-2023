package com.inf1009.team67.engine.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.audio.Music;

public class AssetsManager {
    public final AssetManager manager = new AssetManager();
    public final String skin = "skin/metal-ui.json";
    public final String playingMusic = "music/loz_title.mp3";

    public final String background = "background.jpg";
    private Sprite sprite;

    public void queueAddSkin(){
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("skin/metal-ui.atlas");
        manager.load(skin, Skin.class, params);
    }

    public void queueAddMusic(){
        manager.load(playingMusic, Music.class);
    }

    public void queueAddBackground(){
        // Update the asset manager to load the queued assets
        manager.update();

        // Wait for the asset to finish loading
        //manager.finishLoading();

        sprite = new Sprite(new Texture(Gdx.files.internal("background.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void queueAddGameOverScreen(){
        //manager.load("gameover.jpg", Texture.class);

        manager.update();
        sprite = new Sprite(new Texture(Gdx.files.internal("gameover.png")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}