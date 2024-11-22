package com.capgemini.wsb.fitnesstracker.mail.internal;


import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MailController {


    private final MailServiceImpl mailService;
    private final UserServiceImpl userService;
    private final TrainingServiceImpl trainingService;

    @Autowired
    public MailController(MailServiceImpl mailService, UserServiceImpl userService, TrainingServiceImpl trainingServiceImpl) {
        this.mailService = mailService;
        this.userService = userService;
        this.trainingService = trainingServiceImpl;
    }


    @Scheduled(cron = "0 0 0  * * MON")
    public void weeklyMessage() {
        LocalDate lastWeek = LocalDate.now().minusDays(7);
        Date lastWeekDate = java.sql.Date.valueOf(lastWeek);
        LocalDate currenTime = LocalDate.now();
        Date currenTimeDate = java.sql.Date.valueOf(currenTime);
        List<User> users = userService.findAllUsers();

        for (User user : users) {
            List<Training> trainings = trainingService.findByUserAndDateBetween(user, lastWeekDate, currenTimeDate);
            List<Training> filteredTrainings = trainingService.findFinishedAfterTimeFromList(trainings, lastWeekDate);
            String report = generateReport(filteredTrainings);
            EmailDto emailDto = new EmailDto(user.getEmail(), "Weekly Training Report", report);
            mailService.send(emailDto);
        }
    }

    private String generateReport(List<Training> trainings) {
        return trainings.stream()
                .map(training -> String.format("Date: %s, Activity: %s, Distance %s, all traings done in this week: %s", training.getEndTime(), training.getActivityType(), training.getDistance(), trainings.size()))
                .collect(Collectors.joining("\n"));
    }
}
