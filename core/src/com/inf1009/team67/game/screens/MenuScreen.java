package com.inf1009.team67.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inf1009.team67.game.AssetsManager;

public class MenuScreen extends ScreenManager {

    private Stage stage;

    private Skin skin;

    private AssetsManager assetsManager = new AssetsManager();

    private SpriteBatch batch;
    private Sprite sprite;
    private Music playingMusic;

    public MenuScreen(Game myGdxGame) {
        //parent = myGdxGame;
        super(myGdxGame);


        stage = new Stage(new ScreenViewport());
        //Gdx.input.setInputProcessor(stage);

        assetsManager.queueAddSkin();
        assetsManager.manager.finishLoading();
        skin = assetsManager.manager.get("skin/metal-ui.json");



    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();

        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
        table.setSize(500, 500);
        table.setScale(1.0f);

        TextButton Play = new TextButton("Play!", skin);
        TextButton leaderboard = new TextButton("Leaderboard", skin);
        TextButton instructions = new TextButton("Instructions", skin);
        TextButton settings = new TextButton("Settings", skin);
        TextButton exit = new TextButton("Exit", skin);

        table.add(Play).fillX().uniformX().size(110,50);
        table.row().pad(10, 0, 5, 0);
        table.add(leaderboard).fillX().uniformX().size(110, 50);
        table.row().pad(5, 0, 5, 0);
        table.add(instructions).fillX().uniformX().size(110,50);
        table.row().pad(5, 0, 5, 0);
        table.add(settings).fillX().uniformX().size(110,50);
        table.row().pad(5, 0, 5, 0);
        table.add(exit).fillX().uniformX().size(110,50);




        Play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.changeScreen(ScreenManager.APPLICATION);
                game.setScreen(new GameScreen(game));
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.changeScreen(ScreenManager.PREFERENCES);
                game.setScreen(new SettingsScreen(game));
            }
        });

        assetsManager.queueAddMusic();
        assetsManager.manager.finishLoading();
        playingMusic = assetsManager.manager.get("music/loz_title.mp3");
        playingMusic.play();

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
        //Gdx.gl.glClearColor(0f, 0.4f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //ask stage to do action and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin(); // Call the batch processing (Has to be called first for the sprite to be on top of UI)
        sprite.draw(batch);
        batch.end();


        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw(); // Drawing the level




    }

    @Override
    public void resize(int width, int height) {
        // change stage viewport when screen size is changed
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

    }

    @Override
    public void dispose() {
        assetsManager.manager.dispose();
        stage.dispose();

    }
}
