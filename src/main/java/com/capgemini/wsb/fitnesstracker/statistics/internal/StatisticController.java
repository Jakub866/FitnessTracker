package com.capgemini.wsb.fitnesstracker.statistics.internal;


import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/statistics")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticServiceImpl statisticService;
    private final StatisticsMapper statisticsMapper;
    private final UserServiceImpl userService;

    @PostMapping
    ResponseEntity createStatistic(@RequestBody StatisticsDto statisticDto) throws InterruptedException {

        Optional<User> userOptional = userService.getUser(statisticDto.userId());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such user");
        }

        User user = userOptional.get();

        Statistics createdStatistic = statisticService.save(statisticsMapper.toEntity(statisticDto, user));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatistic);
    }

    @GetMapping("/{userId}")
    ResponseEntity statisticsByUserId(@PathVariable long userId){
        Optional<User> userOptional = userService.getUser(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such user");
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                statisticService.findAllTrainingsByUser(userId)
                        .stream().map(statisticsMapper::toDto).toList()
        );
    }
    @PutMapping("/{statisticId}")
    ResponseEntity updateByUserId(@PathVariable long statisticId, @RequestBody StatisticsDto statisticDto)
    {
        Optional<Statistics> statisticsOptional = statisticService.findByID(statisticId);

        if (statisticsOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no statistics with such id");
        }

        Statistics updateStatistic = statisticService.updateStatistic(statisticId,statisticDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateStatistic);
    }
    @GetMapping("/calories/{value}")
    ResponseEntity statisticByCalories(@PathVariable int value){
        List<Statistics> statisticsList = statisticService.findAllByCalories(value);
        return ResponseEntity.status(HttpStatus.FOUND).body(statisticsList);
    }

    @DeleteMapping("/{statisticId}")
    ResponseEntity statisticById(@PathVariable long statisticId){
        Statistics statisticsToDel = statisticService.findByID(statisticId)
                .orElseThrow(() -> new EntityNotFoundException("Statistic not found with id " + statisticId));
        ;
        return ResponseEntity.status(HttpStatus.OK).body(statisticService.delete(statisticsToDel));

    }

}
