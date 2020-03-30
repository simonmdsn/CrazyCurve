package sdu.cbse.group2.commonpowerup;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.commonsnake.CommonSnake;

public abstract class CommonPowerUp extends Entity {

    public CommonPowerUp(GameSprite gameSprite) {
        super(gameSprite);
    }

    public abstract void applyPowerUp(CommonSnake commonSnake);

    public abstract void removePowerUp(CommonSnake commonSnake);
}
