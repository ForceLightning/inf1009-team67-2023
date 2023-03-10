package com.inf1009.team67.engine.entitymanagement;

public enum CardinalDirection {
    // TODO(Review): Is this enum necessary?
    NORTH(0, 1), EAST(1, 0), SOUTH(0, -1), WEST(-1, 0);

    public final int x, y;

    private CardinalDirection(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
