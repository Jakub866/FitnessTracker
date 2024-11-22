package com.capgemini.wsb.fitnesstracker.statistics.internal;


import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import org.springframework.stereotype.Component;

@Component
class StatisticsMapper {
    StatisticsDto toDto(Statistics statistics){
        return new StatisticsDto(
                statistics.getId(),
                statistics.getUser().getId(),
                statistics.getTotalTrainings(),
                statistics.getTotalDistance(),
                statistics.getTotalCaloriesBurned()
        );
    }

    Statistics toEntity(StatisticsDto statisticsDto, User user){
        return new Statistics(
                user,
                statisticsDto.totalTrainings(),
                statisticsDto.totalDistance(),
                statisticsDto.totalCaloriesBurned()
        );
    }
}
