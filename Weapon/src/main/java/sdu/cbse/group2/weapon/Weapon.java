package sdu.cbse.group2.weapon;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.item.Item;

import java.util.UUID;

@Getter
@Setter
public class Weapon extends Item {

    public Weapon(GameSprite gameSprite) {
        super(gameSprite);
    }
}
