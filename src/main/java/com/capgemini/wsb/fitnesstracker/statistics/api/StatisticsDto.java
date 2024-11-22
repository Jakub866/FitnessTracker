package com.capgemini.wsb.fitnesstracker.statistics.api;
import jakarta.annotation.Nullable;

public record StatisticsDto(
        @Nullable Long id,
        long userId,
        int totalTrainings,
        double totalDistance,
        int totalCaloriesBurned
) {
}

