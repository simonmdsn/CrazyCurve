package sdu.cbse.group2.collision;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.commonobstacle.CommonObstacle;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonsnake.CommonSnake;
import sdu.cbse.group2.commonsnake.Tail;
import sdu.cbse.group2.commonweapon.CommonWeapon;

import java.util.Iterator;
import java.util.stream.Collectors;

public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (CommonSnake commonSnakeOuter : world.getBoundedEntities(CommonSnake.class).stream().map(CommonSnake.class::cast).collect(Collectors.toList())) {
            boolean hasCollided = false;
            if (commonSnakeOuter.isAlive()) {
                //Check commonSnakeOuter for collision with tail of every snake, but not with the last 6 tail parts or if the snake is very small
                for (Tail tail : world.getBoundedEntities(Tail.class).stream().map(Tail.class::cast).collect(Collectors.toList())) {
                    Iterator<Tail> tailIterator = commonSnakeOuter.getTailList().descendingIterator();
                    if (commonSnakeOuter.getTailList().size() > 2 && tailIterator.next() != tail && tailIterator.next() != tail) {
                        if (checkForCollision(commonSnakeOuter, tail)) {
                            hasCollided = true;
                            kill(commonSnakeOuter);
                            break;
                        }
                    }
                }
                if (hasCollided)
                    break;
            }

            for (CommonObstacle obstacle : world.getBoundedEntities(CommonObstacle.class).stream().map(CommonObstacle.class::cast).collect(Collectors.toList())) {
                if (checkForCollision(commonSnakeOuter, obstacle)) {
                    hasCollided = true;
                    kill(commonSnakeOuter);
                    break;
                }
            }
            if (hasCollided)
                break;

            for (Entity weapon : world.getBoundedEntities(CommonWeapon.class)) {
                for (Tail tail : world.getEntities(Tail.class).stream().map(Tail.class::cast).collect(Collectors.toList())) {
                    if (((CommonWeapon) weapon).isShooting()) {
                        //If weapon hits a snake's head
                        if (!((CommonWeapon) weapon).getShooterUUID().equals(commonSnakeOuter.getUuid()) && checkForCollision(weapon, commonSnakeOuter)) {
                            hasCollided = true;
                            kill(commonSnakeOuter);
                            break;
                        }
                        //If weapon hits a snake's tail
                        if (checkForCollision(weapon, tail)) {
                            hasCollided = true;
                            world.removeEntity(tail);
                            tail.getCommonSnake().getTailList().remove(tail);
                            break;
                        }
                    }
                }
            }

            if (hasCollided) break;

            //Check for collision with powerups
            for (Entity commonPowerUp : world.getBoundedEntities(CommonPowerUp.class)) {
                if (checkForCollision(commonSnakeOuter, commonPowerUp)) {
                    ((CommonPowerUp) commonPowerUp).applyPowerUp(commonSnakeOuter);
                    world.removeEntity(commonPowerUp);
                    hasCollided = true;
                    break;
                }
            }
            //Check for collision with edge of screen (-20 because the edge is weird?)
            if (!hasCollided) {
                PositionPart pos = commonSnakeOuter.getPart(PositionPart.class);
                if (pos.getX() > gameData.getDisplayWidth() - 20 || pos.getX() < 0 || pos.getY() > gameData.getDisplayWidth() || pos.getY() < 0) {
                    kill(commonSnakeOuter);
                }
            }
        }
    }


    private boolean checkForCollision(Entity entity1, Entity entity2) {
        PositionPart entMov1 = entity1.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);
        float dx = entMov1.getX() - entMov2.getX();
        float dy = entMov1.getY() - entMov2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    private void kill(CommonSnake commonSnake) {
        if (commonSnake.isAlive()) {
            CollisionSound.getSoundSPI().playSound("audio/death.mp3", .5f, false);
        }
        commonSnake.kill();
    }
}


