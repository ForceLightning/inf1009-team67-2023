package com.inf1009.team67.game.CollisionManagement;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.inf1009.team67.game.EntityManagement.EntityBase;

public abstract class DynamicBody extends EntityBase implements RigidBody<DynamicBody> {
    private Circle hitBox;
    private CollisionAccumulator accumulator;

    public DynamicBody() {
        super();
        accumulator = new CollisionAccumulator(this);
        hitBox = new Circle(getCentre(), Math.min(getWidth()/2, getHeight()/2));
    }

    @Override 
    public void update(float delta) {
        applyFromAccumulator(delta);
        super.update(delta);
    }

    public <V extends RigidBody<?>> boolean isCollidingWith(V other) {
        return this.getHitBox().overlaps(other.getHitBox());
    }

    public <V extends RigidBody<?>> void handleCollision(V other) {
        Vector2 center = new Vector2(this.getHitBox().x, this.getHitBox().y);
        Vector2 otherCenter = new Vector2(other.getHitBox().x, other.getHitBox().y);
        Vector2 normal = new Vector2(
            center.x - otherCenter.x,
            center.y - otherCenter.y
        ).nor();
        double distance = center.dst(otherCenter);
        double intersect = this.getHitBox().radius + other.getHitBox().radius - distance;
        float overlap = Math.max(0, (float) intersect);
        Vector2 delta_p = normal.cpy().scl(overlap / 2);
        Vector2 delta_v = normal.cpy().scl(overlap / 2);
        Vector2 delta_a = normal.cpy().scl(overlap / 4);
        this.getAccumulator().addToAbsolutePosition(delta_p);
        this.getAccumulator().addToVelocity(delta_v);
        this.getAccumulator().addToAcceleration(delta_a);
    }

    public void applyFromAccumulator(float delta) {
        setPosition(getPosition().add(accumulator.getAbsolutePositionUpdate()));
        setVelocity(getVelocity().add(accumulator.getVelocityUpdate().scl(delta)));
        setAcceleration(getAcceleration().add(accumulator.getAccelerationUpdate().scl(delta)));
        setRotation(getRotation() + accumulator.getRotationUpdate());
        setAngularVelocity(getAngularVelocity() + accumulator.getAngularVelocityUpdate() * delta);
        setAngularAcceleration(getAngularAcceleration() + accumulator.getAngularAccelerationUpdate() * delta);
    }

    public void resetAccumulator() {
        accumulator.reset();
    }

    public CollisionAccumulator getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(CollisionAccumulator accumulator) {
        this.accumulator = accumulator;
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);
        shapes.line(getCentre(), getVelocity().cpy().scl(2f).add(getCentre()));
    }

    public DynamicBody getEntity() {
        return this;
    }

    public Circle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Circle hitBox) {
        this.hitBox = hitBox;
    }

    public void setHitBox(float x, float y, float radius) {
        this.hitBox = new Circle(x, y, radius);
    }

    public void setHitBox(Vector2 position, float radius) {
        this.hitBox = new Circle(position, radius);
    }

    public void setHitBox(float radius) {
        this.hitBox = new Circle(getCentre(), radius);
    }

    public void setHitBox(float x, float y) {
        this.hitBox = new Circle(x, y, hitBox.radius);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (hitBox != null) {
            hitBox.setPosition(getCentre());
        }
    }

    @Override
    public void setPosition(Vector2 position) {
        super.setPosition(position);
        if (hitBox != null) {
            hitBox.setPosition(getCentre());
        }
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        super.setPosition(x, y, alignment);
        if (hitBox != null) {
            hitBox.setPosition(getCentre());
        }
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        if (hitBox != null) {
            hitBox.setRadius((float) Math.min(width/2, height/2));
        }
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        if (hitBox != null) {
            hitBox.setPosition(getCentre());
        }
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        if (hitBox != null) {
            hitBox.setPosition(getCentre());
        }
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        if (hitBox != null) {
            hitBox.setRadius((float) Math.min(width/2, getHeight()/2));
        }
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        if (hitBox != null) {
            hitBox.setRadius((float) Math.min(getWidth()/2, height/2));
        }
    }

    @Override
    public void setCentreX(float x) {
        super.setCentreX(x);
        if (hitBox != null) {
            hitBox.setPosition(getCentre());
        }
    }

    @Override
    public void setCentreY(float y) {
        super.setCentreY(y);
        if (hitBox != null) {
            hitBox.setPosition(getCentre());
        }
    }

    @Override
    public void setCentre(float x, float y) {
        super.setCentre(x, y);
        if (hitBox != null) {
            hitBox.setPosition(getCentre());
        }
    }
}
