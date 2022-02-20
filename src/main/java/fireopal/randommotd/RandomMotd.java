package fireopal.randommotd;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.ServerMetadata;
import net.minecraft.server.dedicated.ServerPropertiesHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.Weight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fireopal.randommotd.complex.ComplexMotd;
import fireopal.randommotd.util.WeightedMotd;

public class RandomMotd implements ModInitializer {
	public static final String MODID = "random_motd";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final FOModVersion VERSION = FOModVersion.fromString("0.0.0");

	private static Config CONFIG;
	private static Random random = new Random();
	
	private static int arrLength;
	private static Text[] motds;
	private static Double[] chances;
	private static Text fallbackMotd;

	public static Config getConfig() {
		return CONFIG;
	}

	public static Text getRandomMotd() {
		return getRandomMotd(random);
	}

	public static Text getRandomMotd(Random random) {
		// for (int j = 0; j < CONFIG.loops; j += 1) {
		// 	for (int i = 0; i < arrLength; i += 1) {
		// 		if (random.nextDouble() > chances[i]) {
		// 			log(motds[i]);
		// 			return motds[i];
		// 		}
		// 	}
		// }

		// return fallbackMotd;

		return motds[random.nextInt(0, motds.length)];
	}

	public static void log(String output) {
        LOGGER.info(output);
    }
    
	@Override
    public void onInitialize() {
        loadConfigFromFile();
    }

    public static void loadConfigFromFile() {
        CONFIG = Config.init();
        if (CONFIG.log_when_loaded) log("Loaded config for " + MODID);

		cacheMotds();
    }

	private static void cacheMotds() {
		int totalWeight = 0;

		List<Text> motdsTemp = new ArrayList<>();
		List<Integer> chancesTemp0 = new ArrayList<>();
		List<Double> chancesTemp1 = new ArrayList<>();
		double highestChance = 0d;
		int highestChanceIndex = 0;
		int length = 0;

		for (WeightedMotd<ComplexMotd> w : CONFIG.complex_motds) {
			motdsTemp.add(w.getMotd().asText());
			chancesTemp0.add(w.getWeight());
			totalWeight += w.getWeight();
			length += 1;
		}

		for (WeightedMotd<String> w : CONFIG.simple_motds) {
			motdsTemp.add(new LiteralText(w.getMotd()));
			chancesTemp0.add(w.getWeight());
			totalWeight += w.getWeight();
			length += 1;
		}

		for (int i = 0; i < length; i += 1) {
			double chance = (double) chancesTemp0.get(i) / (double) totalWeight;
			chancesTemp1.add(chance);

			if (chance > highestChance) {
				highestChance = chance;
				highestChanceIndex = i;
			}
		}

		motds = motdsTemp.toArray(new Text[0]);
		chances = chancesTemp1.toArray(new Double[0]);
		arrLength = motds.length;
		fallbackMotd = motds[highestChanceIndex];


		if (motds.length == 0) {
			motds = new Text[]{new LiteralText("Bestie didn't provide any MOTDS in config/random_motd.json\nMaybe you oughta do that. Anyway \u00a7bT\u00a7dR\u00a7fA\u00a7dN\u00a7bS \u00a7rrights")};
			chances = new Double[]{1.0};
		}
	}
}
