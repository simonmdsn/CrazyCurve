package sdu.cbse.group2.player;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonsnake.CommonSnake;
import sdu.cbse.group2.commonsnake.SnakeSPI;

public class PlayerPlugin implements SnakeSPI, IGamePluginService {

    private CommonSnake player;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayerSnake();
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
        player.getTailList().forEach(world::removeEntity);
    }

    private CommonSnake createPlayerSnake() {
        float maxSpeed = 100;
        float rotationSpeed = 3;
        float x = 200;
        float y = 200;
        float radians = 5;

        CommonSnake playerSnake = new CommonSnake(new GameSprite("player/player.png", 30, 30),new GameSprite("player/tail.png",30,30));
        playerSnake.add(new MovingPart(maxSpeed, rotationSpeed));
        playerSnake.add(new PositionPart(x, y, radians));

        return playerSnake;
    }

    @Override
    public CommonSnake create(GameData gameData, World world) {
        return null;
    }
}
