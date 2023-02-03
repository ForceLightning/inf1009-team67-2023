package com.inf1009.team67.game.Shape;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Rectangle{

    float x;
    float y;
    private float width;

    private float height;

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public float setX(float x){
        return this.x = x;
    }

    public float setY(float y){
        return this.y = y;
    }
    private Sprite sprite;
    private float speed;
    private ShapeRenderer shape;

    //constructor
    public Rectangle(float x, float y, float width, float height, float speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.shape = new ShapeRenderer();
    }

    public void render(){
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.BLUE);
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            x -= speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            x += speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            y += speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            y -= speed;
        }
        shape.rect(x,y, width, height);
        shape.end();
    }

}