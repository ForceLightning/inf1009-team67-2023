package com.inf1009.team67.game.SceneManagement;

import java.util.function.Consumer;

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
import com.inf1009.team67.game.Util.Pair;

public class SettingsScreen extends ScreenBase {

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
        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        Table backArea = new Table();
        backArea.setPosition(0, 0);
        backArea.setSize(100, 100);
        table.setFillParent(true);
        // table.setDebug(true);
        getStage().addActor(table);
        getStage().addActor(backArea);
        backArea.add(backButton).size(110, 50);
        Label masterVolumeLabel = new Label("Master Volume", skin);
        Label musicVolumeLabel = new Label("Music Volume", skin);
        Label effectsVolumeLabel = new Label("Effects Volume", skin);
        masterVolumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
        musicVolumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
        effectsVolumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
        for (Pair<Label, Slider> pair : new Pair[] {
                new Pair<Label, Slider>(masterVolumeLabel, masterVolumeSlider),
                new Pair<Label, Slider>(musicVolumeLabel, musicVolumeSlider),
                new Pair<Label, Slider>(effectsVolumeLabel, effectsVolumeSlider) }) {
            table.row();
            table.add(pair.first).width(300).height(50).pad(10);
            table.add(pair.second).width(300).height(50).pad(10);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(getStage());

        masterVolumeSlider.setValue(Settings.getMasterVolume());
        musicVolumeSlider.setValue(Settings.getMusicVolume());
        effectsVolumeSlider.setValue(Settings.getEffectsVolume());
        // return to main screen button
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // screenManager.changeScreen(ScreenManager.MENU);
                game.setScreen(ScreenEnum.MENU);
            }
        });

        // refer to the addSliderListener method below
        for (Pair<Slider, Consumer<Float>> pair : new Pair[] {
                new Pair<Slider, Consumer<Float>>(masterVolumeSlider, Settings::setMasterVolume),
                new Pair<Slider, Consumer<Float>>(musicVolumeSlider, Settings::setMusicVolume),
                new Pair<Slider, Consumer<Float>>(effectsVolumeSlider, Settings::setEffectsVolume) }) {
            addSliderListener(pair.first, pair.second);
        }

    }

    public void addSliderListener(Slider slider, Consumer<Float> setter) {
        // magic to get around the fact that the lambda needs to be final
        final Consumer<Float> finalSetter = setter;
        final Slider finalSlider = slider;
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                finalSetter.accept(finalSlider.getValue());
            }
        });
    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0x77, 0xff, 0xff, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our getStage() to do actions and draw itself
        getStage().act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        getStage().draw();

    }

    @Override
    public void resize(int width, int height) {
        // change the getStage()'s viewport when the screen size is changed
        getStage().getViewport().update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        getStage().unfocusAll();
    }

    @Override
    public void dispose() {

    }
}
