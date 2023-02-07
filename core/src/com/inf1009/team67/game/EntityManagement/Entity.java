package com.inf1009.team67.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Entity {
    private Vector3 position;
    private Vector3 velocity;
    private Vector3 acceleration;
    private float width;
    private float height;
    private Texture texture;
    private boolean isActive = true;
    private float movementSpeed;
    private Color color;
    private Circle boundingCircle;

    public Entity(float x, float y, int z, float width, float height, String texturePath, float movementSpeed, int colorHex) {
        position = new Vector3(x, y, z);
        velocity = new Vector3(0, 0, 0);
        acceleration = new Vector3(0, 0, 0);
        this.width = width;
        this.height = height;
        texture = new Texture(Gdx.files.internal(texturePath));
        this.movementSpeed = movementSpeed;
        color = new Color(colorHex);
        boundingCircle = new Circle(position.x + width / 2, position.y + height / 2, width / 2);
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
        boundingCircle.setPosition(position.x + width / 2, position.y + height / 2);
    }

    public void setPosition(Vector2 position) {
        this.position.x = position.x;
        this.position.y = position.y;
        boundingCircle.setPosition(position.x + width / 2, position.y + height / 2);
    }

    public void setPosition(Vector3 position) {
        this.position = position;
        boundingCircle.setPosition(position.x + width / 2, position.y + height / 2);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getCentre() {
        return new Vector3(position.x + width / 2, position.y + height / 2, position.z);
    }

    public void setCentre(float x, float y) {
        position.x = x - width / 2;
        position.y = y - height / 2;
        boundingCircle.setPosition(x, y);
    }

    public void setX(float x) {
        position.x = x;
        boundingCircle.setX(position.x + width / 2);
    }

    public float getX() {
        return position.x;
    }

    public void setY(float y) {
        position.y = y;
        boundingCircle.setY(position.y + height / 2);
    }

    public float getY() {
        return position.y;
    }

    public void setZ(int z) {
        position.z = z;
    }

    public int getZ() {
        return (int) position.z;
    }

    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.x = velocity.x;
        this.velocity.y = velocity.y;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setAcceleration(float x, float y) {
        acceleration.x = x;
        acceleration.y = y;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration.x = acceleration.x;
        this.acceleration.y = acceleration.y;
    }

    public void setAcceleration(Vector3 acceleration) {
        this.acceleration = acceleration;
    }

    public Vector3 getAcceleration() {
        return acceleration;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWidth() {
        return width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTexture(String texturePath) {
        texture = new Texture(Gdx.files.internal(texturePath));
    }

    public Texture getTexture() {
        return texture;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void deactivate() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void toggleActive() {
        isActive = !isActive;
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        velocity = velocity.add(acceleration.scl(delta));
        position = position.add(velocity.scl(delta));
        boundingCircle.setPosition(position.x + width / 2, position.y + height / 2);
    }

    public void modifyPosition(Vector3 normalisedVelocityVector3, float scalar) {
        float delta = Gdx.graphics.getDeltaTime();
        position = position.add(normalisedVelocityVector3.scl(delta * scalar));
        boundingCircle.setPosition(position.x + width / 2, position.y + height / 2);
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(int colorHex) {
        color = new Color(colorHex);
    }

    public void setColor(float r, float g, float b, float a) {
        color = new Color(r, g, b, a);
    }

    public Color getColor() {
        return color;
    }

    public void cardinalMovement(CardinalDirection direction, float movementSpeed, float delta) {
        position = position.add(direction.x * movementSpeed * delta, direction.y * movementSpeed * delta, 0);
        boundingCircle.setPosition(position.x + width / 2, position.y + height / 2);
    }

    public void cardinalMovement(CardinalDirection direction, float movementSpeed) {
        float delta = Gdx.graphics.getDeltaTime();
        cardinalMovement(direction, movementSpeed, delta);
    }

    public void cardinalMovement(CardinalDirection direction) {
        float delta = Gdx.graphics.getDeltaTime();
        cardinalMovement(direction, movementSpeed, delta);
    }
}
