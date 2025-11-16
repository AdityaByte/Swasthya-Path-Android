package in.ayush.swasthyapath.ui.patient.assessment;

import androidx.lifecycle.ViewModel;
import in.ayush.swasthyapath.dto.*;

import java.util.List;

/**
 * AssessmentViewModel: A single view model is been used for all the assessment so that
 * the data should be at the single place.
 */
public class AssessmentViewModel extends ViewModel {
    
    // Basic assessment fields.
    public String activityLevel;
    public float waterIntake;
    public byte mealFrequency;
    public String sleepingSchedule;
    public float hoursOfSleep;
    public String preferredFoodGenre;
    
    // Health assessment fields.
    public List<String> healthIssues;
    public List<String> allergies;
    public List<String> preferredTastes;
    public String agni;
    
    // Prakruti assessment fields.
    public String bodyType;
    public String skinNature;
    public String digestionStrength;
    public String energyPattern;
    public String sleepNature;
    
    // Vikruti assessment fields.
    public String currentConcerns;
    public String currentEnergy;
    public String currentSleep;
    public String digestionToday;
    
    public AssessmentDTO getAssessment() {
        return AssessmentDTO.builder()
                .basicAssessment(new BasicAssessment(activityLevel, waterIntake, mealFrequency, sleepingSchedule, hoursOfSleep, preferredFoodGenre))
                .healthAssessment(new HealthAssessment(healthIssues, allergies, preferredTastes, agni))
                .prakrutiAssessment(new PrakrutiAssessment(bodyType, skinNature, digestionStrength, energyPattern, sleepNature))
                .vikrutiAssessment(new VikrutiAssessment(currentConcerns, currentEnergy, currentSleep, digestionToday))
                .build();
    }
}
