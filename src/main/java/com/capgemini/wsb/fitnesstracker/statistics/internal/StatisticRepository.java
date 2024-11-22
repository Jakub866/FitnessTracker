package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;

public interface StatisticRepository extends JpaRepository<Statistics, Long> {
    default List<Statistics> findByUserId(long id) {
        return findAll().stream()
                .filter( statistics-> Objects.equals(statistics.getUser().getId(), id)).toList();
    }

    default List<Statistics> findByCalories(int calories) {
        return findAll().stream().filter(statistics -> statistics.getTotalCaloriesBurned() > calories ).toList();
    }

}
