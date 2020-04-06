package sdu.cbse.group2.collision;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonsnake.CommonSnake;
import sdu.cbse.group2.commonsnake.Tail;
import sdu.cbse.group2.commonweapon.CommonWeapon;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity commonSnakeOuter : world.getBoundedEntities(CommonSnake.class)) {
            boolean hasCollided = false;
            if (((CommonSnake) commonSnakeOuter).isAlive()) {
                //Check commonSnakeOuter for collision with tail of every snake, but not with the last 6 tail parts or if the snake is very small

                for (Entity commonSnakeInner : world.getBoundedEntities(CommonSnake.class)) {
                    List<Tail> tail = ((CommonSnake) commonSnakeInner).getTailList();
                    for (Entity tailPart : tail) {
                        if (tail.size() > 5 && !IntStream.rangeClosed(tail.size() - 6, tail.size()).boxed().collect(Collectors.toList()).contains(tail.indexOf(tailPart))) {
                            if(checkForCollision(commonSnakeOuter, tailPart)) {
                                hasCollided = true;
                                killSnake(commonSnakeOuter);
                                break;
                            }
                        }
                    }
                    if (hasCollided)
                        break;
                }

                for (Entity weapon : world.getBoundedEntities(CommonWeapon.class)) {
                    List<Tail> tail = ((CommonSnake) commonSnakeOuter).getTailList();
                    for (Entity tailPart : tail) {
                        if (tail.size() > 5 && !IntStream.rangeClosed(tail.size() - 6, tail.size()).boxed().collect(Collectors.toList()).contains(tail.indexOf(tailPart)) && ((CommonWeapon) weapon).isShooting()) {
                            if(!((CommonWeapon) weapon).getShooterUUID().equals(commonSnakeOuter.getUuid()) && checkForCollision(weapon, ((CommonSnake) commonSnakeOuter).getHead())){
                                hasCollided = true;
                                killSnake(commonSnakeOuter);
                                break;
                            }
                            if(checkForCollision(weapon, tailPart)) {
                                hasCollided = true;
                                world.removeEntity(tailPart);
                                tail.remove(tailPart);
                                break;
                            }
                        }
                    }
                    if (hasCollided)
                        break;
                }

                //Check for collision with edge of screen
                if (!hasCollided) {
                    for (Entity commonPowerUp : world.getBoundedEntities(CommonPowerUp.class)) {
                        if (checkForCollision(commonSnakeOuter, commonPowerUp)) {
                            ((CommonPowerUp) commonPowerUp).applyPowerUp((CommonSnake) commonSnakeOuter);
                            world.removeEntity(commonPowerUp);
                            hasCollided = true;
                            break;
                        }
                    }
                }
                if (!hasCollided) {
                    int gameWidth = gameData.getDisplayWidth();
                    int gameHeight = gameData.getDisplayHeight();
                    float headX = ((PositionPart) commonSnakeOuter.getPart(PositionPart.class)).getX();
                    float headY = ((PositionPart) commonSnakeOuter.getPart(PositionPart.class)).getY();
                    if (headX > gameWidth || headX < 0 || headY > gameHeight || headY < 0) {
                        killSnake(commonSnakeOuter);
                    }
                }
            }
        }
    }

    private boolean checkForCollision(Entity entity1, Entity entity2) {
        PositionPart entMov1 = entity1.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);
        float dx = (float) entMov1.getX() - (float) entMov2.getX();
        float dy = (float) entMov1.getY() - (float) entMov2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance < (entity1.getRadius() + entity2.getRadius())) {
            return true;
        }
        return false;
    }

    private void killSnake(Entity e) {
        ((CommonSnake) e).setAlive(false);
        ((MovingPart) e.getPart(MovingPart.class)).setMaxSpeed(0);
        ((MovingPart) e.getPart(MovingPart.class)).setRotationSpeed(0);
    }

}


