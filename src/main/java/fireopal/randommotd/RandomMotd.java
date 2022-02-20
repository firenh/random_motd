package fireopal.randommotd;

import net.fabricmc.api.ModInitializer;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fireopal.randommotd.complex.ParseStringToComplexMotd;

public class RandomMotd implements ModInitializer {
	public static final String MODID = "random_motd";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final FOModVersion VERSION = FOModVersion.fromString("0.0.0");

	private static Config CONFIG;
	private static Random random = new Random();
	
	private static Text[] motds;

	public static Config getConfig() {
		return CONFIG;
	}

	public static Text getRandomMotd() {
		return getRandomMotd(random);
	}

	public static Text getRandomMotd(Random random) {
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
		int length = 0;

		for (String s : CONFIG.complex_motds) {
			motdsTemp.add(ParseStringToComplexMotd.parse(s).asText());
			length += 1;
		}

		for (String w : CONFIG.simple_motds) {
			motdsTemp.add(new LiteralText(w));
			length += 1;
		}

		for (int i = 0; i < length; i += 1) {
			double chance = (double) chancesTemp0.get(i) / (double) totalWeight;
			chancesTemp1.add(chance);

			if (chance > highestChance) {
				highestChance = chance;
			}
		}

		motds = motdsTemp.toArray(new Text[0]);

		if (motds.length == 0) {
			motds = new Text[]{new LiteralText("Bestie didn't provide any MOTDS in config/random_motd.json\nMaybe you oughta do that. Anyway \u00a7bT\u00a7dR\u00a7fA\u00a7dN\u00a7bS \u00a7rrights")};
		}
	}
}
