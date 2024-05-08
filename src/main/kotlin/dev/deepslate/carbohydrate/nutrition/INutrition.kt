package dev.deepslate.carbohydrate.nutrition

interface INutrition {
    fun getCarbohydrate(): Float
    fun getGrease(): Float
    fun getProtein(): Float
    fun getPlantFiber(): Float
    fun getElectrolyte(): Float
    fun getFoodLevel(): Int // 0 1 2 3
}