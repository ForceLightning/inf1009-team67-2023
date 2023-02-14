package com.inf1009.team67.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;

public abstract class EntityBase extends Actor {
    private float alpha;
    private Vector2 velocity;
    private Vector2 acceleration;
    private Rectangle boundingBox;
    private TextureRegion region;
    private float angularVelocity;
    private float angularAcceleration;
    private Body body;
    private float baseMovementSpeed;

    public EntityBase(float x, float y, int z, float width, float height, Color color, float rotation, String name, boolean visible, Touchable touchable, String texturePath, float alpha, float baseMovementSpeed) {
        super();
        this.setPosition(x, y);
        this.setZIndex(z);
        this.setWidth(width);
        this.setHeight(height);
        this.setColor(color);
        this.setRotation(rotation);
        this.setName(name);
        this.setVisible(visible);
        this.setTouchable(touchable);
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        region = new TextureRegion(texture);
        this.setBounds(region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight());
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 0);
        this.setAngularVelocity(0);
        this.setAngularAcceleration(0);
        this.alpha = alpha;
        this.boundingBox = new Rectangle(x, y, width, height);
        this.baseMovementSpeed = baseMovementSpeed;
    }

    public EntityBase() {
        super();
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 0);
        Texture texture = new Texture(Gdx.files.internal("textures/badlogic.jpg"));
        region = new TextureRegion(texture);
        this.setBounds(region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight());
        this.setAngularVelocity(0);
        this.setAngularAcceleration(0);
        this.alpha = 1;
        this.boundingBox = new Rectangle(0, 0, 0, 0);
        this.setPosition(0, 0);
        this.setZIndex(0);
        this.setWidth(0);
        this.setHeight(0);
        this.setColor(Color.WHITE);
        this.setRotation(0);
        this.setName("Entity");
        this.setVisible(true);
        this.setTouchable(Touchable.disabled);
        this.baseMovementSpeed = 0;
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    @Override
    public void setPosition(float x, float y) {
        setPosition(x, y, Align.bottomLeft);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        super.setPosition(x, y, alignment);
        switch (alignment) {
            case Align.bottom:
                this.boundingBox.setPosition(x - getWidth()/2, y);
                break;
            case Align.bottomLeft:
                this.boundingBox.setPosition(x, y);
                break;
            case Align.bottomRight:
                this.boundingBox.setPosition(x - getWidth(), y);
                break;
            case Align.center:
                this.boundingBox.setPosition(x - getWidth()/2, y - getHeight()/2);
                break;
            case Align.left:
                this.boundingBox.setPosition(x, y - getHeight()/2);
                break;
            case Align.right:
                this.boundingBox.setPosition(x - getWidth(), y - getHeight()/2);
                break;
            case Align.top:
                this.boundingBox.setPosition(x - getWidth()/2, y - getHeight());
                break;
            case Align.topLeft:
                this.boundingBox.setPosition(x, y - getHeight());
                break;
            case Align.topRight:
                this.boundingBox.setPosition(x - getWidth(), y - getHeight());
                break;
        }
    }

    public void modifyPosition(Vector2 deltaPosition) {
        setPosition(getX() + deltaPosition.x, getY() + deltaPosition.y);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        this.boundingBox.setSize(width, height);
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        this.boundingBox.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        this.boundingBox.setY(y);
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        this.boundingBox.setWidth(width);
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        this.boundingBox.setHeight(height);
    }

    public void setVelocity(float x, float y) {
        this.velocity = new Vector2(x, y);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public float getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularAcceleration(float angularAcceleration) {
        this.angularAcceleration = angularAcceleration;
    }

    public float getAngularAcceleration() {
        return angularAcceleration;
    }

    public void setAcceleration(float x, float y) {
        acceleration = new Vector2(x, y);
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setBoundingBox(Rectangle rectangle) {
        this.boundingBox = rectangle;
    }


    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setTexture(Texture texture) {
        this.region.setTexture(texture);
    }

    public void setTexture(String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        region.setTexture(texture);
    }
    
    public Texture getTexture() {
        return region.getTexture();
    }

    public void setTextureRegion(TextureRegion region) {
        this.region = region;
    }

    public void setTextureRegion(int x, int y, int width, int height) {
        region.setRegion(x, y, width, height);
    }

    public void setTextureRegion(float u, float v, float u2, float v2) {
        region.setRegion(u, v, u2, v2);
    }

    public TextureRegion getTextureRegion() {
        return region;
    }

    public void setColor(int colorHex) {
        this.setColor(new Color(colorHex));
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    public float getCentreX() {
        return getX() + getWidth() / 2;
    }

    public float getCentreY() {
        return getY() + getHeight() / 2;
    }

    public Vector2 getCentre() {
        return new Vector2(getCentreX(), getCentreY());
    }

    public void setCentreX(float x) {
        setX(x - getWidth() / 2);
    }

    public void setCentreY(float y) {
        setY(y - getHeight() / 2);
    }

    public void setCentre(float x, float y) {
        setCentreX(x);
        setCentreY(y);
    }

    public boolean isOverlapping(EntityBase other) {
        return boundingBox.overlaps(other.getBoundingBox());
    }

    public void update(float delta) {
        if (body != null) {
            // checks if there is a box2d body attached to this actor
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        } else {
            setVelocity(velocity.scl((1-delta)));
            setVelocity(velocity.add(acceleration.cpy().scl(delta)));
            setAcceleration(0, 0);
            setPosition(getPosition().add(getVelocity().cpy().scl(delta)));
            setRotation(getRotation() + angularVelocity * delta);
            angularVelocity += angularAcceleration * delta;
            angularAcceleration *= (1-delta);
        }
    }

    public void update() {
        update(Gdx.graphics.getDeltaTime());
    }

    public void setBaseMovementSpeed(float baseMovementSpeed) {
        this.baseMovementSpeed = baseMovementSpeed;
    }

    public float getBaseMovementSpeed() {
        return baseMovementSpeed;
    }
}
