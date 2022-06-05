package com.asynch.completableFuture.restproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync //it runs task in the back using threadpool concept
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
         executor.setCorePoolSize(2);
         executor.setMaxPoolSize(2);
         executor.setQueueCapacity(60); //#of task can be in the queue at a time
         executor.setThreadNamePrefix("User Thread-");
         executor.initialize();
         return executor;
    }
}
