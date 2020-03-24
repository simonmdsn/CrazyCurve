package sdu.cbse.group2.collision;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        //Check collision with any other snake than self
        for (Entity e : world.getEntities(CommonSnake.class)) {
            boolean hasCollided = false;
            for (Entity f : world.getEntities(CommonSnake.class)) {
                if(e.getUuid().equals(f.getUuid()))
                    continue;
                //Check the head of e for collision with every part of f's tail
                for (Entity tailPart : new ArrayList<>(((CommonSnake) f).getTail())){
                    if(checkForCollision(((CommonSnake)e).getHead(), tailPart)){
                        ((CommonSnake)e).getTailTask().cancel(true);
                        hasCollided = true;
                        break;
                    }
                }
                if (hasCollided)
                    break;
            }

            //Check for collision with edge of screen
            if(!hasCollided) {
                int width = gameData.getDisplayWidth();
                int height = gameData.getDisplayHeight();
                System.out.println("Head:" + ((CommonSnake) e).getHead());
                System.out.println("x: " + ((PositionPart)((CommonSnake) e).getHead().getPart(PositionPart.class)).getX());
                float headX = ((PositionPart) ((CommonSnake) e).getHead().getPart(PositionPart.class)).getX();
                float headY = ((PositionPart) ((CommonSnake) e).getHead().getPart(PositionPart.class)).getY();
                if (headX > width || headX < 0 || headY > height || headY < 0) {
                    ((CommonSnake) e).getTailTask().cancel(true);
                }
            }
        }
    }

    public boolean checkForCollision(Entity entity1, Entity entity2) {
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

}


