package kaeso.vanillasneak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod(
    modid = kaeso.vanillasneak.SneakMod.MODID,
    version = kaeso.vanillasneak.SneakMod.VERSION,
    name = kaeso.vanillasneak.SneakMod.NAME,
    acceptedMinecraftVersions = "[1.8.9]",
    clientSideOnly = true
)
public class SneakMod
{
    public static final String NAME = "VanillaSneak";
    public static final String MODID = "vanillasneak";
    public static final String VERSION = "1.0";

    public static KeyBinding toggleSneakKey = new KeyBinding("Toggle Sneak", Keyboard.KEY_NONE, "Vanilla Sneak");
    public static KeyBinding toggleSprintKey = new KeyBinding("Toggle Sprint", Keyboard.KEY_NONE, "Vanilla Sneak");

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new SneakMod());
        ClientRegistry.registerKeyBinding(toggleSneakKey);
        ClientRegistry.registerKeyBinding(toggleSprintKey);
    }

    @SubscribeEvent
    public void trackKeyInputs(InputEvent.KeyInputEvent event) {
        if (toggleSneakKey.isPressed()) {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            KeyBinding keyBindSneak = gameSettings.keyBindSneak;

            if (keyBindSneak.isKeyDown()) {
                playSadDing();
            } else {
                playDing();
            }

            KeyBinding.setKeyBindState(keyBindSneak.getKeyCode(), !keyBindSneak.isKeyDown());
        } else if (toggleSprintKey.isPressed()) {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;

            KeyBinding keyBindSprint = gameSettings.keyBindSprint;

            if (keyBindSprint.isKeyDown()) {
                playSadDing();
            } else {
                playDing();
            }

            KeyBinding.setKeyBindState(keyBindSprint.getKeyCode(), !keyBindSprint.isKeyDown());
        }
    }

    private static void playDing() {
        ISound sound = new PositionedSound(new ResourceLocation("random.orb")) {{
            volume = 8f;
            pitch = 1.0f;
            repeat = false;
            repeatDelay = 0;
            attenuationType = ISound.AttenuationType.NONE;
        }};

        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        float oldLevel = gameSettings.getSoundLevel(SoundCategory.PLAYERS);
        gameSettings.setSoundLevel(SoundCategory.PLAYERS, 0.5f);
        Minecraft.getMinecraft().getSoundHandler().playSound(sound);
        gameSettings.setSoundLevel(SoundCategory.PLAYERS, oldLevel);
    }

    private static void playSadDing() {
        ISound sound = new PositionedSound(new ResourceLocation("random.orb")) {{
            volume = 8f;
            pitch = 0.0f;
            repeat = false;
            repeatDelay = 0;
            attenuationType = ISound.AttenuationType.NONE;
        }};

        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        float oldLevel = gameSettings.getSoundLevel(SoundCategory.PLAYERS);
        gameSettings.setSoundLevel(SoundCategory.PLAYERS, 0.5f);
        Minecraft.getMinecraft().getSoundHandler().playSound(sound);
        gameSettings.setSoundLevel(SoundCategory.PLAYERS, oldLevel);
    }
}