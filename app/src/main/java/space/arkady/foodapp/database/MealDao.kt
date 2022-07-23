package space.arkady.foodapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import space.arkady.foodapp.models.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal:Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @Query("SELECT * FROM mealInfo")
    fun getAllMeals(): LiveData<List<Meal>>
}