package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticServiceImpl {
    private final StatisticRepository statisticRepository;
    private final UserServiceImpl userService;


    public List<Statistics> findAllTrainingsByUser(long Id){
        return statisticRepository.findByUserId(Id);
    }
    public Statistics save(Statistics entity) {
        return statisticRepository.save(entity);
    }
    public Optional<Statistics> findByID(long Id) { return  statisticRepository.findById(Id);}

    public Statistics updateStatistic(long Id, StatisticsDto newStatisticsDto ) {
        Statistics statisticsExisted = statisticRepository.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Statistic not found with id " + Id));

        User userExisted = userService.getUser(newStatisticsDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        statisticsExisted.setUser(userExisted);
        statisticsExisted.setTotalDistance(newStatisticsDto.totalDistance());
        statisticsExisted.setTotalTrainings(newStatisticsDto.totalTrainings());
        statisticsExisted.setTotalCaloriesBurned(newStatisticsDto.totalCaloriesBurned());

        return statisticRepository.save(statisticsExisted);
    }

    public Statistics delete(Statistics statisticsToDel){
        statisticRepository.delete(statisticsToDel);
        return statisticsToDel;
    }

    public List<Statistics> findAllByCalories(int calories){
        return statisticRepository.findByCalories(calories);
    }
}
