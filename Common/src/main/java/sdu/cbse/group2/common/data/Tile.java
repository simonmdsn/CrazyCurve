package sdu.cbse.group2.common.data;

import lombok.Data;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tile {

    public static final int LENGTH = 30;
    private final PositionPart positionPart;
    private final List<Entity> entities = new ArrayList<>();

    public Tile(PositionPart positionPart) {
        this.positionPart = positionPart;
    }

    public boolean isObstructing() {
        return entities.stream().anyMatch(Entity::isObstructing);
    }
}
