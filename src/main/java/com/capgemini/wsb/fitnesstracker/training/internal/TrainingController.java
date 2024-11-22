package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingMapper;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;
    private final UserServiceImpl userService;


    @GetMapping
    public List<TrainingDto> allTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public List<TrainingDto> allTrainingByUserId(@PathVariable Long id) {
        return trainingService.findAllTrainingsByUser(id)
                .stream()
                .map(trainingMapper::toDto).toList();
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> allTrainingFinishedAfterTime(@PathVariable LocalDate afterTime) {
        return trainingService.findAllTrainingFinishedAfterTime(afterTime).stream()
                .map(trainingMapper::toDto).toList();
    }

    @GetMapping("/activityType")
    public List<TrainingDto> allTrainingByActivity(@RequestParam String activityType){

        return trainingService.findAllTrainingsActivity(activityType).stream()
                .map(trainingMapper::toDto).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Training createTraining(@RequestBody TrainingDtoJson trainingDtoJson) throws InterruptedException {

        Optional<User> userOptional = userService.getUser(trainingDtoJson.userId());

        User user = userOptional.orElseThrow(() ->
                new RuntimeException("User not found with id: " + trainingDtoJson.userId())
        );

        return trainingService.save(trainingMapper.toEntity(trainingDtoJson, user));
    }
    @PutMapping("/{trainingId}")
    public Training updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDtoJson trainingDtoJson) {

        Optional<Training> existingTrainingOptional = trainingService.getTrainingById(trainingId);


        Optional<User> userOptional = userService.getUser(trainingDtoJson.userId());

        User user = userOptional.orElseThrow(() ->
                new RuntimeException("User not found with id: " + trainingDtoJson.userId())
        );

        return trainingService.save(trainingMapper.toEntity(trainingDtoJson, user));
    }
}

