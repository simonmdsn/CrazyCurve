package sdu.cbse.group2.commonweapon;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;

import java.util.UUID;

@Getter
@Setter
public class CommonWeapon extends Entity {

    private boolean shooting;
    private UUID shooterUUID;

    public CommonWeapon(GameSprite gameSprite) {
        super(gameSprite);
    }

    public CommonWeapon(GameSprite gameSprite, UUID shooterUUID) {
        super(gameSprite);
        this.shooterUUID = shooterUUID;
    }
}
