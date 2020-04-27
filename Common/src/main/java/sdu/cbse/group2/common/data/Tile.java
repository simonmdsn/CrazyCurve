package sdu.cbse.group2.common.data;

import lombok.Data;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

@Data
public class Tile {

    public static final int length = 30;
    private final PositionPart positionPart;

    public Tile(PositionPart positionPart) {
        this.positionPart = positionPart;
    }
}
