package fireopal.randommotd.mixin;

import java.util.Collection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fireopal.randommotd.RandomMotd;
import net.minecraft.server.command.ReloadCommand;
import net.minecraft.server.command.ServerCommandSource;

@Mixin(ReloadCommand.class)
public class ReloadCommandMixin {
    @Inject(method = "tryReloadDataPacks", at = @At("RETURN"), cancellable = true)
    private static void tryReloadDataPacks(Collection<String> dataPacks, ServerCommandSource source, CallbackInfo info) {
        try {
            RandomMotd.loadConfigFromFile();
        } catch (Exception e) {
            RandomMotd.log("Failed to reload config");
        }
    }
}
