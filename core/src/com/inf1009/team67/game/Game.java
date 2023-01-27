package com.inf1009.team67.game;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inf1009.team67.game.EntityManagement.Entity;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private TreeMap<Integer, ArrayList<Entity>> entityCollection;
	private Renderer renderer;
	private Stage stage;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		renderer = new Renderer(batch);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		stage = new Stage(new ScreenViewport(camera), batch);
		Gdx.input.setInputProcessor(stage);
		entityCollection = new TreeMap<Integer, ArrayList<Entity>>();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		update();
		batch.begin();
		for (Integer z : entityCollection.keySet()) {
			for (Entity entity : entityCollection.get(z)) {
				Color currentColor = batch.getColor();
				batch.setColor(entity.getColor());
				renderer.render(entity);
				batch.setColor(currentColor);
			}
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		// img.dispose();
	}

	/**
	 * Inserts a new entity into the entityCollection at the specified z value
	 * @param entity The entity to insert
	 * @param z	The z value to insert the entity at
	 */
	public void insertAtZ(Entity entity, int z) {
		if (entityCollection.containsKey(z)) {
			entityCollection.get(z).add(entity);
		} else {
			ArrayList<Entity> newZ = new ArrayList<Entity>();
			newZ.add(entity);
			entityCollection.put(z, newZ);
		}
	}

	public void update() {
		for (Integer z : entityCollection.keySet()) {
			for (Entity entity : entityCollection.get(z)) {
				entity.update();
			}
		}
	}
}
