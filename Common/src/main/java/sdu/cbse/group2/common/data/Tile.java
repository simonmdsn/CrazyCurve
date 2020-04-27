package sdu.cbse.group2.common.data;

import sdu.cbse.group2.common.data.entityparts.PositionPart;

public class Tile extends Entity {

    public static final int length = 30;
    private final PositionPart positionPart;

    public Tile(PositionPart positionPart) {
        this.positionPart = positionPart;
    }
}
