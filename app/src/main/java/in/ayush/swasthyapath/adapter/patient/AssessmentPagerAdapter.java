package in.ayush.swasthyapath.adapter.patient;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import in.ayush.swasthyapath.ui.patient.assessment.BasicAssessmentFragment;
import in.ayush.swasthyapath.ui.patient.assessment.HealthAssessmentFragment;
import in.ayush.swasthyapath.ui.patient.assessment.PrakrutiAssessmentFragment;
import in.ayush.swasthyapath.ui.patient.assessment.VikrutiAssessmentFragment;
import lombok.NonNull;

public class AssessmentPagerAdapter extends FragmentStateAdapter {

    public AssessmentPagerAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new BasicAssessmentFragment();
            case 1: return new HealthAssessmentFragment();
            case 2: return new PrakrutiAssessmentFragment();
            case 3: return new VikrutiAssessmentFragment();
            default: return new BasicAssessmentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
