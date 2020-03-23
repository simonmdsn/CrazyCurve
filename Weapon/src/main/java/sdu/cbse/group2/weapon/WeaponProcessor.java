package sdu.cbse.group2.weapon;

import sdu.cbse.group2.common.data.*;
import sdu.cbse.group2.common.data.entityparts.*;
import sdu.cbse.group2.common.item.ItemSPI;
import sdu.cbse.group2.common.services.IEntityProcessingService;

public class WeaponProcessor implements IEntityProcessingService, ItemSPI {
    @Override
    public void process(GameData gameData, World world) {
        world.getEntities().stream().filter(entity -> entity.getPart(ShootingPart.class) != null).forEach(entity -> {
            ShootingPart shootingPart = entity.getPart(ShootingPart.class);
            ItemPart itemPart = entity.getPart(ItemPart.class);
            Entity weapon = itemPart.getItems(Weapon.class).get(0);
            TimerPart weaponTimerPart = weapon.getPart(TimerPart.class);

            if (shootingPart.isShooting()) {
                spawnTongueAttack(weapon);
                shootingPart.setCoolDown(10);
                weaponTimerPart.setExpiration(2);
                shootingPart.setShooting(false);
            }
            weaponTimerPart.process(gameData, entity);
            shootingPart.process(gameData, entity);

            if (weaponTimerPart.getExpiration() <= 0) {
                weapon.setGameSprite(new GameSprite("items/tongue.png", 30, 30));
            }
            PositionPart shooterPosition = entity.getPart(PositionPart.class);
            PositionPart weaponPosition = weapon.getPart(PositionPart.class);

            GameSprite shooterGameSprite = entity.getGameSprite();
            float bx = (float) (shooterPosition.getX() + (shooterGameSprite.getWidth()) * Math.cos(shooterPosition.getRadians()));
            float by = (float) (shooterPosition.getY() + (shooterGameSprite.getHeight()) * Math.sin(shooterPosition.getRadians()));

            weaponPosition.setX(shooterPosition.getX());
            weaponPosition.setY(shooterPosition.getY());
            weaponPosition.setRadians(shooterPosition.getRadians());
        });
    }

    private void spawnTongueAttack(Entity weapon) {
        weapon.setGameSprite(new GameSprite("items/tongue-long.png", 65, 65));
    }

    @Override
    public void affectEntity(Entity e) {

    }
}
