package com.goodforallcode.mp3Tagger.controller;

import com.goodforallcode.mp3Tagger.batch.JobCompletionNotificationListener;
import com.goodforallcode.mp3Tagger.model.domain.Mp3Info;
import com.goodforallcode.mp3Tagger.model.input.DirectoryCallInput;
import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.TextSearchOptions;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Accumulators.first;
import static com.mongodb.client.model.Accumulators.topN;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Sorts.descending;
import static java.util.Arrays.asList;

@RestController
public class GenerateController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Value("${spring.data.mongodb.uri}")
    String uri;

    @GetMapping("/directories/")
    public void generateDirectoryList(@RequestBody DirectoryCallInput directory) throws Exception {

        System.err.println("Getting path lists");
        List<Path> directories = Files.walk(Paths.get(directory.getDirectory()), 10)
                .filter(Files::isDirectory)
                .collect(Collectors.toList());
        System.err.println("Got path lists");
        List<String> pathStrings = directories.stream().map(Path::toAbsolutePath).map(Path::toString).collect(Collectors.toList());
        String filePath = "C:\\temp\\playlist.txt";
        try (
                PrintWriter pw = new PrintWriter(Files.newBufferedWriter(
                        Paths.get(filePath)))) {
            pathStrings.stream().forEach(pw::println);
        }
    }

    @GetMapping("/tag/")
    public void generateTags() throws Exception {
        Date date = new Date();
        JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder().addDate("launchDate", date)
                .toJobParameters());
    }

    @GetMapping("/duplicates/")
    public List<Mp3Info> getDuplicates() throws Exception {
        List<Mp3Info> results=new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("OwnedMusic");
            MongoCollection<Document> collection = database.getCollection("Songs");

            List<Bson> clauses = asList(
                    group(new Document("title", "$title").append("artist", "$artist")
                            ,topN("fileLocations",  descending("fileLocation"), asList("$fileLocation"), 10)
                     ,Accumulators.sum("count", 1))
//                            ,count() was not needed
//                    ,match(Filters.gt("$count", "1"))
                    ,sort(descending("count"))
            );


            AggregateIterable<Document> documents = collection.aggregate(clauses);
            Job job;

            List<String>urls=new ArrayList<>();
            Mp3Info currentMp3Info;
            List<Object> fileLocations;
            Document ids;
            for(Document doc:documents){
                fileLocations=doc.getList("fileLocations",Object.class);
                ids=(Document) doc.get("_id");
                for(Object fileLocation:fileLocations) {
                    currentMp3Info = new Mp3Info(String.valueOf(ids.get("title")),String.valueOf(ids.get("artist")));
                    currentMp3Info.setFileLocation(String.valueOf(fileLocation));
                    currentMp3Info.setCount(doc.getInteger("count"));
                    results.add(currentMp3Info);
                    }
            }
        } catch (MongoException mt) {
            mt.printStackTrace();
        }

        return results;
    }


}
