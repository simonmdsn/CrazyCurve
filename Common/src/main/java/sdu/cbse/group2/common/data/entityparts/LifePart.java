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

@Getter
@Setter
public class LifePart implements EntityPart {

    private boolean dead;
    private int life;
    private boolean hit;

    public LifePart(int life) {
        this.life = life;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (hit) {
            life = -1;
            hit = false;
        }
        if (life <= 0) {
            dead = true;
        }
    }
}
