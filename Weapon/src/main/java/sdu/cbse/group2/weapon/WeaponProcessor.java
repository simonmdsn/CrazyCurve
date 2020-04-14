package sdu.cbse.group2.weapon;

import sdu.cbse.group2.common.data.*;
import sdu.cbse.group2.common.data.entityparts.*;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sun.corba.EncapsInputStreamFactory;

public class WeaponProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity weaponEntity : world.getEntities(Weapon.class)) {
            Weapon weapon = (Weapon) weaponEntity;
            Entity shooter = world.getEntity(weapon.getShooterUUID());
            if (shooter != null){
                ShootingPart shootingPart = shooter.getPart(ShootingPart.class);
                shootingPart.setShooting(gameData.getKeys().isDown(GameKeys.SPACE));

                TimerPart weaponTimerPart = weapon.getPart(TimerPart.class);

                if (shootingPart.isShooting() && shootingPart.getCoolDown() <= 0) {
                    spawnAttack(weapon);
                    shootingPart.setCoolDown(5);
                    weaponTimerPart.setExpiration(2);
                }

                if (weaponTimerPart.getExpiration() <= 0) {
                    weapon.setGameSprite(new GameSprite("items/tongue-short.png", 60, 60));
                    weapon.setShooting(false);
                }

                weaponTimerPart.process(gameData, shooter);
                shootingPart.process(gameData, shooter);

                processPosition(shooter, weapon);
            } else {
                world.removeEntity(weaponEntity);
            }
        }
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

    private void spawnAttack(Weapon weapon) {
        weapon.setGameSprite(new GameSprite("items/tongue-long.png", 60, 60));
        weapon.setShooting(true);
    }
}
