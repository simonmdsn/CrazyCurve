/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdu.cbse.group2.common.data.entityparts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;

@Setter
@Getter
@AllArgsConstructor
public class TimerPart implements EntityPart {

    private float expiration;

    public void reduceExpiration(float delta) {
        this.expiration -= delta;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (expiration > 0) {
            reduceExpiration(gameData.getDelta());
        }
    }
}
