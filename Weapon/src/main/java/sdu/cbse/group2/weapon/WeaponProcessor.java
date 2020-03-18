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
                weapon.setGameSprite(new GameSprite("items/tongue.png", 36, 26));
            }
        });
    }

    private void spawnTongueAttack(Entity weapon) {
        weapon.setGameSprite(new GameSprite("items/tongue-long.png", 58, 26));
    }

    @Override
    public void affectEntity(Entity e) {

    }
}
