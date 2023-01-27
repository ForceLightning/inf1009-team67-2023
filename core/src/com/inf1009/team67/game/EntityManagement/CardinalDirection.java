package com.inf1009.team67.game.EntityManagement;

public enum CardinalDirection {
    NORTH(0, 1), EAST(1, 0), SOUTH(0, -1), WEST(-1, 0);

    public final int x, y;

    private CardinalDirection(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
