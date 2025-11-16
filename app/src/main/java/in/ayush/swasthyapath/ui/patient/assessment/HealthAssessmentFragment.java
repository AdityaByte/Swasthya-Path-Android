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
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class HealthAssessmentFragment extends Fragment {

    private AssessmentViewModel viewModel;
    private CustomInputField healthIssuesIF, allergiesIF, tastesIF, agniIF;
    private MaterialButton nextBtn;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_assessment, container, false);

        healthIssuesIF = view.findViewById(R.id.health_issues_input);
        allergiesIF = view.findViewById(R.id.allergies_input);
        tastesIF = view.findViewById(R.id.tastes_input);
        agniIF = view.findViewById(R.id.agni_input);
        nextBtn = view.findViewById(R.id.nextBtnHealth);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Here we have to get the viewmodel.
        viewModel = new ViewModelProvider(requireActivity()).get(AssessmentViewModel.class);

        nextBtn.setOnClickListener(v -> {
            boolean isValid = Utility.isAllFieldsAreValid(healthIssuesIF, allergiesIF, tastesIF, agniIF);
            if (!isValid) {
                Toast.makeText(requireContext(), "Please fill out the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Else we need to save the fields to the viewmodel.
            viewModel.healthIssues = Arrays.asList(healthIssuesIF.getText().toString().trim().split(","));
            viewModel.allergies = Arrays.asList(allergiesIF.getText().toString().trim().split(","));
            viewModel.preferredTastes = Arrays.asList(tastesIF.getText().toString().trim().split(","));
            viewModel.agni = agniIF.getText().toString().trim();

            ((AssessmentActivity) requireActivity()).goToNextPage();
        });
    }
}
