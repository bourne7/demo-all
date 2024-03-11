package com.pbr.plugin.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@Slf4j
public class ExtensionPointADefaultImpl implements ExtensionPointA {

    @Override
    public Object sayHello(Object input) {
        log.info("default " + input);
        return input;
    }

}
