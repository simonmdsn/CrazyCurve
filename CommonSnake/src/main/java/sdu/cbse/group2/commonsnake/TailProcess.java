package sdu.cbse.group2.commonsnake;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        Tail tail = new Tail(snake.getTailSprite(), snake);
        PositionPart part = snake.getPart(PositionPart.class);
        tail.add(new PositionPart(part.getX(), part.getY(), part.getRadians()));

        snake.getTailList().add(tail);
        world.addEntity(tail);
    }

    @Override
    public void process(GameData gameData, World world) {
        for (CommonSnake commonSnake : world.getBoundedEntities(CommonSnake.class).stream().map(CommonSnake.class::cast).collect(Collectors.toList())) {
            if (commonSnake.getTailList().isEmpty() || commonSnake.isAlive() && distance(commonSnake, commonSnake.getTailList().getLast()) > DRAW_DISTANCE) {
                if (commonSnake.isActiveTail()) {
                    if (ThreadLocalRandom.current().nextInt(1000) > 950) {
                        disableActiveTail(commonSnake);
                    } else createTail(world, commonSnake);
                }
            }
        }
    }

    //Temporarily disables a snake tail
    private void disableActiveTail(CommonSnake snake) {
        snake.setActiveTail(false);
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                    snake.setActiveTail(true);
                }
                //The delay is set to the normal speed divided by the current speed mupltiplied with 4 times the normal speed to get the appropriate hole sizes independently of snake speed
                , (long) ((snake.getMaxSpeed() / ((MovingPart) snake.getPart(MovingPart.class)).getMaxSpeed() * (6 * snake.getMaxSpeed()))), TimeUnit.MILLISECONDS);
    }
}
