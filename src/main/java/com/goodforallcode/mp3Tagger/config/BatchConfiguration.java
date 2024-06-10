package com.goodforallcode.mp3Tagger.config;

import com.goodforallcode.mp3Tagger.batch.TaggingProcessor;
import com.goodforallcode.mp3Tagger.batch.JobCompletionNotificationListener;
import com.goodforallcode.mp3Tagger.batch.Mp3InfoToDatabaseProcessor;
import com.goodforallcode.mp3Tagger.batch.Mp3InfoPublishingProcessor;
import com.goodforallcode.mp3Tagger.model.domain.Mp3Info;
import com.goodforallcode.mp3Tagger.model.input.DirectoryInput;
import com.goodforallcode.mp3Tagger.spotify.TokenGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.data.mongodb.core.MongoTemplate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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


    @Bean
    /**
     * @param jobRepository persists metadata about batch jobs
     * @param listener
     * @param step1
     */
    public Job taggingJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1, Step step2, Step step3) {


        return new JobBuilder(JOB_NAME, jobRepository)
//                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .validator(validator())
//                .flow(step1)
                .start(step1)
                .next(step2)
                .next(step3)
//                .end() only for flow not for start
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
    public MongoItemWriter<Mp3Info> mp3Writer(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Mp3Info>().template(mongoTemplate).collection("Songs")
                .build();
    }
    @Bean
    public MongoItemWriter<DirectoryInput> directoryWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<DirectoryInput>().template(mongoTemplate).collection("Directories")
                .build();
    }
    @Bean
    public ItemWriter<List<Mp3Info>> collectionWriter(MongoTemplate mongoTemplate,MongoItemWriter<Mp3Info> mp3Writer) {

        return new ItemWriter<List<Mp3Info>>() {

            @Override
            public void write(Chunk<? extends List<Mp3Info>> chunk) throws Exception {

                Chunk<Mp3Info> totalChunk=new Chunk<>();
                for(List<Mp3Info> list : chunk) {
                    for(Mp3Info item : list) {
                        totalChunk.add(item);
                    }
                }
                if(totalChunk.getItems().size()>0) {
                    mp3Writer.write(totalChunk);
                }
            }

        };

    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,MongoItemWriter<DirectoryInput> directoryWriter, FlatFileItemReader reader) throws Exception {
        TokenGenerator tokenGenerator = new TokenGenerator("savecuomo", true);

        return new StepBuilder("tagMp3s", jobRepository)
                .<DirectoryInput, Void>chunk(2, transactionManager)
                .reader(reader)
                .processor(processor(tokenGenerator.getToken()))
                .writer(directoryWriter)
                .faultTolerant()
                .skipLimit(100)//defaults to 0
                .skip(Exception.class)
                .build();
    }
    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager,ItemWriter<List<Mp3Info>> mp3Writer, FlatFileItemReader reader) throws Exception {

        return new StepBuilder("summarizeAndWrite", jobRepository)
                .<DirectoryInput, List<Mp3Info>>chunk(2, transactionManager)
                .reader(reader)
                .processor(new Mp3InfoToDatabaseProcessor())
                .writer(mp3Writer)
                .faultTolerant()
                .skipLimit(20)
                .skip(Exception.class)
                .build();
    }

    @Bean
    public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager,ItemWriter<List<Mp3Info>> mp3Writer, FlatFileItemReader reader) throws Exception {
        TokenGenerator tokenGenerator = new TokenGenerator("savecuomo", true);
        String token = tokenGenerator.getToken();
        return new StepBuilder("publishPlaylist", jobRepository)
                .<DirectoryInput, List<Mp3Info>>chunk(2, transactionManager)
                .reader(reader)
                .processor(new Mp3InfoPublishingProcessor(token, tokenGenerator.getPlaylistId(), tokenGenerator.getSpotifyApi()))
                .writer(mp3Writer)
                .faultTolerant()
                .skipLimit(40)
                .skip(Exception.class)
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


    public TaggingProcessor processor(String token) {
        return new TaggingProcessor(token);
    }

}
