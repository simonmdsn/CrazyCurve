package sdu.cbse.group2.player;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonsnake.CommonSnake;
import sdu.cbse.group2.commonsnake.SnakeSPI;

import java.util.Random;

public class PlayerPlugin implements SnakeSPI, IGamePluginService {

    private CommonSnake player;
    private Random random;
    private GameData gameData;

    @Override
    public void start(GameData gameData, World world) {
        random = new Random();
        System.out.println("random done");
        this.gameData = gameData;
        System.out.println("gameData done");
        System.out.println(gameData.getDisplayWidth());
        System.out.println(gameData.getDelta());
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
        float x = random.nextInt(gameData.getDisplayWidth());
        float y = random.nextInt(gameData.getDisplayHeight());
        float radians = 0;

        CommonSnake playerSnake = new CommonSnake(new GameSprite("player/player.png", 30, 30),new GameSprite("player/tail.png",30,30));
        playerSnake.add(new MovingPart(maxSpeed, rotationSpeed));
        playerSnake.add(new PositionPart(x, y, radians));

        return playerSnake;
    }

    @Override
    public CommonSnake create(GameData gameData, World world) {
        return null;
    }

    private int getRadiansForSnake(int x, int y){
        int centerX = gameData.getDisplayWidth() / 2;
        int centerY = gameData.getDisplayHeight() / 2;
        return 0;
    }
}


