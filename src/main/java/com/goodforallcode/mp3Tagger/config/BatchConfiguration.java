package com.goodforallcode.mp3Tagger.config;

import com.goodforallcode.mp3Tagger.batch.DirectoryProcessor;
import com.goodforallcode.mp3Tagger.batch.JobCompletionNotificationListener;
import com.goodforallcode.mp3Tagger.model.input.DirectoryInput;
import com.goodforallcode.mp3Tagger.spotify.TokenGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static com.goodforallcode.mp3Tagger.config.Constants.JOB_NAME;

@Configuration
//with Spring boot 3.0, the @EnableBatchProcessing is discouraged
//Also, JobBuilderFactory and StepBuilderFactory are deprecated and it is recommended to use JobBuilder and StepBuilder classes with the name of the job or step builder.
//https://www.baeldung.com/spring-boot-spring-batch
@EnableTransactionManagement
public class BatchConfiguration {
    private final Environment env;
    @Value("${file.directory}")
    private String fileLocation = "C:\\temp\\playlist.txt";

    public BatchConfiguration(Environment env) {
        this.env = env;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema-all.sql")
                .build();
    }

    @Bean(name = "batchJpaVendorAdapter")
    public JpaVendorAdapter batchJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfBean =
                new LocalContainerEntityManagerFactoryBean();
        emfBean.setDataSource(dataSource());
        emfBean.setPackagesToScan("com.goodforallcode.playlistgenerator");
        emfBean.setBeanName("entityManagerFactory");
        emfBean.setJpaVendorAdapter(batchJpaVendorAdapter());

        Properties jpaProps = new Properties();
        jpaProps.put("hibernate.hbm2ddl.auto", env.getProperty(
                "spring.jpa.hibernate.ddl-auto", "none"));
        jpaProps.put("hibernate.jdbc.fetch_size", env.getProperty(
                "spring.jpa.properties.hibernate.jdbc.fetch_size",
                "200"));

        Integer batchSize = env.getProperty(
                "spring.jpa.properties.hibernate.jdbc.batch_size",
                Integer.class, 100);
        if (batchSize > 0) {
            jpaProps.put("hibernate.jdbc.batch_size", batchSize);
            jpaProps.put("hibernate.order_inserts", "true");
            jpaProps.put("hibernate.order_updates", "true");
        }

        jpaProps.put("hibernate.show_sql", env.getProperty(
                "spring.jpa.properties.hibernate.show_sql", "true"));
        jpaProps.put("hibernate.format_sql", env.getProperty(
                "spring.jpa.properties.hibernate.format_sql", "true"));

        emfBean.setJpaProperties(jpaProps);
        return emfBean;
    }

    @Bean
    /**
     * @param jobRepository persists metadata about batch jobs
     * @param listener
     * @param step1
     */
    public Job job(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .validator(validator())
                .flow(step1)
//                .start(step1)
                .end()
                .build();
    }

    @Bean
    public JobParametersValidator validator() {
        return new JobParametersValidator() {
            @Override
            public void validate(JobParameters parameters) throws JobParametersInvalidException {
                String fileName = fileLocation;
                if (StringUtils.isBlank(fileName)) {
                    throw new JobParametersInvalidException(
                            "file location parameter is required.");
                }
                try {
                    Path file = Paths.get(fileLocation);
                    if (Files.notExists(file) || !Files.isReadable(file)) {
                        throw new Exception("File did not exist or was not readable");
                    }
                } catch (Exception e) {
                    throw new JobParametersInvalidException(
                            "file location parameter needs to " +
                                    "be a valid file location.");
                }
            }
        };
    }
    @Bean
    public JdbcBatchItemWriter<DirectoryInput> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<DirectoryInput>().itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO DirectoryInput (directory) VALUES (:directory)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcBatchItemWriter<DirectoryInput> writer, FlatFileItemReader reader) throws Exception {
        TokenGenerator tokenGenerator = new TokenGenerator("savecuomo", true);
        String token = tokenGenerator.getToken();

        return new StepBuilder("addMp3Tags", jobRepository)
                .<DirectoryInput, Void>chunk(2, transactionManager)
                .reader(reader)
                .processor(processor(token))
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader reader() {
        return new FlatFileItemReaderBuilder().name("directoryReader")
                .resource(new FileSystemResource(fileLocation))
                .delimited()
                .names("Directory")
                .fieldSetMapper(new BeanWrapperFieldSetMapper() {{
                    setTargetType(DirectoryInput.class);
                }})
                .build();
    }


    public DirectoryProcessor processor(String token) {
        return new DirectoryProcessor(token);
    }

}
