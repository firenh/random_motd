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

public class Config {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //Config Default Values

    public String CONFIG_VERSION_DO_NOT_TOUCH_PLS = RandomMotd.VERSION.toString();

    public List<List<String>> motds = defaultSimpleMotds();
    public boolean use_randomized_icons = true;
    public List<String> icons = defaultServerIcons();
    public boolean log_when_loaded = false;
    
    private List<List<String>> defaultSimpleMotds() {
        List<List<String>> motds = new ArrayList<>();
        
        List<String> motds_0 = new ArrayList<>();
        Collections.addAll(motds_0,
            "RandomMOTD",
            "<rb>RandomMOTD</rb>"
        );
        motds.add(motds_0);

        List<String> motds_1 = new ArrayList<>();
        Collections.addAll(motds_1,
            "This is an MOTD",
            "Another MOTD",
            "This is an MOTD with <rb>formatting</rb>"
        );
        motds.add(motds_1);
        
        return motds;
    }

    //~~~~~~~~

    private List<String> defaultServerIcons() {
        ArrayList<String> list = new ArrayList<>();
        list.add("server-icon.png");
        return list;
    }

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
