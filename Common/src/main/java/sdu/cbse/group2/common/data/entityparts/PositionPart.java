/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdu.cbse.group2.common.data.entityparts;

import lombok.Data;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;

@Data
public class PositionPart implements EntityPart {

    private float x, y, radians;

    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
