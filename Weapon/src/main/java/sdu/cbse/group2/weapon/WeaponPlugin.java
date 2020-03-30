package sdu.cbse.group2.weapon;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.*;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.List;

public class WeaponPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        List<Entity> entities = world.getEntities(CommonSnake.class);
        entities.forEach(snake -> {
            snake.getParts().put(ShootingPart.class, new ShootingPart());
            Weapon weapon = createWeapon(snake);
            world.addEntity(weapon);
        });
    }

    public Weapon createWeapon(Entity shooter) {
        PositionPart shooterPosition = shooter.getPart(PositionPart.class);
        GameSprite shooterGameSprite = shooter.getGameSprite();

        float x = shooterPosition.getX();
        float y = shooterPosition.getY();
        float radians = shooterPosition.getRadians();

        Weapon weapon = new Weapon(new GameSprite("items/tongue-short.png", 60, 60));
        weapon.setShooterUUID(shooter.getUuid());

        float bx = (float) (x + (shooterGameSprite.getWidth() / 2) * Math.cos(radians));
        float by = (float) (y + (shooterGameSprite.getHeight() / 2) * Math.sin(radians));

        //Sets weapons position
        MovingPart movingPart = shooter.getPart(MovingPart.class);
        weapon.add(new MovingPart(movingPart.getMaxSpeed(), movingPart.getRotationSpeed()));
        weapon.add(new PositionPart(bx - (shooterGameSprite.getWidth() / 2),  by - (shooterGameSprite.getHeight() / 2), radians));
        weapon.add(new TimerPart(2));
        return weapon;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Weapon.class).forEach(world::removeEntity);
    }
}
