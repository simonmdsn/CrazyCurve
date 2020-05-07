package sdu.cbse.group2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sdu.cbse.group2.common.services.DrawSPI;

import java.util.HashSet;
import java.util.Set;

public class LibGDXDraw implements DrawSPI {

    public final static Set<Point> POINT_SET = new HashSet<>();

    @Override
    public void drawCircle(final int x, final int y, final int radius) {
        POINT_SET.add(new Point(x, y, radius));
    }

    @Getter
    @RequiredArgsConstructor
    public static class Point {
        private final int x, y, radius;
    }
}
