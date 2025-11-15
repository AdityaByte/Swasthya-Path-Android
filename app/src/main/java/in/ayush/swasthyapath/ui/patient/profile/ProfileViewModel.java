package in.ayush.swasthyapath.ui.patient.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import in.ayush.swasthyapath.model.Patient;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<Patient> patient = new MutableLiveData<>();

    public LiveData<Patient> getPatientData() {
        return patient;
    }

    public void setPatientData(Patient p) {
        patient.setValue(p);
    }
}