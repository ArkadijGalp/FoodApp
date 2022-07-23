package space.arkady.foodapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.arkady.foodapp.data.RetrofitInstance
import space.arkady.foodapp.models.MealsByCategory
import space.arkady.foodapp.models.MealsByCategoryList

class CategoryMealsViewModel: ViewModel() {

    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let { mealslist ->
                    mealsLiveData.postValue(mealslist.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("CategoryMealsViewModel", t.message.toString())
            }
        })
    }
    fun observeMealsLiveData(): LiveData<List<MealsByCategory>> {
        return mealsLiveData
    }
}