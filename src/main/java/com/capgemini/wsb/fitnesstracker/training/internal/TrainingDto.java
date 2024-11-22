package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.util.Date;

record TrainingDto(@Nullable Long id, User user,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+00:00") Date startTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+00:00") Date endTime,
        ActivityType activityType, double distance, double averageSpeed) {
}

record TrainingDtoJson(@Nullable Long id, long userId,
                       @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date startTime,
                       @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endTime,
                       ActivityType activityType, double distance, double averageSpeed){

}