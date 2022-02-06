package com.example.calorietracker.utility

import com.example.calorietracker.data.Food
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.data.Nutrient

const val CALORIE_ID = "208"

/** Returns calorie [String] value **/
fun getCalories(food: Food): String {
    for (nutrient: Nutrient in food.full_nutrients) {
        if (nutrient.attr_id == CALORIE_ID) {
            return nutrient.value.toFloat().toInt().toString()    // round down
        }
    }
    return "0"
}

fun getUpdatedFoodEntry(id: Int, name: String, qty: String, unit: String, cal: String, photo: String, category: String, consumed: Boolean): FoodItem {
    return FoodItem(
        id,
        name,
        qty,
        unit,
        cal,
        photo,
        category,
        consumed)
}