package sdu.cbse.group2.weapon;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.commonweapon.CommonWeapon;

import java.util.UUID;

@Getter
@Setter
public class Weapon extends CommonWeapon {

    public Weapon(GameSprite gameSprite) {
        super(gameSprite);
    }
    public Weapon(GameSprite gameSprite, UUID shooterUUID) {
        super(gameSprite, shooterUUID);
    }

}
