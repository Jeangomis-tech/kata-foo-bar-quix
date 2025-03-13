package com.jcgomis.kata_foo_bar_quix.batch;

import com.jcgomis.kata_foo_bar_quix.batch.processors.FooBarQuixProcessor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    //@Value("classpath:" + "input.txt")

   // private Resource fileInput;


    @Bean
    PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);

    }
    @Bean
    JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
         JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
         factory.setDataSource(dataSource);
         factory.setTransactionManager(transactionManager);
         factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
         factory.afterPropertiesSet();
         return factory.getObject();
    }
    @Bean
    JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;


    }
    @Bean
    public FlatFileItemReader<String>reader(){
        var fileInput= new FileSystemResource("input.txt");
        return new FlatFileItemReaderBuilder<String>()
                .name("fooBarQuixItemReader")
                .resource(fileInput)
                .lineMapper( new PassThroughLineMapper())
                .build();
    }
    @Bean
    public FooBarQuixProcessor fooBarQuixProcessor(){
        return new FooBarQuixProcessor();
    }

    @Bean
    public FlatFileItemWriter<String>writer(){
        return  new FlatFileItemWriterBuilder<String>()
                .name("fooBarQuixItemWriter")
                .resource( new FileSystemResource("src/main/resources/output.txt"))
                .lineAggregator( new PassThroughLineAggregator<>())
                .build();
    }
    @Bean
    public Job fooBarQuixJob(JobRepository jobRepository, Step fooBarQuixStep) {
        return new JobBuilder("fooBarQuixJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(fooBarQuixStep).end().build();
    }
    @Bean
    public Step fooBarQuixStep(JobRepository jobRepository,PlatformTransactionManager transactionManager,
                               FlatFileItemReader<String> reader, FooBarQuixProcessor fooBarQuixProcessor,
                               FlatFileItemWriter<String> writer) {
        return new StepBuilder("fooBarQuixStep", jobRepository)
                .<String, String>chunk(10,transactionManager)
                .reader(reader)
                .processor(fooBarQuixProcessor)
                .writer(writer)
                .build();

    }
}
