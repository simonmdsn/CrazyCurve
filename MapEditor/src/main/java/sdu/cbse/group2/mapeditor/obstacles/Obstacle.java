package sdu.cbse.group2.mapeditor.obstacles;


import lombok.Data;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

@Data
public class Obstacle extends Entity {

    private float x, y;
    private PositionPart positionPart;

    public Obstacle(GameSprite gameSprite, float x, float y) {
        super(gameSprite);
        this.x = x;
        this.y = y;
        this.positionPart = new PositionPart(x, y, 0);
        add(positionPart);
    }
}
