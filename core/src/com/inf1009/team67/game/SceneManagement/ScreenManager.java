package com.inf1009.team67.game.SceneManagement;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.inf1009.team67.game.Main.MyGdxGame;

public class ScreenManager {
    private HashMap<ScreenEnum, Class<? extends ScreenBase>> screenCollection;
    private MyGdxGame game;

    public ScreenManager(MyGdxGame game) {
        this.game = game;
        screenCollection = new HashMap<ScreenEnum, Class<? extends ScreenBase>>();
        screenCollection.put(ScreenEnum.SPLASHSCREEN, SplashScreen.class);
        screenCollection.put(ScreenEnum.MENU, MenuScreen.class);
        screenCollection.put(ScreenEnum.SETTINGS, SettingsScreen.class);
        screenCollection.put(ScreenEnum.GAME, GameScreen.class);
        screenCollection.put(ScreenEnum.END, EndScreen.class);
        screenCollection.put(ScreenEnum.INSTRUCTIONS, InstructionsScreen.class);
    }

    public void switchScreen(ScreenEnum screen) {
        if (game.getScreen() instanceof ScreenBase) {
            Stage currentStage = ((ScreenBase) game.getScreen()).getStage();
            currentStage.getRoot().getColor().a = 1;
            SequenceAction sequenceAction = new SequenceAction();
            sequenceAction.addAction(Actions.fadeOut(0.5f));
            sequenceAction.addAction(Actions.run(new Runnable() {
                @Override
                public void run() {
                    setScreen(screen);
                }
            }));
            currentStage.getRoot().addAction(sequenceAction);
        } else {
            setScreen(screen);
        }
    }

    public void setScreen(ScreenEnum screen) {
        try {
            ScreenBase newScreen = screenCollection
                .get(screen)
                .getDeclaredConstructor(MyGdxGame.class)
                .newInstance(game);
            game.setScreen(newScreen);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
