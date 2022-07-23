package space.arkady.foodapp.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import space.arkady.foodapp.models.CategoryList
import space.arkady.foodapp.models.MealsByCategoryList
import space.arkady.foodapp.models.MealList
import space.arkady.foodapp.models.MealsByCategory

interface MealAPI {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName: String): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealsByCategoryList>

}