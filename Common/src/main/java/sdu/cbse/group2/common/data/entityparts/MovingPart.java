/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdu.cbse.group2.common.data.entityparts;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;

import static java.lang.Math.cos;
import static java.lang.StrictMath.sin;

@Getter
@Setter
public class MovingPart implements EntityPart {

    private float dx, dy;
    private float maxSpeed, rotationSpeed;
    private boolean left, right;

    public MovingPart(float maxSpeed, float rotationSpeed) {
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float dt = gameData.getDelta();

        // turning
        if (left) {
            radians += rotationSpeed * dt;
        }

        if (right) {
            radians -= rotationSpeed * dt;
        }

        // set position
        x += cos(radians) * maxSpeed * dt;
        if (x > gameData.getDisplayWidth()) {
            x = 0;
        } else if (x < 0) {
            x = gameData.getDisplayWidth();
        }

        y += sin(radians) * maxSpeed * dt;
        if (y > gameData.getDisplayHeight()) {
            y = 0;
        } else if (y < 0) {
            y = gameData.getDisplayHeight();
        }

        positionPart.setX(x);
        positionPart.setY(y);
        positionPart.setRadians(radians);
    }
}
