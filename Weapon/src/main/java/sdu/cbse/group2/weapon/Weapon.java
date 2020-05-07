package sdu.cbse.group2.weapon;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.data.entityparts.TimerPart;
import sdu.cbse.group2.commonweapon.CommonWeapon;

@Getter
@Setter
public class Weapon extends CommonWeapon {

    public Weapon(GameSprite gameSprite, Entity shooter) {
        super(gameSprite, shooter.getUuid());

        PositionPart shooterPosition = shooter.getPart(PositionPart.class);
        GameSprite shooterGameSprite = shooter.getGameSprite();

        float x = shooterPosition.getX();
        float y = shooterPosition.getY();
        float radians = shooterPosition.getRadians();

        float bx = (float) (x + (shooterGameSprite.getWidth() / 2) * Math.cos(radians));
        float by = (float) (y + (shooterGameSprite.getHeight() / 2) * Math.sin(radians));

        MovingPart movingPart = shooter.getPart(MovingPart.class);
        add(new MovingPart(movingPart.getMaxSpeed(), movingPart.getRotationSpeed()));
        add(new PositionPart(bx - (shooterGameSprite.getWidth() / 2), by - (shooterGameSprite.getHeight() / 2), radians));
        add(new TimerPart(2));

        setRadius(14);
    }
}
