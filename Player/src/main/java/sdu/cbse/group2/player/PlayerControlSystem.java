package sdu.cbse.group2.player;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameKeys;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.data.entityparts.ShootingPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;

import java.util.stream.Collectors;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        //TODO make for loop allow multi player on same keyboard, or remove for loop
        for (Player player : world.getEntities(Player.class).stream().map(Player.class::cast).collect(Collectors.toList())) {
            if(player.isAlive()) {
                PositionPart positionPart = player.getPart(PositionPart.class);
                MovingPart movingPart = player.getPart(MovingPart.class);
                ShootingPart shootingPart = player.getPart(ShootingPart.class);

                movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
                movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
                shootingPart.setShooting(gameData.getKeys().isDown(GameKeys.SPACE));

                movingPart.process(gameData, player);
                positionPart.process(gameData, player);
            }
        }
    }
}
