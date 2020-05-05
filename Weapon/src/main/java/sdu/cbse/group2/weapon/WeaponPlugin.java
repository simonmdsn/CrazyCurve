package sdu.cbse.group2.weapon;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.ShootingPart;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.List;

public class WeaponPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        List<Entity> entities = world.getBoundedEntities(CommonSnake.class);
        entities.forEach(snake -> {
            snake.getParts().put(ShootingPart.class, new ShootingPart());
            Weapon weapon = new Weapon(new GameSprite("textures/items/tongue-short.png", 60, 60), snake);
            world.addEntity(weapon);
        });
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Weapon.class).forEach(world::removeEntity);
    }
}
