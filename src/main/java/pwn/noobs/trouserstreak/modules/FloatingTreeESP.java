package pwn.noobs.trouserstreak.modules;

import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class FloatingTreeESP extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<SettingColor> color = sgGeneral.add(new ColorSetting.Builder()
        .name("highlight-color")
        .description("Color of the ESP box.")
        .defaultValue(new SettingColor(34, 139, 34, 150))
        .build()
    );

    public FloatingTreeESP() {
        super(Trouser.baseHunting, "FloatingTreeESP", "Highlights logs that have air directly below them.");
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (mc.world == null) return;

        for (BlockPos pos : BlockPos.iterate(
                mc.player.getBlockPos().add(-32, -32, -32), 
                mc.player.getBlockPos().add(32, 32, 32))) {

            if (mc.world.getBlockState(pos).getBlock() == Blocks.OAK_LOG 
             || mc.world.getBlockState(pos).getBlock() == Blocks.BIRCH_LOG
             || mc.world.getBlockState(pos).getBlock() == Blocks.SPRUCE_LOG
             || mc.world.getBlockState(pos).getBlock() == Blocks.JUNGLE_LOG
             || mc.world.getBlockState(pos).getBlock() == Blocks.ACACIA_LOG
             || mc.world.getBlockState(pos).getBlock() == Blocks.DARK_OAK_LOG) {

                BlockPos below = pos.down();
                if (mc.world.isAir(below)) {
                    RenderUtils.drawBox(event.matrixStack, pos, color.get(), 2.0f);
                }
            }
        }
    }
}
