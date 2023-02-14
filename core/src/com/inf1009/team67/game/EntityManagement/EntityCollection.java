package com.inf1009.team67.game.EntityManagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class EntityCollection {
    private TreeMap<Integer, ArrayList<EntityBase>> entityCollection;
    private Stage stage;

    public EntityCollection(Stage stage) {
        entityCollection = new TreeMap<Integer, ArrayList<EntityBase>>();
        this.stage = stage;
    }

    public void insertAtZ(EntityBase entity, int z) {
        if (!entityCollection.containsKey(z)) {
            entityCollection.put(z, new ArrayList<EntityBase>());
        }
        // determine the index of the entity in the stage
        Integer prev = entityCollection.lowerKey(z);
        Integer groupIndex = 0;
        for (Integer i : entityCollection.keySet()) {
            if (i == prev) {
                break;
            }
            groupIndex += 1; 
        }
        Integer entityIndex = 0;
        Iterator<Integer> layerIterator = entityCollection.keySet().iterator();
        for (int i = 0; i < groupIndex; i++) {
            entityIndex += entityCollection.get(layerIterator.next()).size();
        }
        // add entity to stage and entityCollection
        stage.getRoot().addActorAt(entityIndex, entity);
        entityCollection.get(z).add(entity);
    }

    public void insertAtZ(EntityBase entity, int z, int index) {
        if (!entityCollection.containsKey(z)) {
            entityCollection.put(z, new ArrayList<EntityBase>());
            entityCollection.get(z).add(entity);
            return;
        }
        // determine the index of the entity in the stage
        Integer prev = entityCollection.lowerKey(z);
        Integer groupIndex = 0;
        for (Integer i : entityCollection.keySet()) {
            if (i == prev) {
                break;
            }
            groupIndex += 1; 
        }
        Integer entityIndex = 0;
        Iterator<Integer> layerIterator = entityCollection.keySet().iterator();
        for (int i = 0; i < groupIndex; i++) {
            entityIndex += entityCollection.get(layerIterator.next()).size();
        }
        // add entity to stage and entityCollection
        stage.getRoot().addActorAt(entityIndex + index, entity);
        entityCollection.get(z).add(index, entity);
    }

    public void insertEntity(EntityBase entity) {
        Integer lastLayer = 0;
        try {
            lastLayer = entityCollection.lastKey();
        } catch (NoSuchElementException e) {
            lastLayer = 0;
        } finally {
            insertAtZ(entity, lastLayer);
        }
    }

    public TreeMap<Integer, ArrayList<EntityBase>> getEntityCollection() {
        return entityCollection;
    }

    public void removeEntity(int z, int index) {
        Integer entityIndex = 0;
        Iterator<Integer> layerIterator = entityCollection.keySet().iterator();
        for (int i = 0; i < z; i++) {
            entityIndex += entityCollection.get(layerIterator.next()).size();
        }
        stage.getRoot().removeActorAt(entityIndex, true);
        entityCollection.get(z).remove(index);
        if (entityCollection.get(z).size() == 0) {
            entityCollection.remove(z);
        }
    }

    public void removeLayer(int z) {
        Integer entityIndex = 0;
        Iterator<Integer> layerIterator = entityCollection.keySet().iterator();
        for (int i = 0; i < z; i++) {
            entityIndex += entityCollection.get(layerIterator.next()).size();
        }
        for (int i = 0; i < entityCollection.get(z).size(); i++) {
            stage.getRoot().removeActorAt(entityIndex, true);
        }
        entityCollection.remove(z);
    }

    public boolean removeEntity(EntityBase entity) {
        Integer entityIndex = 0;
        Integer layerIndex = 1;
        for (Integer z : entityCollection.keySet()) {
            if (entityCollection.get(z).contains(entity)) {
                break;
            }
            entityIndex += entityCollection.get(z).size();
            layerIndex += 1;
        }
        entityIndex += entityCollection.get(layerIndex).indexOf(entity);
        stage.getRoot().removeActorAt(entityIndex, true);
        for (Integer z : entityCollection.keySet()) {
            if (entityCollection.get(z).contains(entity)) {
                return entityCollection.get(z).remove(entity);
            }
        }
        return false;
    }

    public void dispose() {
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z)) {
                entityCollection.get(z).remove(entity);
            }
        }
        stage.getRoot().clear();
    }

    public void update(float delta) {
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z)) {
                entity.update(delta);
            }
        }
    }


}
