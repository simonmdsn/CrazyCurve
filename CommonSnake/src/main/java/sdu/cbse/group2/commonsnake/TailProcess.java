package sdu.cbse.group2.commonsnake;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TailProcess implements IEntityProcessingService {

    private static final float DRAW_DISTANCE = 16;

    private float distance(Entity entity1, Entity entity2) {
        PositionPart positionPart1 = entity1.getPart(PositionPart.class);
        PositionPart positionPart2 = entity2.getPart(PositionPart.class);
        float dx = positionPart1.getX() - positionPart2.getX();
        float dy = positionPart1.getY() - positionPart2.getY();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private void createTail(World world, CommonSnake snake) {
        Tail tail = new Tail(snake.getTailSprite());
        PositionPart part = snake.getPart(PositionPart.class);
        tail.add(new PositionPart(part.getX(), part.getY(), part.getRadians()));
        snake.getTailList().add(tail);
        world.addEntity(tail);
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity commonSnake : world.getEntities(CommonSnake.class)) {
            CommonSnake snake = ((CommonSnake) commonSnake);
            if (snake.getTailList().isEmpty()) {
                createTail(world, snake);
            } else if (distance(snake, snake.getTailList().get(snake.getTailList().size() - 1)) > DRAW_DISTANCE) {
                if (snake.isActiveTail()) {
                    if (ThreadLocalRandom.current().nextInt(1000) > 950) {
                        disableActiveTail(snake);
                    } else {
                        createTail(world, snake);
                    }
                }
            }
        }
    }

    //Temporarily disables a snakes tail
    private void disableActiveTail(CommonSnake snake){
        snake.setActiveTail(false);
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            snake.setActiveTail(true);
        }, 255, TimeUnit.MILLISECONDS);
    }
}
