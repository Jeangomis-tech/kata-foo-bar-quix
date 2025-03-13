package com.jcgomis.kata_foo_bar_quix.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchLauncher {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired

    Job fooBarQuixJob;

    @Bean
    public CommandLineRunner run(){
        return args -> {
            //if(args.length > 0 && args[0].equals("--batch")){

           // }
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(fooBarQuixJob, jobParameters);
        };
    }
}
