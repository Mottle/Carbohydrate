package dev.deepslate.carbohydrate.diet

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.neoforged.neoforge.common.util.INBTSerializable
import java.util.*

class EatenQueue: INBTSerializable<CompoundTag> {
    private var foodQueue: Queue<Item> = LinkedList()

    fun queue() = foodQueue

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        val nameList = foodQueue.map { BuiltInRegistries.ITEM.getKey(it).toString() }
        val subTag = CompoundTag()
        nameList.forEachIndexed {
                index, name -> subTag.putString(index.toString(), name)
        }
        tag.put("eaten_queue", subTag)
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        val subTag = nbt.get("eaten_queue") as CompoundTag
        val list = LinkedList<Item>()
        for ( idx in 0..9) {
            val ids = idx.toString()
            if(!subTag.contains(ids)) continue
            val name = subTag.getString(ids)
            val rl = ResourceLocation(name)
            if(!BuiltInRegistries.ITEM.containsKey(rl)) return
            list.add(BuiltInRegistries.ITEM.get(rl))
        }
        foodQueue = list
    }
}