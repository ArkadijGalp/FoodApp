package space.arkady.foodapp.data

import retrofit2.Call
import retrofit2.http.GET
import space.arkady.foodapp.models.MealList

interface MealAPI {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
}