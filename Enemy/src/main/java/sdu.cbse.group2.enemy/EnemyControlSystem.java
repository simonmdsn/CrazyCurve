package sdu.cbse.group2.enemy;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.AiSPI;
import sdu.cbse.group2.common.services.IEntityProcessingService;

public class EnemyControlSystem implements IEntityProcessingService {

    private AiSPI aiSPI;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            if (aiSPI != null) {
                aiSPI.move(enemy, world, 10); //TODO Search radius?
            } else {
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
            }

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
        }
    }

    public void setAiSPI(final AiSPI aiSPI) {
        this.aiSPI = aiSPI;
    }
}
