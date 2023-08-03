package fireopal.randommotd.mixin;

import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import net.minecraft.server.ServerMetadata;

import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fireopal.randommotd.RandomMotd;

@Mixin(QueryResponseS2CPacket.class)
public class QueryResponseS2CPacketMixin {
	@Shadow @Mutable
	private ServerMetadata metadata;

	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/server/ServerMetadata;)V")
	private void _init__ServerMetaData(CallbackInfo ci) {
		// RandomMotd.log("QueryResponseS2CPacket created with ServerMetaData.");

		try {
			ServerMetadata newMetadata = new ServerMetadata(
				RandomMotd.getRandomMotd(), 
				metadata.players(), 
				metadata.version(), 
				RandomMotd.useRandomIcons() ? Optional.of(RandomMotd.getRandomIcon()) : metadata.favicon(), 
				metadata.secureChatEnforced()
			);

			metadata = newMetadata;
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// RandomMotd.log("MOTD: " + this.metadata.description().getString());
	}

	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V")
	private void _init__PacketByteBuf(CallbackInfo ci) {
		// RandomMotd.log("QueryResponseS2CPacket created with PacketByteBuf.");

		try {
			ServerMetadata newMetadata = new ServerMetadata(
				RandomMotd.getRandomMotd(), 
				metadata.players(), 
				metadata.version(), 
				RandomMotd.useRandomIcons() ? Optional.of(RandomMotd.getRandomIcon()) : metadata.favicon(), 
				metadata.secureChatEnforced()
			);

			metadata = newMetadata;
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// RandomMotd.log("MOTD: " + this.metadata.description().getString());
	}
}
