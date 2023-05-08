package com.pbr;

import com.pbr.service.CrudService;
import com.sun.management.HotSpotDiagnosticMXBean;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication(scanBasePackages = {"com.pbr"})

public class MySpringBootApplication {

    @Autowired
    CrudService crudService;

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

            // TODO your codes
//            crudService.test();

            // If you want to exit application after your code, you may do call this method.
//            System.exit(0);


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
