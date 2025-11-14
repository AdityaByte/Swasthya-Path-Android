package in.ayush.swasthyapath.ui.patient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import in.ayush.swasthyapath.model.Meal;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Meal>> meals = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);

    public LiveData<List<Meal>> getMeals() {
        return meals;
    }

    // 2. The required getter method
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setMeals(List<Meal> mealList) {
        meals.setValue(mealList);
        isLoading.setValue(false);
    }

    public void setNetworkError() {
        isLoading.setValue(false);
        meals.setValue(null);
    }
}