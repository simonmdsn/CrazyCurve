package sdu.cbse.group2.weapon;

import sdu.cbse.group2.common.data.*;
import sdu.cbse.group2.common.data.entityparts.*;
import sdu.cbse.group2.common.item.ItemSPI;
import sdu.cbse.group2.common.services.IEntityProcessingService;

public class WeaponProcessor implements IEntityProcessingService, ItemSPI {
    @Override
    public void process(GameData gameData, World world) {
        world.getEntities().stream().filter(entity -> entity.getPart(ShootingPart.class) != null).forEach(shooter -> {
            ItemPart shooterItemPart = shooter.getPart(ItemPart.class);
            Entity weapon = shooterItemPart.getItems(Weapon.class).get(0);
            /*
            ShootingPart shootingPart = shooter.getPart(ShootingPart.class);
            TimerPart weaponTimerPart = weapon.getPart(TimerPart.class);

            if (shootingPart.isShooting()) {
                spawnTongueAttack(weapon);
                shootingPart.setCoolDown(10);
                weaponTimerPart.setExpiration(2);
                shootingPart.setShooting(false);
            }
            weaponTimerPart.process(gameData, shooter);
            shootingPart.process(gameData, shooter);

            if (weaponTimerPart.getExpiration() <= 0) {
                weapon.setGameSprite(new GameSprite("items/tongue.png", 30, 30));
            }*/

            PositionPart shooterPosition = shooter.getPart(PositionPart.class);
            PositionPart weaponPosition = weapon.getPart(PositionPart.class);

            float x = shooterPosition.getX();
            float y = shooterPosition.getY();
            float radians = shooterPosition.getRadians();

            GameSprite shooterGameSprite = shooter.getGameSprite();
            /*
            float bx = (float) (x + (shooterGameSprite.getWidth()) * Math.cos(radians));
            float by = (float) (y + (shooterGameSprite.getHeight()) * Math.sin(radians));
            */

            float bx = (float) (x + (shooterGameSprite.getWidth() / 2) * Math.cos(radians));
            float by = (float) (y + (shooterGameSprite.getHeight() / 2) * Math.sin(radians));

            System.out.println(shooterPosition.getX() + " " + shooterPosition.getY());
            weaponPosition.setX(bx - (shooterGameSprite.getWidth() / 2));
            weaponPosition.setY(by - (shooterGameSprite.getHeight() / 2));
            weaponPosition.setRadians(shooterPosition.getRadians());
        });
    }

    private void spawnTongueAttack(Entity weapon) {
        weapon.setGameSprite(new GameSprite("items/tongue-short.png", 65, 65));
    }

    @Override
    public void affectEntity(Entity e) {

    }
}
