package com.inf1009.team67.game.food;


public class FoodFactory {
    public static HealthPack createFood(String type) {
        if (type.equals("healthy")) {
            return new HealthyFood();
        } else if (type.equals("unhealthy")) {
            return new UnhealthyFood();
        } else {
            return null;
        }
    }

    public static HealthPack createFood(int seed, int health, float movementSpeedAilment, float maxHealthAilment) {
        // 50% chance to create healthy food from a rng
        if (seed % 2 == 0) {
            return new HealthyFood(health, movementSpeedAilment, maxHealthAilment);
        } else {
            return new UnhealthyFood(health, movementSpeedAilment, maxHealthAilment);
        }
    }

    public static HealthPack createFood(int seed) {
        if (seed % 2 == 0) {
            return new HealthyFood();
        } else {
            return new UnhealthyFood();
        }
    }
}
