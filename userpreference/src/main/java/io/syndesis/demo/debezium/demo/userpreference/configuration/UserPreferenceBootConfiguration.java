package io.syndesis.demo.debezium.demo.userpreference.configuration;

import io.syndesis.demo.debezium.demo.userpreference.service.UserPreferenceService;
import io.syndesis.demo.debezium.demo.userpreference.service.UserPreferenceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class UserPreferenceBootConfiguration {

    @Bean
    public UserPreferenceService userService(){
        return new UserPreferenceServiceImpl();
    }
}
