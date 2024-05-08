package dev.deepslate.carbohydrate.nutrition

import dev.deepslate.carbohydrate.Attachments
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.DeferredHolder

class VarNutrition(private val stack: ItemStack) : INutrition {
    fun setCarbohydrate(carbohydrate: Float) {
        stack.setData(Attachments.CARBOHYDRATE, carbohydrate)
    }

    fun setGrease(grease: Float) {
        stack.setData(Attachments.GREASE, grease)
    }

    fun setProtein(protein: Float) {
        stack.setData(Attachments.PROTEIN, protein)
    }

    fun setPlantFiber(plantFiber: Float) {
        stack.setData(Attachments.PLANT_FIBER, plantFiber)
    }

    fun setElectrolyte(electrolyte: Float) {
        stack.setData(Attachments.ELECTROLYTE, electrolyte)
    }

    override fun getCarbohydrate(): Float = stack.tryGet(Attachments.CARBOHYDRATE, 0f)

    override fun getGrease(): Float = stack.tryGet(Attachments.GREASE, 0f)

    override fun getProtein(): Float = stack.tryGet(Attachments.PROTEIN, 0f)

    override fun getPlantFiber(): Float = stack.tryGet(Attachments.PLANT_FIBER, 0f)

    override fun getElectrolyte(): Float = stack.tryGet(Attachments.ELECTROLYTE, 0f)

    override fun getFoodLevel(): Int = stack.tryGet(Attachments.FOOD_LEVEL, 0)

    private fun <T> ItemStack.tryGet(
        dataType: DeferredHolder<AttachmentType<*>, AttachmentType<T>>,
        elseValue: T
    ): T = if (hasData(dataType)) getData(dataType) else elseValue

}