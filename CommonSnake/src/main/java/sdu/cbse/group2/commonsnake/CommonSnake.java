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

@Getter
@Setter
public class CommonSnake extends Entity {

    private final Entity head;
    private final GameSprite tailSprite;
    private final List<Tail> tailList = new LinkedList<>();
    private boolean isAlive;
    private float maxSpeed = 100;
    private float rotationSpeed = 3;

    public CommonSnake(GameSprite gameSprite, GameSprite tailSprite) {
        super(gameSprite);
        this.head = new Entity(gameSprite);
        this.tailSprite = tailSprite;
        this.isAlive = true;
        add(new MovingPart(this.getMaxSpeed(), this.getRotationSpeed()));
        add(new PositionPart(0, 0, 0));
    }

    public void deleteAndEmptyTails(World world) {
        tailList.forEach(world::removeEntity);
        tailList.clear();
    }

}
