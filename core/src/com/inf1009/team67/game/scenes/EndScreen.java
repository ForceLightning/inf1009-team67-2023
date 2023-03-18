package com.inf1009.team67.game.scenes;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.inf1009.team67.engine.scenemanagement.ScreenBase;
import com.inf1009.team67.game.main.MyGdxGame;

public class EndScreen extends ScreenBase {
    private Table scoreTable;
    private Skin skin;
    private TextButton backButton;
    private TextField username;
    private TextButton saveButton;
    private boolean saved = false;
    private Label labelresponse;

    public EndScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);
        game.assetsManager.manager.finishLoading();
        skin = game.assetsManager.manager.get("skin/metal-ui.json");
        backButton = new TextButton("Back", skin);
        saveButton = new TextButton("Save", skin);
        Table backArea = new Table();
        scoreTable = new Table();
        scoreTable.setFillParent(true);
        backArea.setPosition(0, 0);
        backArea.setSize(100, 100);
        Table saveArea = new Table();
        saveArea.setPosition(700, 0);
        saveArea.setSize(100, 100);
        getStage().addActor(backArea);
        getStage().addActor(saveArea);
        getStage().addActor(scoreTable);
        backArea.add(backButton).size(110, 50);
        saveArea.add(saveButton).size(110, 50);
        FileHandle file = Gdx.files.local("leaderboard.csv");
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
        labelresponse = new Label("Please input your name", skin);
        getStage().addActor(labelresponse);
        labelresponse.setX(580);
        labelresponse.setY(270);
        scoreTable.add(score).width(width).pad(padding);
        // scoreTable.add(name).width(width).pad(padding);
        username = new TextField("Name", skin);
        scoreTable.add(username).width(width).pad(padding);
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

        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (EndScreen.this.username.getText().isEmpty()) {
                    EndScreen.this.labelresponse.setText("Input your username please.");
                } else if (!saved) {
                    saveScore(username.getText(), ((Integer) game.getScore()).toString());
                    saved = true;
                    game.setScreen(ScreenEnum.LEADERBOARD);
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0x77, 0x77, 0x77, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getStage().act(delta);
        getStage().draw();
        // String name = listener.getName();
        // if (name != null) {
        //     this.name.setText(name);
        // }

        username.setTextFieldFilter(new TextFieldFilter() {
            // Accepts all Characters except ',' so the it wouldnt affect csv file
            public boolean acceptChar(TextField textField, char c) {
                if (c == ',')
                    return false;
                return true;
            }
        });

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

    private void saveScore(String name, String score) {
        // Open the CSV file for reading
        boolean addedScore = false;
        FileHandle file = Gdx.files.local("leaderboard.csv");
        String text = file.readString();
        String[] lines = text.split("\\r?\\n");
        ArrayList<String> inputLines = new ArrayList<String>();
        for (String line : lines) {
            inputLines.add(line);
        }
        String dummyLine = "0,NULL,-999";
        inputLines.add(dummyLine);
        ArrayList<String> outputLines = new ArrayList<String>();

        // Check if the name and score already exist in the file
        boolean exists = false;
        int rank = 0;
        for (String line : inputLines) {
            rank++;
            String[] parts = line.split(",");
            if (parts.length == 3 && parts[1].equals(name) && parts[2].equals(score)) {
                exists = true;
                return;
            }
            if (parts.length == 3 && Integer.parseInt(parts[2]) < Integer.parseInt(score) && !addedScore) {
                String uppercaseName = name.toUpperCase();
                String newScore = String.format("%d,%s,%s\n", rank, uppercaseName, score);
                rank++;
                outputLines.add(newScore);
                addedScore = true;
            }
            String newLine = String.format("%d,%s,%s\n", rank, parts[1], parts[2]);
            outputLines.add(newLine);
        }
        
        // remove dummy line
        outputLines = new ArrayList<String>(outputLines.subList(0, outputLines.size() - 1));

        // strip the last newline
        if (outputLines.size() > 0) {
            outputLines.set(outputLines.size() - 1, outputLines.get(outputLines.size() - 1).replace("\n", ""));
        }
        if (outputLines.size() > 10) {
            outputLines = new ArrayList<String>(outputLines.subList(0, 10));
        }
        if (!exists) {
            file.writeString("", false);
            for (String line: outputLines) {
                file.writeString(line, true);
            }
        }
    }

    private int getNextRank() {
        // Read the existing scores from the CSV file
        FileHandle file = Gdx.files.local("leaderboard.csv");
        String text = file.readString();
        String[] lines = text.split("\\r?\\n");


        // Return the next available rank
        return lines.length + 1;
    }
}

// private void saveScore(String name, String score) {
// FileHandle file = Gdx.files.local("leaderboard.csv");
// String text = file.readString();

// // Create a new entry with the name and score
// String newEntry = String.format("%s,%s,%s\n", getRank(), name, score);

// // Append the new entry to the file
// text += newEntry;
// file.writeString(text, false);

// // Read the file and keep only the top 5 scores
// String[] lines = text.split("\\r?\\n");
// Arrays.sort(lines, new Comparator<String>() {
// @Override
// public int compare(String o1, String o2) {
// return Integer.parseInt(o2.split(",")[2]) - Integer.parseInt(o1.split(",")[2]);
// }
// });
// if (lines.length > 5) {
// // Keep only the top 5 scores
// lines = Arrays.copyOfRange(lines, 0, 5);
// }
// text = String.join("\n", lines);

// // Write the new file contents
// file.writeString(text, false);
// }
