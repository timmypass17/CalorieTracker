package com.example.calorietracker.utility

import com.example.calorietracker.data.Food
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
