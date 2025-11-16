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

public class VikrutiAssessmentFragment extends Fragment {

    private AssessmentViewModel viewModel;
    private CustomInputField concernIF, energyIF, sleepIF, digestionIF;
    private MaterialButton finishBtn;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vikruti_assessment, container, false);

        concernIF = view.findViewById(R.id.current_concerns_input);
        energyIF = view.findViewById(R.id.current_energy_input);
        sleepIF = view.findViewById(R.id.current_sleep_input);
        digestionIF = view.findViewById(R.id.digestion_today_input);
        finishBtn = view.findViewById(R.id.finishBtn);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AssessmentViewModel.class);

        finishBtn.setOnClickListener(v -> {
            boolean isValid = Utility.isAllFieldsAreValid(concernIF, energyIF, sleepIF, digestionIF);
            if (!isValid) {
                Toast.makeText(requireContext(), "Please fill out the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.currentConcerns = concernIF.getText().toString().trim();
            viewModel.currentEnergy = energyIF.getText().toString().trim();
            viewModel.currentSleep = sleepIF.getText().toString().trim();
            viewModel.digestionToday = digestionIF.getText().toString().trim();

            ((AssessmentActivity) requireActivity()).goToNextPage();
        });
    }
}
