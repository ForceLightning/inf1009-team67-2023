package com.inf1009.team67.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.inf1009.team67.engine.scenemanagement.ScreenBase;
import com.inf1009.team67.game.main.MyGdxGame;

public class EndScreen extends ScreenBase {
    private Table scoreTable;
    private Skin skin;
    private TextButton backButton;
    private Label name;
    private NameInputListener listener;

    public EndScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);
        game.assetsManager.manager.finishLoading();
        skin = game.assetsManager.manager.get("skin/metal-ui.json");
        backButton = new TextButton("Back", skin);
        Table backArea = new Table();
        scoreTable = new Table();
        scoreTable.setFillParent(true);
        backArea.setPosition(0, 0);
        backArea.setSize(100, 100);
        getStage().addActor(backArea);
        getStage().addActor(scoreTable);
        backArea.add(backButton).size(110, 50);
        FileHandle file = Gdx.files.internal("leaderboard.csv");
        String text = file.readString();
        String[] lines = text.split("\\r?\\n");
        // Add the header
        scoreTable.row();
        Label rankLabel = new Label("Rank", skin);
        Label scoreLabel = new Label("Score", skin);
        Label nameLabel = new Label("Name", skin);
        float width = Gdx.graphics.getWidth() * 0.20f;
        float padding = Gdx.graphics.getWidth() / 16.0f;
        scoreTable.add(rankLabel).width(width).pad(padding);
        scoreTable.add(scoreLabel).width(width).pad(padding);
        scoreTable.add(nameLabel).width(width).pad(padding);
        Label rankAttained = new Label("NIL", skin);
        scoreTable.row();
        // Check if the file is empty
        if (lines.length == 0) {
            rankAttained.setText("1");
        } else {
            // Add the scores
            rankAttained.setText(((Integer) (lines.length + 1)).toString());
            for (String line : lines) {
                String[] parts = line.split(",");
                Integer priorTopScore = Integer.parseInt(parts[2]);
                if (game.getScore() > priorTopScore) {
                    rankAttained.setText(parts[0]);
                    break;
                }
            }
        }
        scoreTable.add(rankAttained).width(width).pad(padding);
        String scoreString = ((Integer) game.getScore()).toString();
        Label score = new Label(scoreString, skin);
        // Handle input for the name
        name = new Label("", skin);
        scoreTable.add(score).width(width).pad(padding);
        scoreTable.add(name).width(width).pad(padding);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(getStage());
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(ScreenEnum.MENU);
            }
        });

        listener = new NameInputListener();
        // TODO: Fix or Replace the input listener
        Gdx.input.getTextInput(listener, "Enter your name", "", "John Doe");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0x77, 0x77, 0x77, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getStage().act(delta);
        getStage().draw();
        String name = listener.getName();
        if (name != null) {
            this.name.setText(name);
        }
    }

    @Override
    public void resize(int width, int height) {

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
        getStage().dispose();
    }
}

