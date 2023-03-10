package com.inf1009.team67.game.scenes;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inf1009.team67.engine.scenemanagement.ScreenBase;
import com.inf1009.team67.engine.util.AssetsManager;
import com.inf1009.team67.game.main.MyGdxGame;

public class InstructionsScreen extends ScreenBase {

    private Skin skin;

    private String instructions;
    
    private TextButton backButton;
    private Label instruction_box;
    private AssetsManager assetsManager = new AssetsManager();
    private SpriteBatch batch;
    private Sprite sprite;
    private Music playingMusic;


    public InstructionsScreen(MyGdxGame game) {
        super(game);
        //parent = myGdxGame;
        //Gdx.input.setInputProcessor(getStage());
        game.assetsManager.manager.finishLoading();
        playingMusic = game.assetsManager.manager.get("music/loz_title.mp3");
        skin = game.assetsManager.manager.get("skin/metal-ui.json");

        //instructions = game.assetsManager.manager.get("skin/instructions.txt");

        FileHandle handle = Gdx.files.local("instructions.txt");
        String text = handle.readString();
        ArrayList<String> newItems = new ArrayList<String>();
        String wordArray[] = text.split("\\r?\\n");
        for(String word: wordArray){
            newItems.add(word);
        }


        backButton = new TextButton("Back", skin);
        instruction_box = new Label(newItems.get(0), skin);
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

    }

    @Override
    public void resize(int width, int height) {
        // change the getStage()'s viewport when the screen size is changed
        getStage().getViewport().update(width, height, true);

    }

}

