package io.syndesis.demo.debezium.gameprice.configuration;

import io.syndesis.demo.debezium.gameprice.service.GamePriceService;
import io.syndesis.demo.debezium.gameprice.service.GamePriceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class GamePriceBootConfiguration {

    @Bean
    public GamePriceService orderService(){
        return new GamePriceServiceImpl();
    }
}
