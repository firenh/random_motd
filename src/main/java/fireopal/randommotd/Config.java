package fireopal.randommotd;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import fireopal.randommotd.complex.Color;
import fireopal.randommotd.complex.ComplexMotd;
import fireopal.randommotd.complex.StringWithFormatting;
import fireopal.randommotd.util.WeightedMotd;
public class Config {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //Config Default Values

    public String CONFIG_VERSION_DO_NOT_TOUCH_PLS = RandomMotd.VERSION.toString();

    public List<String> simple_motds = defaultSimpleMotds();
    public List<String> complex_motds = defaultComplexMotds();
    public boolean log_when_loaded = false;
    public int loops = 5;
    
    private List<String> defaultSimpleMotds() {
        List<String> motds = new ArrayList<>();

        Collections.addAll(motds,
            "This is a simple MOTD",
            "Another simple MOTD"
        );

        return motds;
    }

    private List<String> defaultComplexMotds() {
        List<String> motds = new ArrayList<>();

        Collections.addAll(motds,
            "This is a <c#FF0000#0000FF> COMPLEX motd!!",
            "<b>Gradients work in<c#FFFF00#0000FF> complex motds"
        );

        return motds;
    }

    //~~~~~~~~

    public static Config init() {
        Config config = null;

        try {
            Path configPath = Paths.get("", "config", RandomMotd.MODID + ".json");

            if (Files.exists(configPath)) {
                config = gson.fromJson(
                    new FileReader(configPath.toFile()),
                    Config.class
                );

                if (!config.CONFIG_VERSION_DO_NOT_TOUCH_PLS.equals(RandomMotd.VERSION.toString())) {
                    config.CONFIG_VERSION_DO_NOT_TOUCH_PLS = RandomMotd.VERSION.toString();

                    BufferedWriter writer = new BufferedWriter(
                        new FileWriter(configPath.toFile())
                    );

                    writer.write(gson.toJson(config));
                    writer.close();
                }

            } else {
                config = new Config();
                Paths.get("", "config").toFile().mkdirs();

                BufferedWriter writer = new BufferedWriter(
                    new FileWriter(configPath.toFile())
                );

                writer.write(gson.toJson(config));
                writer.close();
            }


        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return config;
    }
}
