package sdu.cbse.group2.common.item;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;

import java.util.UUID;

public class Item extends Entity {
    public Item(GameSprite gameSprite) {
        super(gameSprite);
    }

    public Item(UUID uuid, GameSprite gameSprite) {
        super(uuid, gameSprite);
    }
}
