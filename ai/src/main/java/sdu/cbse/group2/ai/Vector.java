package sdu.cbse.group2.ai;

import lombok.*;

@Data
class Vector {

    private final double x, y;

    Vector subtract(final Vector other) {
        return new Vector(x - other.x, y - other.y);
    }

    double cross(Vector other) {
        return y * other.x - x * other.y;
    }
}
