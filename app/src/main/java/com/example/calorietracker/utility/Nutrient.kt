package com.example.calorietracker.utility

import com.example.calorietracker.data.Food
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.data.Nutrient

const val CALORIE_ID = "208"
const val PROTEIN_ID = "203"

/** Returns calorie [String] value **/
fun getCalories(food: Food): String {
    for (nutrient: Nutrient in food.full_nutrients) {
        if (nutrient.attr_id == CALORIE_ID) {
            return nutrient.value.toFloat().toInt().toString()    // round down
        }
    }
    return "0"
}

fun getProtein(food: Food): String {
    for (nutrient: Nutrient in food.full_nutrients) {
        if (nutrient.attr_id == PROTEIN_ID) {
            return nutrient.value.toFloat().toInt().toString()    // round down
        }
    }
    return "0"
}

fun convertToFoodItem(food: Food, category: String): FoodItem {
    return FoodItem(
        food_name = food.food_name,
        serving_qty = food.serving_qty,
        serving_unit = food.serving_unit,
        calories = getCalories(food),
        photo = food.photo.thumb,
        category = category,
        protein = getProtein(food)
    )
}