package com.pbr.plugin.service;

import cn.hutool.core.compiler.JavaSourceCompiler;
import com.google.common.collect.Lists;
import com.pbr.plugin.dto.PluginDTO;
import com.pbr.plugin.extension.ExtensionPointA;
import com.pbr.utils.JsonUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lawrence Peng
 */
@Slf4j
@Component
public class PluginService {

    @Autowired
    ApplicationContext applicationContext;

    public static Map<String, PluginDTO> PLUGIN_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        PluginDTO pluginDTO = new PluginDTO();
        pluginDTO.pluginId = "MyDynamicBean";
        pluginDTO.fullClassName = "com.pbr.plugin.MyDynamicBean";
        pluginDTO.sourceCode = """
                package com.pbr.plugin;
                public class MyDynamicBean { 
                    public String sayHello() {
                        return "Hello from the dynamic bean!";
                    }
                }
                """;
        PLUGIN_MAP.put(pluginDTO.pluginId, pluginDTO);

        pluginDTO = new PluginDTO();
        pluginDTO.pluginId = "MyExtensionPointAImpl";
        pluginDTO.fullClassName = "com.pbr.ignore.MyExtensionPointAImpl";
        pluginDTO.sourceCode = """
                package com.pbr.ignore;
                  
                  import com.pbr.plugin.extension.ExtensionPointA;
                  import org.slf4j.Logger;
                  import org.slf4j.LoggerFactory;

                  public class MyExtensionPointAImpl implements ExtensionPointA {
                  
                      private static final Logger log = LoggerFactory.getLogger(MyExtensionPointAImpl.class);
                  
                        @Override
                        public Object sayHello(Object input) {
                            log.info("my plugin for A " + input);
                            return input.toString().toUpperCase();
                        }
                  
                  }
                  
                """;
        PLUGIN_MAP.put(ExtensionPointA.class.getSimpleName(), pluginDTO);
    }

    private BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return (BeanDefinitionRegistry) ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
    }

    public void loadPlugin(PluginDTO pluginDTO) {

        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);
        File[] jars = Arrays.stream(classpathEntries)
                .map(File::new)
                .filter(File::isFile)
                .toList()
                .toArray(new File[0]);

        JavaSourceCompiler javaSourceCompiler = JavaSourceCompiler
                .create(applicationContext.getClassLoader())
//                .addLibrary(jars)
                .addSource(pluginDTO.fullClassName, pluginDTO.sourceCode);

        List<String> options = new ArrayList<>();


        List<String> jarsLombok = Arrays.stream(classpathEntries)
                .filter(file -> file.contains("lombok"))
                .toList();

        options.add("-cp");
        options.add(classpath);
        options.add("-p");
        options.addAll(jarsLombok);

//        options = Lists.newArrayList("-cp", "lombok.jar", "-p", "lombok.jar");
//        options = Lists.newArrayList("-proc:full");
//        options = Lists.newArrayList("-processor", "lombok.launch.AnnotationProcessorHider$AnnotationProcessor");

        ClassLoader classLoader = javaSourceCompiler.compile(options);

        try {
            Class<?> aClass = classLoader.loadClass(pluginDTO.fullClassName);

            // Register the class as a bean
            BeanDefinitionRegistry registry = getBeanDefinitionRegistry();

            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(aClass);
            registry.registerBeanDefinition(pluginDTO.pluginId, beanDefinition);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Object pluginBean = applicationContext.getBean(pluginDTO.pluginId);

        log.info("Plugin {} initialed as a bean! ", JsonUtils.obj2String(pluginDTO));
    }

    public void unloadPlugin(PluginDTO pluginDTO) {
        BeanDefinitionRegistry beanDefinitionRegistry = getBeanDefinitionRegistry();
        if (beanDefinitionRegistry.containsBeanDefinition(pluginDTO.pluginId)) {
            beanDefinitionRegistry.removeBeanDefinition(pluginDTO.pluginId);
        }
    }

}
