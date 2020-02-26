package io.syndesis.demo.debezium.gameprice.repository;

import io.syndesis.demo.debezium.gameprice.domain.GamePrice;
import org.springframework.data.repository.CrudRepository;

public interface GamePriceRepository extends CrudRepository<GamePrice, Integer> {
}
