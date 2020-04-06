package sdu.cbse.group2.player;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    private Player player;

    @Override
    public void start(GameData gameData, World world) {
        this.player = createPlayerSnake();
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
        player.getTailList().forEach(world::removeEntity);
    }

    private Player createPlayerSnake() {
        return new Player(new GameSprite("player/player.png", 30, 30), new GameSprite("player/tail.png", 30, 30));
    }

//    private int getRadiansForSnake(int x, int y) {
//        int centerX = gameData.getDisplayWidth() / 2;
//        int centerY = gameData.getDisplayHeight() / 2;
//        return 0;
//    }
}


