package sdu.cbse.group2.weapon;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.ShootingPart;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonsnake.CommonSnake;

public class WeaponPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        world.getBoundedEntities(CommonSnake.class).forEach(snake -> {
            snake.getParts().put(ShootingPart.class, new ShootingPart());
            world.addEntity(new Weapon(new GameSprite("textures/items/tongue-short.png", 60, 60), snake));
        });
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Weapon.class).forEach(world::removeEntity);
    }
}
