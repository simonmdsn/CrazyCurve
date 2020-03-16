/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdu.cbse.group2.common.data.entityparts;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;


public interface EntityPart {
    void process(GameData gameData, Entity entity);
}
