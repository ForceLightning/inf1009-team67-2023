package com.inf1009.team67.engine.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.audio.Music;

public class AssetsManager {
    private final AssetManager manager = new AssetManager();
    private final String skinFilePath = "skin/metal-ui.json";
    private final String playingMusicFilePath = "music/loz_title.mp3";
    private final String backgroundFilePath = "background.jpg";
    private final String backgroundsubFilePath = "backgroundsub.jpg";
    private final String instructionsFilePath = "instructions.jpg";
    private Skin skin;
    private Music music;
    private Sprite background;
    private Sprite backgroundsub;
    private Sprite instructions;
    private static AssetsManager instance = null;

    private AssetsManager() {
        super();
        instance = this;
        manager.finishLoading();
    }

    public void init() {
        manager.finishLoading();
        queueAddSkin();
        queueAddMusic();
        queueAddBackground();
        queueAddInstructions();
        queueAddBackgroundsub();
    }

    public static AssetsManager getInstance() {
        if (instance == null) {
            instance = new AssetsManager();
        }
        return instance;
    }

    public AssetManager getManager() {
        return manager;
    }


    public void queueAddSkin(){
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("skin/metal-ui.atlas");
        manager.load(skinFilePath, Skin.class, params);
    }

    public Skin getSkin() {
        if (skin == null) {
            skin = manager.get(skinFilePath);
        }
        return skin;
    }

    public void queueAddMusic(){
        manager.load(playingMusicFilePath, Music.class);
    }

    public Music getMusic() {
        if (music == null) {
            music = manager.get(playingMusicFilePath);
        }
        return music;
    }

    public void queueAddBackground(){
        // Update the asset manager to load the queued assets
        manager.update();

        // Wait for the asset to finish loading

        background = new Sprite(new Texture(Gdx.files.internal(backgroundFilePath)));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void queueAddInstructions(){
        // Update the asset manager to load the queued assets
        manager.update();

        // Wait for the asset to finish loading

        instructions = new Sprite(new Texture(Gdx.files.internal(instructionsFilePath)));
        instructions.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    public void queueAddBackgroundsub(){
        // Update the asset manager to load the queued assets
        manager.update();

        // Wait for the asset to finish loading

        backgroundsub = new Sprite(new Texture(Gdx.files.internal(backgroundsubFilePath)));
        backgroundsub.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }



    public Sprite getBackground() {
        if (background == null) {
            background = new Sprite(new Texture(Gdx.files.internal(backgroundFilePath)));
            background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        return background;
    }
}