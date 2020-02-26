package io.syndesis.demo.debezium.demo.userpreference.domain;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

import io.syndesis.demo.debezium.demo.userpreference.repository.StringListConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
public class UserPreference {

    @Id
    private int userId;
    @NonNull
    private String userName;
    @NonNull
    private String email;
    @Convert(converter = StringListConverter.class)
    private List<String> gamesLikedIds = new ArrayList<>();

    public void addLikedGame(String gameId) {
        gamesLikedIds.add(gameId);
    }

    public void deleteLikedGame(String gameId) {
        gamesLikedIds.remove(gameId);
    }
}