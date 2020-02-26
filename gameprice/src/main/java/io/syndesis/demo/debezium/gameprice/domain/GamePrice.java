package io.syndesis.demo.debezium.gameprice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class GamePrice {

    @Id
    private int gameId;
    @NotNull
    private String gameTitle;
    private int gamePrice;

}