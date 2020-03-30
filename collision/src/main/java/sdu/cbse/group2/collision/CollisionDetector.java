package sdu.cbse.group2.collision;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity e : world.getEntities(CommonSnake.class)) {
            boolean hasCollided = false;
            if(((CommonSnake)e).isAlive()) {
                //Check e for collision with tail of every snake, but not with the last 6 tail parts or if the snake is very small
                for (Entity f : world.getEntities(CommonSnake.class)) {
                    List<Entity> tail = ((CommonSnake) f).getTail();
                    for (Entity tailPart : tail) {
                        if (tail.size() > 5 && !IntStream.rangeClosed(tail.size() - 6, tail.size()).boxed().collect(Collectors.toList()).contains(tail.indexOf(tailPart)) && checkForCollision(e, tailPart)) {
                            hasCollided = true;
                            killSnake(e);
                            break;
                        }
                    }
                    if (hasCollided)
                        break;
                }

                //Check for collision with edge of screen
                if(!hasCollided) {
                    int gameWidth = gameData.getDisplayWidth();
                    int gameHeight = gameData.getDisplayHeight();
                    float headX = ((PositionPart) e.getPart(PositionPart.class)).getX();
                    float headY = ((PositionPart) e.getPart(PositionPart.class)).getY();
                    if (headX > gameWidth|| headX < 0|| headY > gameHeight || headY < 0) {
                        killSnake(e);
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

    private void killSnake(Entity e){
        ((CommonSnake)e).setAlive(false);
        ((CommonSnake) e).getTailTask().cancel(true);
        ((MovingPart)e.getPart(MovingPart.class)).setMaxSpeed(0);
        ((MovingPart)e.getPart(MovingPart.class)).setRotationSpeed(0);
    }

}


