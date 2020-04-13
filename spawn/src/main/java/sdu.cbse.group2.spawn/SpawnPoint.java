package sdu.cbse.group2.spawn;

import lombok.Data;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

@Data
public class SpawnPoint extends Entity {

    private float x, y, radians;
    private PositionPart positionPart;

    public SpawnPoint(float x, float y, float radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;
        this.positionPart = new PositionPart(x, y, radians);
        add(positionPart);
    }

    public void setSpawnPointOnEntity(Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        positionPart.setX(x);
        positionPart.setY(y);
        positionPart.setRadians(radians);
    }
}
