package io.syndesis.demo.debezium.demo.userpreference.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GameDTO {

    private int gameId;
    private String gameTitle;
}
