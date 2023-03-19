package com.inf1009.team67.game.scenes;

import java.util.Arrays;

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

public class LeaderboardScreen extends ScreenBase {

    private Skin skin;
    private TextButton backButton;
    public LeaderboardScreen(MyGdxGame game) {
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

        getStage().addActor(table);
        getStage().addActor(backArea);

        backArea.add(backButton).size(110, 50);
        FileHandle file = Gdx.files.local("leaderboard.csv");
        String text = file.readString();
        String[] lines = text.split("\\r?\\n");
            // Sort the scores in descending order based on the score
        Arrays.sort(lines, (a, b) -> {
            String[] partsA = a.split(",");
            String[] partsB = b.split(",");
            if (partsA.length != 3 || partsB.length != 3) {
                return 0;
        }
        int scoreA = Integer.parseInt(partsA[2]);
        int scoreB = Integer.parseInt(partsB[2]);
        return scoreB - scoreA;
    });
    int count = 0; // Counter for number of scores added to table
    for (String line : lines) {
        if (count >= 5) { // Stop once 5 scores have been added
            break;
        }
            table.row();
            String[] parts = line.split(",");
            // ranking
            Label ranking = new Label(parts[0], skin);
            float rankingWidth = Gdx.graphics.getWidth() * 0.1f;
            table.add(ranking).width(rankingWidth).height(50).pad(10);
            // name
            Label name = new Label(parts[1], skin);
            float nameWidth = Gdx.graphics.getWidth() * 0.5f;
            table.add(name).width(nameWidth).height(50).pad(10);
            // score
            Label score = new Label(parts[2], skin);
            float scoreWidth = Gdx.graphics.getWidth() * 0.2f;
            table.add(score).width(scoreWidth).height(50).pad(10);
            count++;
        }
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
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0x77, 0xff, 0xff, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getStage().act(delta);
        getStage().draw();
    }


}
