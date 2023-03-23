package com.inf1009.team67.game.scenes.gui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.inf1009.team67.engine.controllables.ControllableCharacter;
import com.inf1009.team67.engine.entitymanagement.EntityBase;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatHelper;
import com.inf1009.team67.game.controllables.Player;

public class Target extends EntityBase {

    private ControllableCharacter target;
    private Camera camera;

    public Target(Camera camera) {
        super();
        this.setTexture("textures/Crosshair.png");
        this.setSize(50, 50);
        this.setColor(Color.RED);
        this.setAlpha(0.0f);
        this.camera = camera;
    }

    public void updateTarget(Player player, Camera camera) {
        ControllableCharacter target = player.getTarget();
        if (target != null && !target.isPlayer() && BasicCombatHelper.inAttackRange(player, target)) {
            this.target = target;
            this.setAlpha(1.0f);
        } else {
            this.setAlpha(0.0f);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (target == null || target.isPlayer()) {
            return;
        }
        Vector3 pos = camera.project(new Vector3(target.getCentreX(), target.getCentreY(), 0));
        this.setCentre(pos.x, pos.y);
        Color oldColor = batch.getColor();
        batch.setColor(this.getColor());
        super.draw(batch, this.getAlpha());
        batch.setColor(oldColor);
    }
}
