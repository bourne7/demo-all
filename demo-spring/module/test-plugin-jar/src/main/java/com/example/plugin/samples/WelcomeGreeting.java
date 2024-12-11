package com.example.plugin.samples;

import com.example.plugin.inter.Greeting;
import org.pf4j.Extension;

/**
 * @author Lawrence Peng
 */
@Extension
public class WelcomeGreeting implements Greeting {

    public String getGreeting() {
        return "Welcome";
    }

}