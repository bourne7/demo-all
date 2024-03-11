package com.pbr.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author Lawrence Peng
 */
@Configuration
@EnableAsync
@Slf4j
public class SpringAsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int coreProcessors = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(coreProcessors);
        executor.setMaxPoolSize(coreProcessors * 2);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("SpringAsyncExecutor-");
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {

        return (ex, method, params) -> {

            log.error("Global AsyncUncaughtExceptionHandler", ex);

            log.error("Class: {} method: {}", method.getDeclaringClass().getName(), method.getName());

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < params.length; i++) {
                sb.append("param ").append(i).append(": [").append(params[i]).append("] ; ");
            }
            log.error("params: {}", sb);
        };

    }


}
