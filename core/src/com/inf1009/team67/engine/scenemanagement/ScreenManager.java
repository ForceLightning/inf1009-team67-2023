package com.inf1009.team67.engine.scenemanagement;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.inf1009.team67.game.main.MyGdxGame;
import com.inf1009.team67.game.scenes.ScreenEnum;
import com.inf1009.team67.game.scenes.TransitionScreen;

public class ScreenManager {
    private HashMap<ScreenEnum, Class<? extends ScreenBase>> screenCollection;
    private MyGdxGame game;

    public ScreenManager(MyGdxGame game) {
        this.game = game;
        screenCollection = new HashMap<ScreenEnum, Class<? extends ScreenBase>>();
    }

    public void addScreen(ScreenEnum screen, Class<? extends ScreenBase> screenClass) {
        screenCollection.put(screen, screenClass);
    }

    public void switchScreen(ScreenEnum screen) {
        if (game.getScreen() instanceof ScreenBase) {
            final ScreenBase currentScreen = (ScreenBase) game.getScreen();
            try {
                ScreenBase newScreen = screenCollection
                    .get(screen)
                    .getDeclaredConstructor(MyGdxGame.class)
                    .newInstance(game);
                TransitionScreen transitionScreen = new TransitionScreen(
                    game,
                    currentScreen,
                    newScreen
                );
                game.setScreen(transitionScreen);
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
