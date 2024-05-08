package dev.deepslate.carbohydrate.diet

import dev.deepslate.carbohydrate.Attachments
import dev.deepslate.carbohydrate.Capabilities
import dev.deepslate.carbohydrate.Effects.FULL
import dev.deepslate.carbohydrate.nutrition.INutrition
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import java.util.*

class BaseDiet(private val player: Player) : IDiet {

    companion object {
        const val QUEUE_MAX_SIZE = 10

//        const val CARBOHYDRATE_TAG = "carbohydrate"
//        const val GREASE_TAG = "grease"
//        const val PROTEIN_TAG = "protein"
//        const val PLANT_FIBER_TAG = "plant_fiber"
//        const val ELECTRONICS_TAG = "electronics"
//        const val EATEN_QUEUE_TAG = "eaten_queue"
    }

    override fun getCarbohydrate(): Float = player.getData(Attachments.CARBOHYDRATE)

    override fun getGrease(): Float = player.getData(Attachments.GREASE)

    override fun getProtein(): Float = player.getData(Attachments.PROTEIN)

    override fun getPlantFiber(): Float = player.getData(Attachments.PLANT_FIBER)

    override fun getElectrolyte(): Float = player.getData(Attachments.ELECTROLYTE)

    override fun setCarbohydrate(carbohydrate: Float) {
        player.setData(Attachments.CARBOHYDRATE, carbohydrate)
    }

    override fun setGrease(grease: Float) {
        player.setData(Attachments.GREASE, grease)
    }

    override fun setProtein(protein: Float) {
        player.setData(Attachments.PROTEIN, protein)
    }

    override fun setPlantFiber(plantFiber: Float) {
        player.setData(Attachments.PLANT_FIBER, plantFiber)
    }

    override fun setElectrolyte(electrolyte: Float) {
        player.setData(Attachments.ELECTROLYTE, electrolyte)
    }

    override fun getEatenQueue(): Queue<Item> = player.getData(Attachments.EATEN_QUEUE).queue()

    override fun eatOver(stack: ItemStack) {
        updateQueue(stack.item)
        val rate = getFoodEatenRate(stack.item)
        val nutrition = updateNutrition(stack, rate)
        eatFood(stack, rate)

        if(nutrition == null) return
        val effectTicks = when (nutrition.getFoodLevel()) {
            1 -> 30
            2 -> 60
            3 -> 120
            else -> 10
        } * 20

        player.addEffect(MobEffectInstance(FULL.get(), effectTicks), null)
    }

    private fun eatFood(stack: ItemStack, rate: Float) {
        val foodData = player.foodData
        val prop = stack.getFoodProperties(player) ?: return
        foodData.eat((prop.nutrition * rate).toInt(), prop.saturationModifier * rate)
    }

    private fun updateNutrition(stack: ItemStack, rate: Float): INutrition? {
        val data = stack.getCapability(Capabilities.NUTRITION) ?: return null
        setCarbohydrate(getCarbohydrate() + data.getCarbohydrate() * rate)
        setGrease(getGrease() + data.getGrease() * rate)
        setProtein(getProtein() + data.getProtein() * rate)
        setPlantFiber(getPlantFiber() + data.getPlantFiber() * rate)
        setElectrolyte(getElectrolyte() + data.getElectrolyte() * rate)
        return data
    }

    private fun updateQueue(item: Item) {
        val wrapper = player.getData(Attachments.EATEN_QUEUE)
        val queue = wrapper.queue()
        queue.offer(item)
        if (queue.size > QUEUE_MAX_SIZE) queue.poll()
        player.setData(Attachments.EATEN_QUEUE, wrapper)
    }

    override fun getFoodEatenTimes(item: Item) = getEatenQueue().count { it == item }

    override fun getFoodEatingExtraTicks(item: Item): Int {
        val count = getFoodEatenTimes(item)
        return when (count) {
            in 0..1 -> 0
            2 -> 10
            3 -> 20
            4 -> 30
            5 -> 40
            else -> 80
        }
    }

    override fun getFoodEatenRate(item: Item): Float {
        val count = getFoodEatenTimes(item)
        return when (count) {
            in 0..1 -> 1f
            2 -> 0.8f
            3 -> 0.5f
            4 -> 0.2f
            else -> 1f
        }
    }

    override fun updateEatenQueue(eaten: EatenQueue) {
        player.setData(Attachments.EATEN_QUEUE, eaten)
    }

    //    override fun serializeNBT(): CompoundTag {
//        val tag = CompoundTag()
//        tag.putFloat(CARBOHYDRATE_TAG, carbohydrate)
//        tag.putFloat(GREASE_TAG, grease)
//        tag.putFloat(PROTEIN_TAG, protein)
//        tag.putFloat(PLANT_FIBER_TAG, plantFiber)
//        tag.putFloat(ELECTRONICS_TAG, electrolyte)
//
//        val nameList = foodQueue.map { BuiltInRegistries.ITEM.getKey(it).toString() }
//        val subTag = CompoundTag()
//        nameList.forEachIndexed {
//            index, name -> subTag.putString(index.toString(), name)
//        }
//        tag.put(EATEN_QUEUE_TAG, subTag)
//
//        return tag
//    }
//
//    override fun deserializeNBT(nbt: CompoundTag) {
//        carbohydrate = nbt.getFloat(CARBOHYDRATE_TAG)
//        grease = nbt.getFloat(GREASE_TAG)
//        protein = nbt.getFloat(PROTEIN_TAG)
//        plantFiber = nbt.getFloat(PLANT_FIBER_TAG)
//        electrolyte = nbt.getFloat(ELECTRONICS_TAG)
//
//        val subTag = nbt.get(EATEN_QUEUE_TAG) as CompoundTag
//        val list = LinkedList<Item>()
//        for ( idx in 0..9) {
//            val ids = idx.toString()
//            if(!subTag.contains(ids)) continue
//            val name = subTag.getString(ids)
//            val rl = ResourceLocation(name)
//            if(!BuiltInRegistries.ITEM.containsKey(rl)) return
//            list.add(BuiltInRegistries.ITEM.get(rl))
//        }
//        foodQueue = list
//    }
}