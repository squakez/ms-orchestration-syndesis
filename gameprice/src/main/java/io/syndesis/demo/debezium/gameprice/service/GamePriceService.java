package io.syndesis.demo.debezium.gameprice.service;

import java.util.Optional;

import io.syndesis.demo.debezium.gameprice.domain.GamePrice;

public interface GamePriceService {

    void createGamePrice(GamePrice gamePrice);

    Optional<GamePrice> getGamePrice(Integer orderId);

    void updateGamePrice(GamePrice gamePrice);

    void deleteGamePrice(GamePrice gamePrice);

}
