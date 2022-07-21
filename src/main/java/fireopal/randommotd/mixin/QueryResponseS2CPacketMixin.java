package fireopal.randommotd.mixin;

import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import net.minecraft.server.ServerMetadata;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fireopal.randommotd.RandomMotd;

@Mixin(QueryResponseS2CPacket.class)
public class QueryResponseS2CPacketMixin {
	@Shadow @Final
	private ServerMetadata metadata;

	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/server/ServerMetadata;)V")
	private void _init__ServerMetaData(CallbackInfo ci) {
		// RandomMotd.log("QueryResponseS2CPacket created with ServerMetaData.");

		this.metadata.setDescription(RandomMotd.getRandomMotd());
		
		if (RandomMotd.useRandomIcons()) {
			this.metadata.setFavicon(RandomMotd.getRandomIcon());
		}
	}

	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V")
	private void _init__PacketByteBuf(CallbackInfo ci) {
		// RandomMotd.log("QueryResponseS2CPacket created with PacketByteBuf.");

		this.metadata.setDescription(RandomMotd.getRandomMotd());

		if (RandomMotd.useRandomIcons()) {
			this.metadata.setFavicon(RandomMotd.getRandomIcon());
		}
	}
}
