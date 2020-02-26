package io.syndesis.demo.debezium.gameprice.service;

import java.util.Optional;

import io.syndesis.demo.debezium.gameprice.domain.GamePrice;
import io.syndesis.demo.debezium.gameprice.repository.GamePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class GamePriceServiceImpl implements GamePriceService {

    @Autowired
    private GamePriceRepository gamePriceRepo;

    @Override
    public void createGamePrice(GamePrice gamePrice) {
        gamePriceRepo.save(gamePrice);
    }

    @Override
    public Optional<GamePrice> getGamePrice(Integer orderId) {
        return gamePriceRepo.findById(orderId);
    }

    @Override
    public void updateGamePrice(GamePrice gamePrice) {
        gamePriceRepo.save(gamePrice);
    }

    @Override
    public void deleteGamePrice(GamePrice gamePrice) {
        gamePriceRepo.delete(gamePrice);
    }

}
