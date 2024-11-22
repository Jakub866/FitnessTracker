package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider {
    private final TrainingRepository trainingRepository;

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    public List<Training> findAllTrainings() {
        return  trainingRepository.findAll();
    }

    public List<Training> findAllTrainingsByUser(long UserId){
        return trainingRepository.findByUserId(UserId);
    }

    public List<Training> findAllTrainingFinishedAfterTime(LocalDate afterTime){
        return trainingRepository.findFinishedAfterTime(afterTime);
    }

    public List<Training> findAllTrainingsActivity(String activity){
        return trainingRepository.findByActivity(activity);
    }
    public Optional<Training> getTrainingById(long Id){
        return trainingRepository.findById(Id);
    }

    public Training save(Training entity){
        return trainingRepository.save(entity);
    }

    public List<Training> findByUserAndDateBetween(User user, Date startDate, Date endDate) {
        return trainingRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    public List<Training> findFinishedAfterTimeFromList(List<Training> trainings, Date afterTime) {
        return trainingRepository.findFinishedAfterTimeFromList(trainings, afterTime);
    }
}
