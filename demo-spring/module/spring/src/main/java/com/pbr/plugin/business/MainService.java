package com.pbr.plugin.business;

import com.pbr.plugin.dto.PluginDTO;
import com.pbr.plugin.extension.ExtensionPointA;
import com.pbr.plugin.service.PluginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Lawrence Peng
 */
@Slf4j
@Component
public class MainService {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 在实际业务代码中写下面这样的业务逻辑。
     */
    public Object doA(Object input) {

        PluginDTO pluginDTO = PluginService.PLUGIN_MAP.get(ExtensionPointA.class.getSimpleName());

        Object bean = null;

        if (pluginDTO == null) {
            bean = applicationContext.getBean(ExtensionPointA.class);
        } else {
            // Here we should run plugin A if some implements exists
            try {
                bean = applicationContext.getBean(pluginDTO.pluginId, ExtensionPointA.class);
            } catch (Exception e) {
                if (pluginDTO.useDefaultImpl) {
                    bean = applicationContext.getBean(ExtensionPointA.class);
                }
            }
        }

        Object result = input;
        if (bean instanceof ExtensionPointA extensionPointA) {
            result = extensionPointA.sayHello(input);
        }

        return result;
    }

}
