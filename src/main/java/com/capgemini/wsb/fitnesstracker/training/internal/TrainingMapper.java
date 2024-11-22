package com.capgemini.wsb.fitnesstracker.training.internal;


import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
class TrainingMapper {

    TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getUser(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    Training toEntity(TrainingDtoJson trainingDtoJson, User user) {

        return new Training(
                user,
                trainingDtoJson.startTime(),
                trainingDtoJson.endTime(),
                trainingDtoJson.activityType(),
                trainingDtoJson.distance(),
                trainingDtoJson.averageSpeed()
        );

    }
}