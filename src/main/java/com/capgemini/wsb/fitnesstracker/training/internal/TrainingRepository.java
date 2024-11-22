package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    default List<Training> findByUserId(long id) {
        return findAll().stream()
                .filter( training-> Objects.equals(training.getUser().getId(), id)).toList();
    }

    default List<Training> findFinishedAfterTime(LocalDate afterTime) {
        Date afterDate = java.sql.Date.valueOf(afterTime);
        return findAll().stream()
                .filter(training -> training.getEndTime().after(afterDate))
                .collect(Collectors.toList());
    }

    default List<Training> findByActivity(String activity) {
        return findAll().stream()
                .filter(training -> training.getActivityType().toString().equalsIgnoreCase(activity))
                .collect(Collectors.toList());
    }

    default List<Training> findByUserAndDateBetween(User user, Date startDate, Date endDate){
        return findAll().stream()
                .filter(training -> training.getUser().getId().equals(user.getId()))
                .toList();
    }

    default List<Training> findFinishedAfterTimeFromList(List<Training> trainings, Date afterDate) {
        return trainings.stream()
                .filter(training -> training.getEndTime().after(afterDate))
                .collect(Collectors.toList());
    }
}
