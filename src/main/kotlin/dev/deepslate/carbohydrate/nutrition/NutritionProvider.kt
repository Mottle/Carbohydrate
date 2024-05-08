package dev.deepslate.carbohydrate.nutrition

import dev.deepslate.carbohydrate.Attachments
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.neoforged.neoforge.capabilities.ICapabilityProvider

class NutritionProvider : ICapabilityProvider<ItemStack, Void, INutrition> {
    override fun getCapability(stack: ItemStack, context: Void?): INutrition? {

        if (check(stack)) {
            return VarNutrition(stack)
        }

        val item = stack.item
        if (baseNutritionMap.containsKey(item)) {
            return baseNutritionMap[item]
        }

        return null
    }

    private fun check(stack: ItemStack): Boolean = with(stack) {
        hasData(Attachments.CARBOHYDRATE) || hasData(Attachments.GREASE) || hasData(Attachments.PROTEIN) || hasData(
            Attachments.PLANT_FIBER
        ) || hasData(Attachments.ELECTROLYTE)
    }

    companion object {

        fun add(item: Item, nutrition: BaseNutrition) {
            baseNutritionMap[item] = nutrition
        }

        private val baseNutritionMap = mutableMapOf(
            Items.ENCHANTED_GOLDEN_APPLE to BaseNutrition(
                carbohydrate = 5f,
                grease = 5f,
                protein = 5f,
                plantFiber = 5f,
                electrolyte = 5f,
                foodLevel = 3
            ),
            Items.GOLDEN_APPLE to BaseNutrition(
                carbohydrate = 1f,
                grease = 1f,
                protein = 1f,
                plantFiber = 1f,
                electrolyte = 1f,
                foodLevel = 2
            ),
            Items.GOLDEN_CARROT to BaseNutrition(carbohydrate = 0.4f, plantFiber = 0.8f, foodLevel = 1),
            Items.COOKED_BEEF to BaseNutrition(grease = 0.4f, protein = 1.5f, foodLevel = 2),
            Items.COOKED_PORKCHOP to BaseNutrition(grease = 1f, protein = 0.8f, foodLevel = 2),
            Items.COOKED_MUTTON to BaseNutrition(grease = 0.8f, protein = 0.8f, foodLevel = 2),
            Items.COOKED_SALMON to BaseNutrition(grease = 0.1f, protein = 0.5f, foodLevel = 2),
            Items.BAKED_POTATO to BaseNutrition(carbohydrate = 1.5f, grease = 0.1f, foodLevel = 2),
            Items.BEETROOT to BaseNutrition(carbohydrate = 1.5f, plantFiber = 0.3f, foodLevel = 1),
            Items.BEETROOT_SOUP to BaseNutrition(carbohydrate = 1.5f, foodLevel = 2),
            Items.BREAD to BaseNutrition(carbohydrate = 0.8f, plantFiber = 0.1f, foodLevel = 1),
            Items.CARROT to BaseNutrition(carbohydrate = 0.4f, plantFiber = 0.8f, foodLevel = 2),
            Items.COOKED_CHICKEN to BaseNutrition(grease = 0.7f, protein = 0.8f, foodLevel = 2),
            Items.COOKED_COD to BaseNutrition(protein = 1f, foodLevel = 2),
            Items.COOKED_RABBIT to BaseNutrition(protein = 1f, foodLevel = 2),
            Items.RABBIT_STEW to BaseNutrition(protein = 0.8f, foodLevel = 2),
            Items.APPLE to BaseNutrition(carbohydrate = 0.6f, plantFiber = 0.8f, electrolyte = 0.1f, foodLevel = 1),
            Items.CHORUS_FRUIT to BaseNutrition(electrolyte = 0.6f, plantFiber = 1f, foodLevel = 0),
            Items.DRIED_KELP to BaseNutrition(carbohydrate = 0.4f, plantFiber = 0.4f, electrolyte = 1f, foodLevel = 1),
            Items.MELON_SLICE to BaseNutrition(carbohydrate = 0.8f, electrolyte = 0.8f, foodLevel = 1),
            Items.POTATO to BaseNutrition(carbohydrate = 1.2f, grease = 0.1f, foodLevel = 1),
            Items.PUMPKIN_PIE to BaseNutrition(carbohydrate = 0.6f, grease = 0.3f, foodLevel = 1),
            Items.BEEF to BaseNutrition(grease = 0.4f, protein = 1f, foodLevel = 1),
            Items.CHICKEN to BaseNutrition(grease = 0.5f, protein = 0.8f, foodLevel = 1),
            Items.MUTTON to BaseNutrition(grease = 0.8f, protein = 0.6f, foodLevel = 1),
            Items.PORKCHOP to BaseNutrition(grease = 0.8f, protein = 0.8f, foodLevel = 1),
            Items.RABBIT to BaseNutrition(protein = 0.8f, foodLevel = 1),
            Items.SWEET_BERRIES to BaseNutrition(carbohydrate = 0.2f, electrolyte = 0.2f, foodLevel = 0),
            Items.GLOW_BERRIES to BaseNutrition(carbohydrate = 0.1f, electrolyte = 0.5f, foodLevel = 0),
            Items.CAKE to BaseNutrition(carbohydrate = 1f, grease = 0.5f, foodLevel = 0),
            Items.HONEY_BOTTLE to BaseNutrition(carbohydrate = 2f, foodLevel = 1),
            Items.PUFFERFISH to BaseNutrition(protein = 2f, foodLevel = 1),
            Items.COD to BaseNutrition(protein = 1f, foodLevel = 2),
            Items.SALMON to BaseNutrition(grease = 0.1f, protein = 0.5f, foodLevel = 2),
            Items.TROPICAL_FISH to BaseNutrition(protein = 1f, foodLevel = 1),
        )
    }
}