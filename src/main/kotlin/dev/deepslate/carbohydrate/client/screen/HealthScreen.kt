package dev.deepslate.carbohydrate.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import dev.deepslate.carbohydrate.Capabilities
import dev.deepslate.carbohydrate.Carbohydrate
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import kotlin.math.floor


class HealthScreen(private val fromInv: Boolean) : Screen(TITLE) {
    companion object {
        private val TITLE: MutableComponent = Component.literal("健康")
        private const val TEXT_COLOR = 0x404040

        private val BACKGROUND: ResourceLocation = ResourceLocation("minecraft", "textures/gui/demo_background.png")
        private val ICONS = ResourceLocation(Carbohydrate.MOD_ID, "textures/gui/icons.png")
    }

    private val xSize = 248
    private val ySize = 5 * 20 + 60

    override fun init() {
        super.init()
        val btnWidth = 80
        addRenderableWidget(
            Button.builder(Component.literal("确定"), ::handleBtn).size(btnWidth, 20)
                .pos(width / 2 - btnWidth / 2, (height + ySize) / 2 - 30).build()
        )
    }

    private fun handleBtn(button: Button) {
        val mc = minecraft ?: return
        val player = mc.player ?: return
        if (fromInv) mc.setScreen(InventoryScreen(player))
        else onClose()
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
//        renderBackground(guiGraphics, mouseX, mouseY, partialTick)
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        renderTitle(guiGraphics, mouseX, mouseY)
        renderForeground(guiGraphics, mouseX, mouseY)
    }

    private fun renderTitle(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        val titleWidth = font.width(title.string)
        guiGraphics.drawString(
            this.font, this.title, this.width / 2 - titleWidth / 2,
            this.height / 2 - this.ySize / 2 + 10, TEXT_COLOR, false
        )
//        val modifiers: List<AttributeModifier> = tooltip.getModifiers()
//        val effects: List<DietEffectsInfo.StatusEffect> = tooltip.getEffects()

//        val mc = minecraft ?: return
        val lowerX = this.width / 2 + (titleWidth / 2) + 5
        val lowerY = this.height / 2 - this.ySize / 2 + 7
//        val upperX = lowerX + 16
//        val upperY = lowerY + 16
        guiGraphics.blit(ICONS, lowerX, lowerY, 16, 16, 0f, 37f, 16, 16, 256, 256)

//            if (mouseX >= lowerX && mouseX <= upperX && mouseY >= lowerY && mouseY <= upperY) {
//                val tooltips: List<Component> = DietTooltip.getEffects()
//                guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY)
//            }
    }

