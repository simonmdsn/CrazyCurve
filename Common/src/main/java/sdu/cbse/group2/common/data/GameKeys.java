package sdu.cbse.group2.common.data;

public class GameKeys {

    private static final int NUM_KEYS = 8;
    private static final boolean[] KEYS = new boolean[NUM_KEYS];
    private static final boolean[] PKEYS = new boolean[NUM_KEYS];
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    public static final int ENTER = 4;
    public static final int ESCAPE = 5;
    public static final int SPACE = 6;
    public static final int SHIFT = 7;

    public void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            PKEYS[i] = KEYS[i];
        }
    }

    public void setKey(int k, boolean b) {
        KEYS[k] = b;
    }

    public boolean isDown(int k) {
        return KEYS[k];
    }

    public boolean isPressed(int k) {
        return KEYS[k] && !PKEYS[k];
    }

}
