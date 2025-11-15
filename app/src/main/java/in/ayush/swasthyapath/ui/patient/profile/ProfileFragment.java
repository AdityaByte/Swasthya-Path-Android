package in.ayush.swasthyapath.ui.patient.profile;

import android.annotation.SuppressLint;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import in.ayush.swasthyapath.R;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextView nameTV, emailTV, phoneNoTV, dobTV, genderTV, physicalStatTV, prakrutiTV, vikrutiTV, consultStatusTV;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameTV = view.findViewById(R.id.txtName);
        emailTV = view.findViewById(R.id.txtEmail);
        phoneNoTV = view.findViewById(R.id.txtPhone);
        dobTV = view.findViewById(R.id.txtDob);
        genderTV = view.findViewById(R.id.txtGender);
        physicalStatTV = view.findViewById(R.id.txtHeightWeight);
        prakrutiTV = view.findViewById(R.id.txtPrakruti);
        vikrutiTV = view.findViewById(R.id.txtVikruti);
        consultStatusTV = view.findViewById(R.id.txtConsultationStatus);
        return view;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        profileViewModel.getPatientData().observe(getViewLifecycleOwner(), patient -> {
            if (patient != null) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                nameTV.setText(patient.getName());
                emailTV.setText(patient.getEmail());
                phoneNoTV.setText(patient.getPhoneNumber());
                dobTV.setText(sdf.format(new Date(patient.getDob())));
                genderTV.setText(patient.getGender());
                physicalStatTV.setText(String.format("Height: %.1f cm   •   Weight: %.1f kg", patient.getHeight(), patient.getWeight()));
                prakrutiTV.setText(patient.getPrakruti() + " Dosha");
                vikrutiTV.setText(patient.getVikruti() + " Dosha");
                consultStatusTV.setText(patient.getConsultedStatus());
            }
        });
    }
}