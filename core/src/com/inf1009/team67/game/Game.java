package com.inf1009.team67.game;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inf1009.team67.game.EntityManagement.EntityBase;
import com.inf1009.team67.game.EntityManagement.Interactable;
import com.inf1009.team67.game.EntityManagement.InteractableEntity;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private TreeMap<Integer, ArrayList<EntityBase>> entityCollection;
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
		entityCollection = new TreeMap<Integer, ArrayList<EntityBase>>();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		updateInteractions();
		update();
		batch.begin();
		for (Integer z : entityCollection.keySet()) {
			for (EntityBase entity : entityCollection.get(z)) {
				renderer.render(entity);
			}
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose();
	}

	/**
	 * Inserts a new entity into the entityCollection at the specified z value
	 * @param entity The entity to insert
	 * @param z	The z value to insert the entity at
	 */
	public void insertAtZ(EntityBase entity, int z) {
		if (entityCollection.containsKey(z)) {
			entityCollection.get(z).add(entity);
		} else {
			ArrayList<EntityBase> newZ = new ArrayList<EntityBase>();
			newZ.add(entity);
			entityCollection.put(z, newZ);
		}
	}

	public void updateInteractions() {
		for (Integer z : entityCollection.keySet()) {
			for (int i = 0; i < entityCollection.get(z).size() - 1; i++) {
				EntityBase entity = entityCollection.get(z).get(i);
				if (entity instanceof InteractableEntity) {
					for (int j = i + 1; j < entityCollection.get(z).size(); j++) {
						EntityBase otherEntity = entityCollection.get(z).get(j);
						if (otherEntity instanceof InteractableEntity) {
							Interactable thisInteractable = (Interactable) entity;
							Interactable otherInteractable = (Interactable) otherEntity;
							if (thisInteractable.isInteractingWith(otherInteractable)) {
								thisInteractable.handleInteraction(otherInteractable);
							}
						}
					}
				}
			}
		}
	}

	public void update() {
		for (Integer z : entityCollection.keySet()) {
			for (EntityBase entity : entityCollection.get(z)) {
				entity.update();
			}
		}
	}
}
