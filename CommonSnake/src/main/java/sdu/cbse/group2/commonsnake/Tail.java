package sdu.cbse.group2.commonsnake;

import lombok.Getter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;

public class Tail extends Entity {

    @Getter
    private final CommonSnake commonSnake;

    public Tail(GameSprite gameSprite, CommonSnake commonSnake) {
        super(gameSprite);
        this.commonSnake = commonSnake;
    }
}
