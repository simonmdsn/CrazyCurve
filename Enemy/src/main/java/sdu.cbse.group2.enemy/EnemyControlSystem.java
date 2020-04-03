package sdu.cbse.group2.enemy;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.commonsnake.CommonSnake;

public class EnemyControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(CommonSnake.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);

            // TODO Update Movement Method
            double randomMovement = (Math.random() * 100);
            if (randomMovement < 40) {
                movingPart.setLeft(true);
                movingPart.setRight(false);
            } else if (randomMovement > 50 && randomMovement < 90) {
                movingPart.setLeft(false);
                movingPart.setRight(true);
            } else if (randomMovement > 40 && randomMovement < 50) {
                movingPart.setLeft(false);
                movingPart.setRight(false);
            }

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
        }
    }
}