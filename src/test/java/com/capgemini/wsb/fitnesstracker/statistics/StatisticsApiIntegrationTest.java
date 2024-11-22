package com.capgemini.wsb.fitnesstracker.statistics;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsDto;
import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticRepository;
import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticServiceImpl;
import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsMapper;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StatisticServiceImpl statisticService;

    @Autowired
    private StatisticRepository statisticsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StatisticsMapper statisticsMapper;

    private User testUser;
    private StatisticsDto statisticsDto;
    private Statistics statistics;

    @BeforeEach
    void setUp() {
        testUser = new User("test-user-id", "John Doe", LocalDate.of(1990, 1, 1), "john@example.com");
        statisticsDto = new StatisticsDto(testUser.getId(), 10.0, 100);
        statistics = new Statistics();
        statistics.setId(1L);
        statistics.setUser(testUser);
        statistics.setTotalDistance(10.0);
        statistics.setTotalCaloriesBurned(100);

        // Mock behavior
        when(userService.getUser(Mockito.anyString())).thenReturn(Optional.of(testUser));
        when(userService.getUser(Mockito.anyLong())).thenReturn(Optional.of(testUser));
        when(statisticService.save(Mockito.any())).thenReturn(statistics);
        when(statisticService.findByID(Mockito.anyLong())).thenReturn(Optional.of(statistics));
        when(statisticService.findAllTrainingsByUser(Mockito.anyLong())).thenReturn(Collections.singletonList(statistics));
        when(statisticService.findAllByCalories(Mockito.anyInt())).thenReturn(Collections.singletonList(statistics));
        when(statisticService.updateStatistic(Mockito.anyLong(), Mockito.any())).thenReturn(statistics);
    }

    @Test
    void createStatistic_ShouldReturnCreatedStatistic() throws Exception {
        mockMvc.perform(post("/api/statistics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statisticsDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalDistance", is(10.0)))
                .andExpect(jsonPath("$.totalCaloriesBurned", is(100)));
    }

    @Test
    void getStatisticsByUserId_ShouldReturnStatisticsList() throws Exception {
        mockMvc.perform(get("/api/statistics/{userId}", testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].totalDistance", is(10.0)));
    }

    @Test
    void updateStatisticById_ShouldReturnUpdatedStatistic() throws Exception {
        mockMvc.perform(put("/api/statistics/{statisticId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statisticsDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalDistance", is(10.0)))
                .andExpect(jsonPath("$.totalCaloriesBurned", is(100)));
    }

    @Test
    void getStatisticsByCalories_ShouldReturnMatchingStatistics() throws Exception {
        mockMvc.perform(get("/api/statistics/calories/{value}", 100)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].totalCaloriesBurned", is(100)));
    }

    @Test
    void deleteStatisticById_ShouldReturnDeletedStatus() throws Exception {
        mockMvc.perform(delete("/api/statistics/{statisticId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createStatistic_ShouldReturnNotFoundIfUserDoesNotExist() throws Exception {
        when(userService.getUser(Mockito.anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/statistics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statisticsDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no such user"));
    }

    @Test
    void getStatisticsByUserId_ShouldReturnNotFoundIfUserDoesNotExist() throws Exception {
        when(userService.getUser(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/statistics/{userId}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no such user"));
    }
}