package sdu.cbse.group2.weapon;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.data.entityparts.ShootingPart;
import sdu.cbse.group2.common.data.entityparts.TimerPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.commonsnake.CommonSnake;

public class WeaponProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        world.getEntities(Weapon.class).forEach(weaponEntity -> {
            Weapon weapon = (Weapon) weaponEntity;
            Entity shooter = world.getEntity(weapon.getShooterUUID());
            if (shooter != null) {
                ShootingPart shootingPart = shooter.getPart(ShootingPart.class);
                TimerPart weaponTimerPart = weapon.getPart(TimerPart.class);

                if (shootingPart.isShooting() && shootingPart.getCoolDown() <= 0) {
                    weapon.setGameSprite(new GameSprite("textures/items/tongue-long.png", 60, 60));
                    weapon.setShooting(true);
                    shootingPart.setCoolDown(5);
                    weaponTimerPart.setExpiration(2);
                }

                if (weaponTimerPart.getExpiration() <= 0) {
                    weapon.setGameSprite(new GameSprite("textures/items/tongue-short.png", 60, 60));
                    weapon.setShooting(false);
                }

                weaponTimerPart.process(gameData, shooter);
                shootingPart.process(gameData, shooter);

                processPosition(shooter, weapon);
            } else {
                world.removeEntity(weaponEntity);
            }
        });
        addWeaponToNewCommonSnakes(world);
    }

    private void processPosition(Entity shooter, Weapon weapon) {
        PositionPart shooterPosition = shooter.getPart(PositionPart.class);
        PositionPart weaponPosition = weapon.getPart(PositionPart.class);

        float x = shooterPosition.getX();
        float y = shooterPosition.getY();
        float radians = shooterPosition.getRadians();

        GameSprite shooterGameSprite = shooter.getGameSprite();

        float bx;
        float by;
        if (weapon.isShooting()) {
            bx = (float) (x + (shooterGameSprite.getWidth()) * Math.cos(radians));
            by = (float) (y + (shooterGameSprite.getHeight()) * Math.sin(radians));
        } else {
            bx = (float) (x + (shooterGameSprite.getWidth() / 2) * Math.cos(radians));
            by = (float) (y + (shooterGameSprite.getHeight() / 2) * Math.sin(radians));
        }

        weaponPosition.setX(bx - (shooterGameSprite.getWidth() / 2));
        weaponPosition.setY(by - (shooterGameSprite.getHeight() / 2));
        weaponPosition.setRadians(shooterPosition.getRadians());
    }

    private void addWeaponToNewCommonSnakes(World world) {
        world.getBoundedEntities(CommonSnake.class).forEach(snake -> {
            if (snake.getPart(ShootingPart.class) == null) {
                snake.getParts().put(ShootingPart.class, new ShootingPart());
                Weapon weapon = new Weapon(new GameSprite("textures/items/tongue-short.png", 60, 60), snake);
                world.addEntity(weapon);
            }
        });
    }
}
