package com.inf1009.team67.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.inf1009.team67.engine.scenemanagement.ScreenBase;
import com.inf1009.team67.engine.util.AssetsManager;
import com.inf1009.team67.game.main.MyGdxGame;
import com.inf1009.team67.game.main.Settings;

public class MenuScreen extends ScreenBase {

    private Skin skin;

    private AssetsManager assetsManager = AssetsManager.getInstance();
    private SpriteBatch batch;
    private Sprite sprite;
    private Music playingMusic;

    private TextButton Play;
    private TextButton leaderboard;
    private TextButton instructions;
    private TextButton settings;
    private TextButton exit;

    public MenuScreen(MyGdxGame game) {
        super(game);
        assetsManager.getManager().finishLoading();
        playingMusic = AssetsManager.getInstance().getMusic();
        skin = AssetsManager.getInstance().getSkin();

        Table table = new Table();

        table.setFillParent(true);
        table.setDebug(false);
        getStage().addActor(table);
        table.setSize(500, 500);
        table.setScale(1.0f);

        Play = new TextButton("Play!", skin);
        leaderboard = new TextButton("Leaderboard", skin);
        instructions = new TextButton("Instructions", skin);
        settings = new TextButton("Settings", skin);
        exit = new TextButton("Exit", skin);

        table.add(Play).fillX().uniformX().size(110,50);
        table.row().pad(10, 0, 5, 0);
        table.add(leaderboard).fillX().uniformX().size(110, 50);
        table.row().pad(5, 0, 5, 0);
        table.add(instructions).fillX().uniformX().size(110,50);
        table.row().pad(5, 0, 5, 0);
        table.add(settings).fillX().uniformX().size(110,50);
        table.row().pad(5, 0, 5, 0);
        table.add(exit).fillX().uniformX().size(110,50);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(getStage());
        playingMusic.play();
        playingMusic.setVolume(Settings.getMusicVolume() * Settings.getMasterVolume());
        playingMusic.setLooping(true);
        // print music status
        Play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(ScreenEnum.GAME);
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(ScreenEnum.SETTINGS);
            }
        });

        instructions.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){

                game.setScreen(ScreenEnum.INSTRUCTIONS);
            }
        });

        leaderboard.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){

                game.setScreen(ScreenEnum.LEADERBOARD);
            }
        });

        assetsManager.queueAddBackground();
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("background.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        //clear screen and prep for next set of images to be drawn
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //ask stage to do action and draw itself
        getStage().act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        getStage().draw();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin(); // Call the batch processing (Has to be called first for the sprite to be on top of UI)
        sprite.draw(batch);
        batch.end();
        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw(); // Drawing the level
    }

    @Override
    public void resize(int width, int height) {
        // change stage viewport when screen size is changed
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
        playingMusic.stop();
    }

    @Override
    public void dispose() {
        getStage().dispose();
        playingMusic.dispose();
        batch.dispose();
    }
}
