package fireopal.randommotd.util;

import java.util.List;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;

import net.minecraft.server.ServerMetadata;

public class ParsePlaceholders {
    public static final List<Pair<String, Function<ServerMetadata, String>>> PLACEHOLDERS = ImmutableList.of(
        Pair.of("%PLAYER_COUNT%", meta -> ((Integer)meta.getPlayers().getOnlinePlayerCount()).toString()),
        Pair.of("%SERVER_GAME_VERSION%", meta -> meta.getVersion().getGameVersion()),
        Pair.of("%RANDOM_PLAYER%", meta -> {
            GameProfile[] profiles = meta.getPlayers().getSample();
            return profiles[(int) (Math.random() * profiles.length)].getName();
        })
    );

    public static String parsePlaceholders(String str, ServerMetadata meta) {
        for (var p : PLACEHOLDERS) {
            str = str.replaceAll(p.getFirst(), p.getSecond().apply(meta));
        }

        return str;
    }
}
