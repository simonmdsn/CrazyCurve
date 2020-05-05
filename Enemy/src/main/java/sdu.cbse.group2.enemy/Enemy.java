package sdu.cbse.group2.enemy;

import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.entityparts.TimerPart;
import sdu.cbse.group2.commonsnake.CommonSnake;

public class Enemy extends CommonSnake {

    public Enemy(GameSprite gameSprite, GameSprite tailSprite, String name) {
        super(gameSprite, tailSprite, name);
        add(new TimerPart(0.05F));
    }
}
