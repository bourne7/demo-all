package com.example.plugin.manager;

import com.example.plugin.inter.Greeting;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Lawrence Peng
 */
public class TestPlugin {

    private static final Logger log = LoggerFactory.getLogger(TestPlugin.class);

    public static void main(String[] args) {

        Path pluginsDir = Path.of(System.getProperty("pf4j.pluginsDir", "/Users/aac/Documents/git/demo-all/demo-spring/module/test-plugin-jar/build/libs"));

        PluginManager pluginManager = new DefaultPluginManager(pluginsDir);
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        pluginManager.getStartedPlugins().forEach(
                v -> log.info(v.getPluginId())
        );

        // pluginManager.enablePlugin("WelcomeGreeting");

        List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);

        for (Greeting greeting : greetings) {
            log.info(">>> {}", greeting.getGreeting());
        }

    }

}
