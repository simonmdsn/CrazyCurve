package sdu.cbse.group2.player;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.data.entityparts.ShootingPart;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonsnake.CommonSnake;
import sdu.cbse.group2.commonsnake.SnakeSPI;

public class PlayerPlugin implements SnakeSPI, IGamePluginService {

    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayerSnake(world);
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }

    private Entity createPlayerSnake(World world) {
        float maxSpeed = 50;
        float rotationSpeed = 3;
        float x = 200, y = 200, radians = 0;

        Entity playerSnake = new CommonSnake(new GameSprite("Player/player.png", 30, 30),new GameSprite("Player/tail.png",30,30),world);
        playerSnake.add(new MovingPart(maxSpeed, rotationSpeed));
        playerSnake.add(new PositionPart(x, y, radians));

        return playerSnake;
    }

    @Override
    public CommonSnake create(GameData gameData, World world) {
        return null;
    }
}
