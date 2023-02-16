package com.inf1009.team67.game.SceneManagement;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

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
        screenCollection.put(ScreenEnum.LEADERBOARD, LeaderboardScreen.class);
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
