package sdu.cbse.group2.enemy;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.data.entityparts.TimerPart;
import sdu.cbse.group2.common.services.AiSPI;
import sdu.cbse.group2.common.services.IEntityProcessingService;

import java.util.Optional;
import java.util.stream.Collectors;

public class EnemyControlSystem implements IEntityProcessingService {

    private AiSPI aiSPI;

    @Override
    public void process(GameData gameData, World world) {
        for (Enemy enemy : world.getEntities(Enemy.class).stream().map(Enemy.class::cast).collect(Collectors.toList())) {
            if (enemy.isAlive()) {
                PositionPart positionPart = enemy.getPart(PositionPart.class);
                MovingPart movingPart = enemy.getPart(MovingPart.class);
                TimerPart aiTimerPart = enemy.getPart(TimerPart.class);
                if (aiTimerPart.getExpiration() <= 0) {
                    if (aiSPI != null) {
                        aiSPI.move(enemy, world, 10); //TODO Search radius?
                    } else {
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
                    aiTimerPart.setExpiration(0.05F);
                }
                aiTimerPart.process(gameData, enemy);
                movingPart.process(gameData, enemy);
                Optional.ofNullable(world.getNearestTile((int) positionPart.getX(), (int) positionPart.getY())).ifPresent(tile -> tile.getEntities().remove(enemy));
                positionPart.process(gameData, enemy);
                Optional.ofNullable(world.getNearestTile((int) positionPart.getX(), (int) positionPart.getY())).ifPresent(tile -> tile.getEntities().add(enemy));

            }
        }
    }

    public void setAiSPI(final AiSPI aiSPI) {
        this.aiSPI = aiSPI;
    }
}
