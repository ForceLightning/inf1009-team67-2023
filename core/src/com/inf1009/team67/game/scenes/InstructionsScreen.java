package com.inf1009.team67.game.scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.inf1009.team67.engine.scenemanagement.ScreenBase;
import com.inf1009.team67.engine.util.AssetsManager;
import com.inf1009.team67.game.main.MyGdxGame;

public class InstructionsScreen extends ScreenBase {

    private Skin skin;
    private TextButton backButton;
    private Label instruction_box;
    private Music playingMusic;
    private SpriteBatch batch;
    private Sprite sprite;
    private AssetsManager assetsManager = AssetsManager.getInstance();
    private TextButton exit;


    public InstructionsScreen(MyGdxGame game) {
        super(game);
        AssetsManager.getInstance().getManager().finishLoading();
        playingMusic = AssetsManager.getInstance().getMusic();
        skin = AssetsManager.getInstance().getSkin();
        FileHandle handle = Gdx.files.local("instructions.txt");
        String text = handle.readString();
        assetsManager.queueAddInstructions();
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("instructions.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backButton = new TextButton("Back", skin);
        instruction_box = new Label(text, skin);
        instruction_box.setWrap(true);
        instruction_box.setAlignment(Align.center);

        Table table = new Table();
        Table backArea = new Table();

        backArea.setPosition(0, 0);
        backArea.setSize(100, 100);
        table.setFillParent(true);

        getStage().addActor(table);
        getStage().addActor(backArea);
        
        table.add(instruction_box).size(500, 400);
        backArea.add(backButton).size(110, 50);

        
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(getStage());

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // screenManager.changeScreen(ScreenManager.MENU);
                game.setScreen(ScreenEnum.MENU);
            }
        });}

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0x77, 0xff, 0xff, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our getStage() to do actions and draw itself
        getStage().act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        getStage().draw();

        batch.begin(); // Call the batch processing (Has to be called first for the sprite to be on top of UI)
        sprite.draw(batch);
        batch.end();
        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw(); // Drawing the level


    }

    @Override
    public void resize(int width, int height) {
        // change the getStage()'s viewport when the screen size is changed
        getStage().getViewport().update(width, height, true);

    }

}

