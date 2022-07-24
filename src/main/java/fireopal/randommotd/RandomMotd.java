package fireopal.randommotd;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.pb4.placeholders.api.TextParserUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.text.Text;

public class RandomMotd implements ModInitializer {
	public static final String MODID = "random_motd";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final FOModVersion VERSION = FOModVersion.fromString("1.0.0");

	private static Config CONFIG;
	private static Random random = new Random();

	private static String[][] motds;
	private static String[] icons;

	public static Config getConfig() {
		return CONFIG;
	}

	public static Text getRandomMotd() {
		return getRandomMotd(random);
	}

	public static Text getRandomMotd(Random random) {
		String s = "";

		for (String[] w : motds) {
			s += w[random.nextInt(w.length)];
		}

		return TextParserUtils.formatText(s);
	}

	public static String getRandomIcon() {
		return getRandomIcon(random);
	}

	public static String getRandomIcon(Random random) {
		// String icon = 
		// LOGGER.info(icon);
		return "data:image/png;base64," + icons[random.nextInt(icons.length)];
	}

	public static boolean useRandomIcons() {
		// LOGGER.info(icons.length);
		return icons.length > 0;
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

		if (CONFIG.use_randomized_icons) {
			// LOGGER.info("Uses randomized icons");
			cacheIcons();
		} else {
			icons = new String[]{};
		}
    }

	private static void cacheMotds() {
		motds = CONFIG.motds.stream()
			.map(l -> l.stream().toArray(String[]::new))
			.toArray(String[][]::new);
	}

	private static void cacheIcons() {
		ArrayList<String> list = new ArrayList<>();

		for (String iconPath : CONFIG.icons) {
			Optional<File> optional = Optional.of(new File(".", iconPath)).filter(File::isFile);
			
			if (optional.isEmpty()) {
				LOGGER.info("Icon `" + iconPath + "` does not exist!");
				continue;
			}
			
			optional.ifPresent(file -> {
				try {
					BufferedImage bufferedImage = ImageIO.read(file);
					Validate.validState(bufferedImage.getWidth() == 64, "Icon `" + iconPath + "` is not 64 pixels wide!", new Object[0]);
					Validate.validState(bufferedImage.getHeight() == 64, "Icon `" + iconPath + "` is not 64 pixels high!", new Object[0]);
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					ImageIO.write((RenderedImage)bufferedImage, "PNG", byteArrayOutputStream);
					byte[] bs = Base64.getEncoder().encode(byteArrayOutputStream.toByteArray());
					String icon = new String(bs, StandardCharsets.UTF_8);
					list.add(icon);
					// LOGGER.info("Icon: " + icon);
				} catch (Exception exception) {
					LOGGER.info("Couldn't load icon `" + iconPath + "`!");
				}
			});
		}

		icons = list.toArray(String[]::new);
	}
}
