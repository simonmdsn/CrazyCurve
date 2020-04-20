package sdu.cbse.group2.player;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.ShootingPart;
import sdu.cbse.group2.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    private Player player;

    @Override
    public void start(GameData gameData, World world) {
        this.player = createPlayerSnake(gameData);
        ShootingPart shootingPart = new ShootingPart();
        player.add(shootingPart);
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
        player.getTailList().forEach(world::removeEntity);
    }

    private Player createPlayerSnake(GameData gameData) {
        return new Player(new GameSprite("textures/player/player.png", 30, 30,1), new GameSprite("textures/player/tail.png", 30, 30), gameData.getPlayerName());
    }
}


