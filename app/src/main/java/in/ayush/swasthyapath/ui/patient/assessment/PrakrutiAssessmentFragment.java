package in.ayush.swasthyapath.ui.patient.assessment;

import android.annotation.SuppressLint;
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

public class PrakrutiAssessmentFragment extends Fragment {

    private AssessmentViewModel viewModel;
    private CustomInputField bodyTypeIF, skinIF, digestionIF, energyIF, sleepIF;
    private MaterialButton nextBtn;

    @SuppressLint("CutPasteId")
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prakruti_assessment, container, false);

        bodyTypeIF = view.findViewById(R.id.body_type_input);
        skinIF = view.findViewById(R.id.skin_type_input);
        digestionIF = view.findViewById(R.id.digestion_strength_input);
        energyIF = view.findViewById(R.id.energy_pattern_input);
        sleepIF = view.findViewById(R.id.sleep_nature_input);
        nextBtn = view.findViewById(R.id.nextBtnPrakruti);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AssessmentViewModel.class);

        nextBtn.setOnClickListener(v -> {
            boolean isValid = Utility.isAllFieldsAreValid(bodyTypeIF, skinIF, digestionIF, energyIF, sleepIF);
            if (!isValid) {
                Toast.makeText(requireContext(), "Please fill out the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.bodyType = bodyTypeIF.getText().toString().trim();
            viewModel.skinNature = skinIF.getText().toString().trim();
            viewModel.digestionStrength = digestionIF.getText().toString().trim();
            viewModel.sleepNature = sleepIF.getText().toString().trim();
            viewModel.energyPattern = energyIF.getText().toString().trim();

            ((AssessmentActivity) requireActivity()).goToNextPage();
        });
    }
}
