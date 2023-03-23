package com.inf1009.team67.game.food;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class FoodFactory {

    private static Pool<HealthyFood> healthyFoodPool = Pools.get(HealthyFood.class);
    private static Pool<UnhealthyFood> unhealthyFoodPool = Pools.get(UnhealthyFood.class);
    private static FoodFactory instance;

    private FoodFactory() {
        instance = this;
    }

    public static void allocatePools(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            healthyFoodPool.free(new HealthyFood());
            unhealthyFoodPool.free(new UnhealthyFood());
        }
    }

    public static FoodFactory getInstance() {
        if (instance == null) {
            instance = new FoodFactory();
        }
        return instance;
    }

    public static HealthPack createFood(String type) {
        if (type.equals("healthy")) {
            HealthyFood healthyFood = healthyFoodPool.obtain();
            healthyFood.init();
            return healthyFood;
        } else if (type.equals("unhealthy")) {
            UnhealthyFood unhealthyFood = unhealthyFoodPool.obtain();
            unhealthyFood.init();
            return unhealthyFood;
        } else {
            return null;
        }
    }

    public static HealthPack createFood(int seed, int health, float movementSpeedAilment, float maxHealthAilment) {
        // 50% chance to create healthy food from a rng
        if (seed % 2 == 0) {
            HealthyFood healthyFood = healthyFoodPool.obtain();
            healthyFood.init(health, movementSpeedAilment, maxHealthAilment);
            return healthyFood;
        } else {
            UnhealthyFood unhealthyFood = unhealthyFoodPool.obtain();
            unhealthyFood.init(health, movementSpeedAilment, maxHealthAilment);
            return unhealthyFood;
        }
    }

    public static HealthPack createFood(int seed) {
        if (seed % 2 == 0) {
            HealthyFood healthyFood = healthyFoodPool.obtain();
            healthyFood.init();
            return healthyFood;
        } else {
            UnhealthyFood unhealthyFood = unhealthyFoodPool.obtain();
            unhealthyFood.init();
            return unhealthyFood;
        }
    }

    public static void freeFood(HealthPack food) {
        if (food instanceof HealthyFood) {
            healthyFoodPool.free((HealthyFood) food);
        } else if (food instanceof UnhealthyFood) {
            unhealthyFoodPool.free((UnhealthyFood) food);
        }
    }
}
