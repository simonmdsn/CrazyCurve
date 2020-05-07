package sdu.cbse.group2.player;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameKeys;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.data.entityparts.ShootingPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        world.getEntities(Player.class).stream().map(Player.class::cast).collect(Collectors.toList()).stream().filter(CommonSnake::isAlive).forEach(player -> {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            ShootingPart shootingPart = player.getPart(ShootingPart.class);
            if (shootingPart != null) {
                shootingPart.setShooting(gameData.getKeys().isDown(GameKeys.SPACE));
            }
            Optional.ofNullable(world.getNearestTile((int) positionPart.getX(), (int) positionPart.getY())).ifPresent(tile -> tile.getEntities().remove(player));
            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            Optional.ofNullable(world.getNearestTile((int) positionPart.getX(), (int) positionPart.getY())).ifPresent(tile -> tile.getEntities().add(player));
        });
    }
}
