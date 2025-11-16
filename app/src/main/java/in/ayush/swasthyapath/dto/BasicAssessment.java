package in.ayush.swasthyapath.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicAssessment {
    private String activityLevel;
    private float waterIntake;
    private byte mealFrequency;
    private String sleepingSchedule;
    private float hoursOfSleep;
    private String preferredFoodGenre;
}
