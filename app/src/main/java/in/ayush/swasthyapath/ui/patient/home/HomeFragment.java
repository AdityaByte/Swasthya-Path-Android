package in.ayush.swasthyapath.ui.patient.home;

import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.ayush.swasthyapath.R;
import in.ayush.swasthyapath.adapter.patient.RecyclerViewAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar loadingSpinner;
    private TextView emptyStateTextView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        loadingSpinner = view.findViewById(R.id.loading_spinner);
        emptyStateTextView = view.findViewById(R.id.empty_state_text);

        recyclerView = view.findViewById(R.id.home_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Setting visibility.
        recyclerView.setVisibility(View.GONE);
        emptyStateTextView.setVisibility(View.GONE);
        loadingSpinner.setVisibility(View.VISIBLE);

        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class); // Activity scoped.
        homeViewModel.getMeals().observe(getViewLifecycleOwner(), meals -> {
            if (meals == null || meals.isEmpty()) {
                emptyStateTextView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else  {
                recyclerViewAdapter.updateList(meals);
                recyclerView.setVisibility(View.VISIBLE);
                emptyStateTextView.setVisibility(View.GONE);
            }
        });

        homeViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                // If loading, show spinner and hide everything else
                loadingSpinner.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                emptyStateTextView.setVisibility(View.GONE);
            } else {
                // Loading finished (success or failure), hide the spinner
                loadingSpinner.setVisibility(View.GONE);
                // The getMeals observer handles which view becomes visible next (RecyclerView or Empty TextView)
            }
        });
    }
}