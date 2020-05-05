package sdu.cbse.group2.commonsnake;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class CommonSnake extends Entity {

    private final GameSprite tailSprite;
    private final List<Tail> tailList = new LinkedList<>();
    private boolean isAlive;
    private boolean activeTail;
    private float maxSpeed = 100;
    private float rotationSpeed = 3;
    private String name;

    public CommonSnake(GameSprite gameSprite, GameSprite tailSprite, String name) {
        super(gameSprite);
        this.tailSprite = tailSprite;
        isAlive = true;
        activeTail = true;
        add(new MovingPart(getMaxSpeed(), getRotationSpeed()));
        add(new PositionPart(0, ThreadLocalRandom.current().nextInt(1000), 0));
        this.name = name;
    }

    public void deleteTail(World world) {
        tailList.forEach(world::removeEntity);
        tailList.clear();
    }

    public void kill() {
        setAlive(false);
    }

    public void revive(World world) {
        deleteTail(world);
        setAlive(true);
    }

}
