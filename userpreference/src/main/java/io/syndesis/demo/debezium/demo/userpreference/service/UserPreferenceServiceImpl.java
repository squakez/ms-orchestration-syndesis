package io.syndesis.demo.debezium.demo.userpreference.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.syndesis.demo.debezium.demo.userpreference.domain.UserPreference;
import io.syndesis.demo.debezium.demo.userpreference.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPreferenceServiceImpl implements UserPreferenceService {

    @Autowired
    private UserPreferenceRepository userPreferenceRepo;

    @Override
    public void createUserPreference(UserPreference userPreference) {
        userPreferenceRepo.save(userPreference);
    }

    @Override
    public Optional<UserPreference> getUserPreference(Integer userId) {
        return userPreferenceRepo.findById(userId);
    }

    @Override
    public void updateUserPreference(UserPreference userPreference) {
        userPreferenceRepo.save(userPreference);
    }

    @Override
    public void deleteUserPreference(UserPreference userPreference) {
        userPreferenceRepo.delete(userPreference);
    }

    @Override
    public void addGame(UserPreference userPreference, String gameId) {
        userPreference.addLikedGame(gameId);
        userPreferenceRepo.save(userPreference);
    }

    @Override
    public void deleteGame(UserPreference userPreference, String gameId) {
        userPreference.deleteLikedGame(gameId);
    }

    @Override
    public List<UserPreference> allUserPreferenceWhoLikes(String gameId) {
        return StreamSupport.stream(userPreferenceRepo.findAll().spliterator(), false)
                .filter(userPreference -> userPreference.getGamesLikedIds().contains(gameId))
                .collect(Collectors.toList());
    }
}
