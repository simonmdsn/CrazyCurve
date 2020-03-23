package sdu.cbse.group2.weapon;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.ItemPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.data.entityparts.ShootingPart;
import sdu.cbse.group2.common.data.entityparts.TimerPart;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.List;

public class WeaponPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        List<Entity> entities = world.getEntities(CommonSnake.class);
        entities.forEach(snake -> {
            System.out.println(snake.getUuid());
            snake.getParts().put(ShootingPart.class, new ShootingPart());
            ItemPart itemPart = snake.getPart(ItemPart.class);
            Weapon weapon = createTongue(snake);
            world.addEntity(weapon);
            itemPart.addItem(weapon);
            System.out.println(itemPart.getItems());
        });
    }

    public Weapon createTongue(Entity shooter) {
        PositionPart shooterPosition = shooter.getPart(PositionPart.class);
        GameSprite shooterGameSprite = shooter.getGameSprite();

        float x = shooterPosition.getX();
        float y = shooterPosition.getY();
        float radians = shooterPosition.getRadians();

        Weapon tongue = new Weapon(new GameSprite("items/tongue.png", 36, 26));

        float bx = (float) (x + (shooterGameSprite.getWidth() / 2 + 15) * Math.cos(radians));
        float by = (float) (y + (shooterGameSprite.getWidth() / 2 + 15) * Math.sin(radians));

        //Sets weapons position
        tongue.add(new PositionPart(bx + (shooterGameSprite.getWidth() / 2), by + (shooterGameSprite.getHeight() / 2), radians));
        tongue.add(new TimerPart(2));
        return tongue;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(CommonSnake.class).forEach(snake -> {
            ItemPart itemPart = snake.getPart(ItemPart.class);
            itemPart.removeItems(Weapon.class);
        });
    }
}
