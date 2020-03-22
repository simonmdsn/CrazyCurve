package sdu.cbse.group2.commonsnake;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class CommonSnake extends Entity {

    private final Entity head;
    private final GameSprite tailSprite;
    private final List<Entity> tail = new LinkedList<>();
    private final World world;

    public CommonSnake(GameSprite gameSprite, GameSprite tailSprite, World world) {
        super(gameSprite);
        this.head = new Entity(gameSprite);
        this.tailSprite = tailSprite;
        this.world = world;
        startTask();
    }

    //TODO implement holes in snake
    private void startTask() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            Entity entity = new Entity(tailSprite);
            PositionPart part = getPart(PositionPart.class);
            entity.add(new PositionPart(part.getX(),part.getY(),part.getRadians()));
            tail.add(entity);
            world.addEntity(entity);
        },0,200, TimeUnit.MILLISECONDS);
    }
}