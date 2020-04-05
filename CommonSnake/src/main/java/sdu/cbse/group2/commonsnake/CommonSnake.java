package sdu.cbse.group2.commonsnake;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class CommonSnake extends Entity {

    private final Entity head;
    private final GameSprite tailSprite;
    //private final List<Entity> tailList = new LinkedList<>();
    private final List<Tail> tailList = new LinkedList<>();
    private final List<Float> tailCount = new LinkedList<>();
    private boolean isAlive;


    public CommonSnake(GameSprite gameSprite, GameSprite tailSprite) {
        super(gameSprite);
        this.head = new Entity(gameSprite);
        this.tailSprite = tailSprite;
        this.isAlive = true;
//        startTailTask();
    }

    private void deleteAndEmptyTails(World world) {
        tailList.forEach(world::removeEntity);
        tailList.clear();
    }

    //TODO implement holes in snake
//    private void startTailTask() {
//         tailTask = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
//            Entity entity = new Entity(tailSprite);
//            PositionPart part = getPart(PositionPart.class);
//            entity.add(new PositionPart(part.getX(), part.getY(), part.getRadians()));
//            tail.add(entity);
//            world.addEntity(entity);
//        }, 10, 150, TimeUnit.MILLISECONDS);
//    }
}
