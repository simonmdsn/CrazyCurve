package sdu.cbse.group2.round.spawn;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

@Getter
@Setter
public class SpawnPoint extends Entity {

    private float x, y, radians;
    private PositionPart positionPart;

    SpawnPoint(float x, float y, float radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;
        positionPart = new PositionPart(x, y, radians);
        add(positionPart);
    }

    void setSpawnPointOnEntity(Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        positionPart.setX(x);
        positionPart.setY(y);
        positionPart.setRadians(radians);
    }
}
