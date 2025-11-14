package in.ayush.swasthyapath.adapter.patient;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.ayush.swasthyapath.R;
import in.ayush.swasthyapath.model.Meal;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<Meal> mealList;

    public RecyclerViewAdapter(List<Meal> mealList) {
        this.mealList = mealList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_diet_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        Meal meal = mealList.get(i);

        viewHolder.mealType.setText(meal.getMeal());
        viewHolder.mealItems.setText(meal.getItems());
        viewHolder.mealCalorie.setText(String.valueOf(meal.getCalories()));
        viewHolder.mealRasa.setText(meal.getRasa());
        viewHolder.mealProperty.setText(meal.getProperty());
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Meal> meals) {
        this.mealList = meals;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mealType, mealItems, mealCalorie, mealRasa, mealProperty;

        public ViewHolder(@NotNull View view) {
            super(view);

            mealType = view.findViewById(R.id.tvMeal);
            mealItems = view.findViewById(R.id.tvItems);
            mealCalorie = view.findViewById(R.id.tvCalories);
            mealRasa = view.findViewById(R.id.tvRasa);
            mealProperty = view.findViewById(R.id.tvProperty);
        }
    }
}
