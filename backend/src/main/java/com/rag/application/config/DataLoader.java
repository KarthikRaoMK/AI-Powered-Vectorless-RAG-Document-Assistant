package com.rag.application.config;

import com.rag.application.entity.Document;
import com.rag.application.repository.DocumentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner load(DocumentRepository repository) {

        return args -> {

            if (repository.count() == 0) {

                repository.save(new Document(
                        "Java",
                        "Java is an object oriented programming language."
                ));

                repository.save(new Document(
                        "Spring Boot",
                        "Spring Boot makes Java development easier."
                ));

                repository.save(new Document(
                        "MySQL",
                        "MySQL is a relational database."
                ));

                repository.save(new Document(
                        "Docker",
                        "Docker is a containerization platform."
                ));

                System.out.println("Documents Loaded");

            }

        };
    }

}