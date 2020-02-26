package io.syndesis.demo.debezium.gameprice.controller;

import io.syndesis.demo.debezium.gameprice.domain.GamePrice;
import io.syndesis.demo.debezium.gameprice.service.GamePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gameprice")
public class GamePriceRestController {

    @Autowired
    private GamePriceService gamePriceService;

    @GetMapping("/{gameId}")
    public GamePrice get(@PathVariable int gameId){
        return gamePriceService.getGamePrice(gameId).get();
    }

    @PutMapping
    public GamePrice update(@RequestBody GamePrice gamePrice) {
        gamePriceService.updateGamePrice(gamePrice);
        return gamePrice;
    }

    @PostMapping
    public GamePrice create(@RequestBody GamePrice gamePrice) {
        gamePriceService.createGamePrice(gamePrice);
        return gamePrice;
    }

    @DeleteMapping("/{orderId}")
    public void delete(@PathVariable int orderId) {
        GamePrice gamePrice = get(orderId);
        gamePriceService.deleteGamePrice(gamePrice);
    }
}
