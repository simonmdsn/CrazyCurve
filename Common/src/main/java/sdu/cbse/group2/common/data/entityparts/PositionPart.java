/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdu.cbse.group2.common.data.entityparts;

import lombok.AllArgsConstructor;
import lombok.Data;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PositionPart implements EntityPart, Serializable {

    private float x, y, radians;

    @Override
    public void process(GameData gameData, Entity entity) {
    }

}
