package com.inf1009.team67.game.SceneManagement;

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
import com.inf1009.team67.game.Main.MyGdxGame;
import com.inf1009.team67.game.Main.Settings;
import com.inf1009.team67.game.Util.AssetsManager;

public class MenuScreen extends ScreenBase {

    private Stage stage;

    private Skin skin;

    private AssetsManager assetsManager = new AssetsManager();
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
        //parent = myGdxGame;
        stage = new Stage(new ScreenViewport());
        //Gdx.input.setInputProcessor(stage);
        game.assetsManager.manager.finishLoading();
        playingMusic = game.assetsManager.manager.get("music/loz_title.mp3");
        skin = game.assetsManager.manager.get("skin/metal-ui.json");

        Table table = new Table();

        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
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
        Gdx.input.setInputProcessor(stage);
        playingMusic.play();
        playingMusic.setVolume(Settings.getMusicVolume() * Settings.getMasterVolume());
        // print music status
        Play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.changeScreen(ScreenManager.APPLICATION);
                game.setScreen(ScreenEnum.GAME);
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.changeScreen(ScreenManager.PREFERENCES);
                game.setScreen(ScreenEnum.SETTINGS);
            }
        });

        instructions.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){

                game.setScreen(ScreenEnum.INSTRUCTIONS);
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
        stage.unfocusAll();
        playingMusic.stop();
    }

    @Override
    public void dispose() {
        assetsManager.manager.dispose();
        stage.dispose();
        playingMusic.dispose();
        batch.dispose();
    }
}
