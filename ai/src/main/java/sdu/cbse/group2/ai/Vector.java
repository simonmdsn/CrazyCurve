package sdu.cbse.group2.ai;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class Vector {

    private final double x, y;

    public Vector subtract(final Vector other) {
        final double x = this.x - other.x;
        final double y = this.y - other.y;
        return new Vector(x, y);
    }

    public Vector getDirection(final float radians) {
        return new Vector(Math.cos(radians), Math.sin(radians));
    }

    public double angle(final Vector other) {
        return Math.acos(dot(other) / (length() * other.length()));
    }

    public double dot(Vector other) {
        return x * other.x + y * other.y;
    }

    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
