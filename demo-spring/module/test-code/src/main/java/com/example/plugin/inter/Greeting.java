package com.example.plugin.inter;

import org.pf4j.ExtensionPoint;

public interface Greeting extends ExtensionPoint {

    String getGreeting();

}
