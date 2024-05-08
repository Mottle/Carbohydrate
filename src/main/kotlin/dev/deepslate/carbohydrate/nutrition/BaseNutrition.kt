package dev.deepslate.carbohydrate.nutrition

data class BaseNutrition(
    private val carbohydrate: Float = 0f,
    private val grease: Float = 0f,
    private val protein: Float = 0f,
    private val plantFiber: Float = 0f,
    private val electrolyte: Float = 0f,
    private val foodLevel: Int = 0,
) : INutrition {
    override fun getCarbohydrate(): Float = carbohydrate

    override fun getGrease(): Float = grease

    override fun getProtein(): Float = protein

    override fun getPlantFiber(): Float = plantFiber

    override fun getElectrolyte(): Float = electrolyte

    override fun getFoodLevel(): Int = foodLevel
}