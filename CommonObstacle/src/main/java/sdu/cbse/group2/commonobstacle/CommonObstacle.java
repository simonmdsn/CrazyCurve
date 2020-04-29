package sdu.cbse.group2.commonobstacle;

import lombok.Getter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

@Getter
public class CommonObstacle extends Entity {

    private final float x;
    private final float y;
    private final PositionPart positionPart;

    public CommonObstacle(GameSprite gameSprite, float x, float y) {
        super(gameSprite);
        this.x = x;
        this.y = y;
        this.positionPart = new PositionPart(x, y, 0);
        add(positionPart);
    }
}
