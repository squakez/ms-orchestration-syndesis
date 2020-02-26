package io.syndesis.demo.debezium.demo.userpreference.service;

import java.util.List;
import java.util.Optional;

import io.syndesis.demo.debezium.demo.userpreference.domain.UserPreference;

public interface UserPreferenceService {

    void createUserPreference(UserPreference userPreference);

    Optional<UserPreference> getUserPreference(Integer userId);

    void updateUserPreference(UserPreference userPreference);

    void deleteUserPreference(UserPreference userPreference);

    void addGame(UserPreference userPreference, String gameId);

    void deleteGame(UserPreference userPreference, String gameId);

    List<UserPreference> allUserPreferenceWhoLikes(String gameId);
}
