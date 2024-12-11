package com.example.plugin.samples;

import com.example.plugin.inter.Greeting;
import org.pf4j.DefaultPluginManager;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lawrence Peng
 */
public class WelcomePlugin extends Plugin {

    private static final Logger log = LoggerFactory.getLogger(WelcomePlugin.class);

    public WelcomePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        log.info("WelcomePlugin.start()");
    }

    @Override
    public void stop() {
        log.info("WelcomePlugin.stop()");
    }


}
