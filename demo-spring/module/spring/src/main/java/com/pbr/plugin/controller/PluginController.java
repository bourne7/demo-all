package com.pbr.plugin.controller;

import com.google.common.reflect.ClassPath;
import com.pbr.plugin.business.MainService;
import com.pbr.plugin.dto.PluginDTO;
import com.pbr.plugin.service.PluginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("plugin")
@Slf4j
public class PluginController {

    AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    MainService mainService;

    @Autowired
    PluginService pluginService;

    @RequestMapping(value = "test/doA", method = RequestMethod.GET)
    public Object doA() {
        return mainService.doA(counter.getAndIncrement());
    }

    @RequestMapping(value = "listClass", method = RequestMethod.GET)
    public Object listClass(@RequestParam(required = false, defaultValue = "com.pbr") String packageName) {

        List<String> list;

        try {
            ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
            Set<ClassPath.ClassInfo> classes = classPath.getAllClasses();

            list = classes.stream()
                    .filter(classInfo -> classInfo.getPackageName().startsWith(packageName))
                    .map(ClassPath.ClassInfo::getName)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    /**
     *
     */
    @RequestMapping(value = "loadPlugin", method = RequestMethod.POST)
    public Object loadPlugin(@RequestBody(required = false) PluginDTO pluginDTO) {

        if(pluginDTO ==null){
            pluginDTO = new PluginDTO();
            pluginDTO.pluginId = "com.pbr.ignore.MyExtensionPointAImpl";
            pluginDTO.fullClassName = "com.pbr.ignore.MyExtensionPointAImpl";
            pluginDTO.sourceCode = """
                    package com.pbr.ignore;
                     
                     import cn.hutool.core.util.RandomUtil;
                     import com.pbr.plugin.extension.ExtensionPointA;
                     import lombok.extern.slf4j.Slf4j;
                     
                     import java.util.ArrayList;
                     import java.util.List;
                     
                     @Slf4j
                     public class MyExtensionPointAImpl implements ExtensionPointA {
                     
                         public static List<String> MAP = new ArrayList<>();
                     
                         @Override
                         public Object sayHello(Object input) {
                     
                             for (int i = 0; i < 10000; i++) {
                                 MAP.add(i + "_" + RandomUtil.randomString(100));
                             }
                     
                             log.info("plugin for A {} MAP {}", input, MAP.size());
                             return input.toString().toUpperCase();
                         }
                     
                     }
                    """;

        }

        pluginService.loadPlugin(pluginDTO);
        return counter.getAndIncrement();
    }

    @RequestMapping(value = "unloadPlugin", method = RequestMethod.DELETE)
    public Object unloadPlugin(@RequestBody PluginDTO pluginDTO) {
        pluginService.unloadPlugin(pluginDTO);
        return counter.getAndIncrement();
    }

    @RequestMapping(value = "getAllPlugin", method = RequestMethod.GET)
    public Object getAllPlugin() {
        return PluginService.PLUGIN_MAP;
    }

}