    private fun renderForeground(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        val mc = minecraft ?: return
//        val level = mc.level ?: return
        val player = minecraft!!.player ?: return

        val diet = player.getCapability(Capabilities.DIET)!!
        val carbohydrate = diet.getCarbohydrate()
        val protein = diet.getProtein()
        val grease = diet.getGrease()
        val plantFiber = diet.getPlantFiber()
        val electrolyte = diet.getElectrolyte()

        var y = this.height / 2 - this.ySize / 2 + 25
        val x = this.width / 2 - this.xSize / 2 + 10
//        var tooltip: Component? = null
        val list = listOf(
            Triple(carbohydrate, Items.SUGAR, 0xff4500) to "碳水",
            Triple(protein, Items.COOKED_BEEF, 0xffa500) to "蛋白质",
            Triple(grease, Items.COCOA_BEANS, 0x8b7e66) to "油脂",
            Triple(plantFiber, Items.WHEAT, 0x32cd32) to "植物纤维",
            Triple(electrolyte, Items.WATER_BUCKET, 0x00ced1) to "电解质"
        )

        for ((group, name) in list) {
            val (percent, item, color) = group
            guiGraphics.renderItem(ItemStack(item), x, y - 5)
            val text = Component.literal(name)
            guiGraphics.drawString(this.font, text, x + 20, y, TEXT_COLOR, false)
            RenderSystem.setShader { GameRenderer.getPositionColorTexShader() }
            RenderSystem.setShaderTexture(0, ICONS)
            val red: Int = color / 0x10000
            val green: Int = (color / 0x100) % 0x100
            val blue: Int = color % 0x100
            val percentInt = floor(percent).toInt()
            val percentText = "$percentInt%"

            drawProgressBar(
                guiGraphics.pose(), x + 90, y + 2, 102, 5, 20f, 0f, 102, 5, 256, 256, red,
                green, blue, 255
            )

            if (percentInt > 0f) {
                val texWidth = percentInt + 1
                drawProgressBar(
                    guiGraphics.pose(), x + 90, y + 2, texWidth, 5, 20f, 5f, texWidth, 5,
                    256, 256, red, green, blue, 255
                )
            }
            val xPos = x + 200
            val yPos = y + 1
            guiGraphics.drawString(this.font, percentText, (xPos + 1), yPos, 0, false)
            guiGraphics.drawString(this.font, percentText, (xPos - 1), yPos, 0, false)
            guiGraphics.drawString(this.font, percentText, xPos, (yPos + 1), 0, false)
            guiGraphics.drawString(this.font, percentText, xPos, (yPos - 1), 0, false)
            guiGraphics.drawString(this.font, percentText, xPos, yPos, color, false)
//            val lowerY = y - 5
//            val upperX = x + 16
//            val upperY = lowerY + 16

//            if (mouseX in x..upperX && mouseY >= lowerY && mouseY <= upperY) {
//                val key =
//                    (("groups." + Carbohydrate.MOD_ID).toString() + "." + group.getName()).toString() + ".tooltip"
//
////                if (Language.getInstance().has(key)) {
////                    tooltip = Component.translatable(key)
////                }
//            }
            y += 20
        }
//        if (tooltip != null) {
//            val tooltips: List<Component> =
//                Lists.newArrayList(tooltip)
//            guiGraphics.renderComponentTooltip(this.font, tooltips, mouseX, mouseY)
//        }
    }

    private fun drawProgressBar(
        matrixStack: PoseStack, x: Int, y: Int, width: Int, height: Int,
        uOffset: Float, vOffset: Float, uWidth: Int, vHeight: Int,
        textureWidth: Int, textureHeight: Int, red: Int, green: Int, blue: Int,
        alpha: Int
    ) {
        val x2 = x + width
        val y2 = y + height
        val minU = (uOffset + 0.0f) / textureWidth.toFloat()
        val maxU = (uOffset + uWidth.toFloat()) / textureWidth.toFloat()
        val minV = (vOffset + 0.0f) / textureHeight.toFloat()
        val maxV = (vOffset + vHeight.toFloat()) / textureHeight.toFloat()
        val matrix = matrixStack.last().pose()
        val bufferBuilder = Tesselator.getInstance().builder
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX)
        bufferBuilder.vertex(matrix, x.toFloat(), y2.toFloat(), 0f).color(red, green, blue, alpha)
            .uv(minU, maxV).endVertex()
        bufferBuilder.vertex(matrix, x2.toFloat(), y2.toFloat(), 0f).color(red, green, blue, alpha)
            .uv(maxU, maxV).endVertex()
        bufferBuilder.vertex(matrix, x2.toFloat(), y.toFloat(), 0f).color(red, green, blue, alpha)
            .uv(maxU, minV).endVertex()
        bufferBuilder.vertex(matrix, x.toFloat(), y.toFloat(), 0f).color(red, green, blue, alpha)
            .uv(minU, minV).endVertex()
        BufferUploader.drawWithShader(bufferBuilder.end())
    }

    override fun renderBackground(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick)
        if (this.minecraft != null) {
            val i = (this.width - this.xSize) / 2
            val j = (this.height - this.ySize) / 2
            guiGraphics.blit(BACKGROUND, i, j, this.xSize, 4, 0f, 0f, 248, 4, 256, 256)
            guiGraphics.blit(BACKGROUND, i, j + 4, this.xSize, this.ySize - 8, 0f, 4f, 248, 24, 256, 256)
            guiGraphics.blit(BACKGROUND, i, j + this.ySize - 4, this.xSize, 4, 0f, 162f, 248, 4, 256, 256)
        }
    }
}