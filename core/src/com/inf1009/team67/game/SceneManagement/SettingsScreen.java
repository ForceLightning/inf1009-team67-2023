package com.inf1009.team67.game.SceneManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inf1009.team67.game.Main.MyGdxGame;
import com.inf1009.team67.game.Main.Settings;


public class SettingsScreen extends ScreenBase {

    private Stage stage;
    private Skin skin;
    private TextButton backButton;
    private Slider masterVolumeSlider;
    private Slider musicVolumeSlider;
    private Slider effectsVolumeSlider;

    public SettingsScreen(MyGdxGame game) {
        super(game);
        game.assetsManager.manager.finishLoading();
        skin = game.assetsManager.manager.get("skin/metal-ui.json");
        backButton = new TextButton("Back", skin);
        stage = new Stage(new ScreenViewport());
        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        Table backArea = new Table();
        backArea.setPosition(0, 0);
        backArea.setSize(100, 100);
        table.setFillParent(true);
        // table.setDebug(true);
        stage.addActor(table);
        stage.addActor(backArea);
        backArea.add(backButton).size(110, 50);
        Label masterVolumeLabel = new Label("Master Volume", skin);
        Label musicVolumeLabel = new Label("Music Volume", skin);
        Label effectsVolumeLabel = new Label("Effects Volume", skin);
        masterVolumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
        musicVolumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
        effectsVolumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
        table.add(masterVolumeLabel).width(300).height(50).pad(10);
        table.add(masterVolumeSlider).width(300).height(50).pad(10);
        table.row();
        table.add(musicVolumeLabel).width(300).height(50).pad(10);
        table.add(musicVolumeSlider).width(300).height(50).pad(10);
        table.row();
        table.add(effectsVolumeLabel).width(300).height(50).pad(10);
        table.add(effectsVolumeSlider).width(300).height(50).pad(10);
        table.row();
        // temporary until we have asset manager in

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        masterVolumeSlider.setValue(Settings.getMasterVolume());
        musicVolumeSlider.setValue(Settings.getMusicVolume());
        effectsVolumeSlider.setValue(Settings.getEffectsVolume());
        // return to main screen button
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.changeScreen(ScreenManager.MENU);
                game.setScreen(ScreenEnum.MENU);
            }
        });

        masterVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.setMasterVolume(masterVolumeSlider.getValue());
            }
        });

        musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.setMusicVolume(musicVolumeSlider.getValue());
            }
        });

        effectsVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.setEffectsVolume(effectsVolumeSlider.getValue());
            }
        });
        // print out current volume settings
        System.out.println("Master Volume: " + Settings.getMasterVolume());
        System.out.println("Music Volume: " + Settings.getMusicVolume());
        System.out.println("Effects Volume: " + Settings.getEffectsVolume());

    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0x77, 0xff, 0xff, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when the screen size is changed
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.unfocusAll();
    }

    @Override
    public void dispose() {

    }
}
