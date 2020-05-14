package sdu.cbse.group2.player;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;

import java.util.stream.Stream;

public class PlayerPlugin implements IGamePluginService {

    private Player player;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayerSnake(gameData);
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        Stream.of(player).peek(player -> player.deleteTail(world)).forEach(world::removeEntity);
    }

    private Player createPlayerSnake(GameData gameData) {
        final Player player = new Player(new GameSprite("textures/player/player.png", 30, 30, 1), new GameSprite("textures/player/tail.png", 30, 30), gameData.getPlayerName());
        return player;
    }
}


