package com.pbr;

import com.sun.management.HotSpotDiagnosticMXBean;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication(scanBasePackages = {"com.pbr"})
@EnableScheduling
@Slf4j
public class MySpringBootApplication {

    @Autowired
    ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }

    @PostConstruct
    public void init() {
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);
        for (String entry : classpathEntries) {
            log.info(entry);
        }

        String[] beanNames = applicationContext.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            log.info(beanName);
        }
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            // If you want to exit application after your code, you may do call this method.
            // System.exit(0);
            dump();
        };
    }

    @SneakyThrows
    private void dump() {
        String pid = ManagementFactory.getRuntimeMXBean().getName();

        pid = pid.substring(0, pid.indexOf('@'));

        String date = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(new Date());
        String fileName = "heap_" + pid + "_" + date + ".dump";

        HotSpotDiagnosticMXBean bean = ManagementFactory.newPlatformMXBeanProxy(
                ManagementFactory.getPlatformMBeanServer(),
                "com.sun.management:type=HotSpotDiagnostic",
                HotSpotDiagnosticMXBean.class);
        bean.setVMOption("HeapDumpOnOutOfMemoryError", "true");
        bean.setVMOption("HeapDumpPath", fileName);
    }
}
