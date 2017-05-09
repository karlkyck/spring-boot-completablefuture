package com.humansreadcode.example.config;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableMongoRepositories(DatabaseConfiguration.MONGO_REPOSITORIES_PACKAGE)
@Import(value = MongoAutoConfiguration.class)
public class DatabaseConfiguration {

    private static final String CHANGE_LOGS_SCAN_PACKAGE = "com.humansreadcode.example";
    static final String MONGO_REPOSITORIES_PACKAGE = "com.humansreadcode.example";

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public Mongobee mongobee(final MongoClient mongoClient, final MongoProperties mongoProperties) {
        final Mongobee mongobee = new Mongobee(mongoClient);
        mongobee.setDbName(mongoProperties.getDatabase());
        mongobee.setChangeLogsScanPackage(CHANGE_LOGS_SCAN_PACKAGE);
        mongobee.setEnabled(true);
        return mongobee;
    }
}
