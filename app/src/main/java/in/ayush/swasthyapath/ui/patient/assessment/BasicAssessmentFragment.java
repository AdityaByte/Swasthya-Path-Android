package in.ayush.swasthyapath.ui.patient.assessment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.button.MaterialButton;
import in.ayush.swasthyapath.AssessmentActivity;
import in.ayush.swasthyapath.R;
import in.ayush.swasthyapath.component.CustomInputField;
import in.ayush.swasthyapath.utils.Utility;

public class BasicAssessmentFragment extends Fragment {

    private AssessmentViewModel assessmentViewModel;
    private CustomInputField activityLevelIF, waterIntakeIF, mealFrequencyIF, sleepingScheduleIF, hoursOfSleepIF, foodGenreIF;
    private MaterialButton nextBtn;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_assessment, container, false);

        activityLevelIF = view.findViewById(R.id.activity_input);
        waterIntakeIF = view.findViewById(R.id.water_input);
        mealFrequencyIF = view.findViewById(R.id.meal_input);
        sleepingScheduleIF = view.findViewById(R.id.sleep_schedule_input);
        hoursOfSleepIF = view.findViewById(R.id.sleep_hours_input);
        foodGenreIF = view.findViewById(R.id.food_genre_input);
        nextBtn = view.findViewById(R.id.nextBtn);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assessmentViewModel = new ViewModelProvider(requireActivity()).get(AssessmentViewModel.class);

        // Defining the on click listener to the nextBtn;
        nextBtn.setOnClickListener(v -> {
            // Here we have to firstly check for all the fields then move forward.
            boolean isValid = Utility.isAllFieldsAreValid(activityLevelIF, waterIntakeIF, mealFrequencyIF, sleepingScheduleIF, hoursOfSleepIF, foodGenreIF);
            if (!isValid) {
                Toast.makeText(requireContext(), "Please fill out the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Else we have to set the data of the view model and move forward to the next page.
            assessmentViewModel.activityLevel = activityLevelIF.getText().toString().trim();
            assessmentViewModel.sleepingSchedule = sleepingScheduleIF.getText().toString().trim();
            assessmentViewModel.preferredFoodGenre = foodGenreIF.getText().toString().trim();
            try {
                assessmentViewModel.waterIntake = Float.parseFloat(waterIntakeIF.getText().toString().trim());
                assessmentViewModel.mealFrequency = Byte.parseByte(mealFrequencyIF.getText().toString().trim());
                assessmentViewModel.hoursOfSleep = Float.parseFloat(hoursOfSleepIF.getText().toString().trim());
            } catch (Exception ex) {
                Toast.makeText(requireContext(), "Please fill valid data, you should fill number in number specific fields", Toast.LENGTH_LONG).show();
                return;
            }

            // Now we have to move forward to the next window.
            ((AssessmentActivity) requireActivity()).goToNextPage();
        });
    }
}
