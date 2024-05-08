package dev.deepslate.carbohydrate

import com.mojang.serialization.Codec
import dev.deepslate.carbohydrate.diet.EatenQueue
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion.MOD_ID
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries


object Attachments {
    val ATTACHMENT_TYPES: DeferredRegister<AttachmentType<*>> =
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID)

    val CARBOHYDRATE: DeferredHolder<AttachmentType<*>, AttachmentType<Float>> =
        ATTACHMENT_TYPES.register("carbohydrate") { _ ->
            AttachmentType.builder { _ -> 100f }.serialize(Codec.FLOAT).copyOnDeath().build()
        }

    val GREASE: DeferredHolder<AttachmentType<*>, AttachmentType<Float>> = ATTACHMENT_TYPES.register("grease") { _ ->
        AttachmentType.builder { _ -> 100f }.serialize(Codec.FLOAT).copyOnDeath().build()
    }

    val PROTEIN: DeferredHolder<AttachmentType<*>, AttachmentType<Float>> = ATTACHMENT_TYPES.register("protein") { _ ->
        AttachmentType.builder { _ -> 100f }.serialize(Codec.FLOAT).copyOnDeath().build()
    }

    val PLANT_FIBER: DeferredHolder<AttachmentType<*>, AttachmentType<Float>> =
        ATTACHMENT_TYPES.register("plant_fiber") { _ ->
            AttachmentType.builder { _ -> 100f }.serialize(Codec.FLOAT).copyOnDeath().build()
        }

    val ELECTROLYTE: DeferredHolder<AttachmentType<*>, AttachmentType<Float>> =
        ATTACHMENT_TYPES.register("electrolyte") { _ ->
            AttachmentType.builder { _ -> 100f }.serialize(Codec.FLOAT).copyOnDeath().build()
        }

    val EATEN_QUEUE: DeferredHolder<AttachmentType<*>, AttachmentType<EatenQueue>> =
        ATTACHMENT_TYPES.register("eaten_queue") { _ -> AttachmentType.serializable { _ -> EatenQueue() }.copyOnDeath().build() }

    val FOOD_LEVEL: DeferredHolder<AttachmentType<*>, AttachmentType<Int>> =
        ATTACHMENT_TYPES.register("food_level") { _ -> AttachmentType.builder { _ -> 0 }.serialize(Codec.INT).build() }

//    val NUTRITION_DATA: DeferredHolder<AttachmentType<*>, AttachmentType<NutritionData>> =
//        ATTACHMENT_TYPES.register("nutrition_data") { _ ->
//            AttachmentType.serializable { _ -> NutritionData() }.build()
//        }
}