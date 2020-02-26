package io.syndesis.demo.debezium.demo.userpreference.controller;

import java.util.List;

import io.syndesis.demo.debezium.demo.userpreference.domain.GameDTO;
import io.syndesis.demo.debezium.demo.userpreference.domain.UserPreference;
import io.syndesis.demo.debezium.demo.userpreference.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userpreference")
public class UserPreferenceRestController {

    @Autowired
    private UserPreferenceService userPreferenceService;

    @GetMapping("/{userId}")
    public UserPreference get(@PathVariable int userId){
        return userPreferenceService.getUserPreference(userId).get();
    }

    @GetMapping
    public List<UserPreference> list(@RequestParam(required = false) String gameLiked) {
       return userPreferenceService.allUserPreferenceWhoLikes(gameLiked);
    }

    @PostMapping
    public UserPreference create(@RequestBody UserPreference userPreference) {
        userPreferenceService.createUserPreference(userPreference);
        return userPreference;
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable int userId){
        UserPreference userPreference = get(userId);
        userPreferenceService.deleteUserPreference(userPreference);
    }

    @PostMapping("/{userId}/game")
    public void addGame(@PathVariable int userId, @RequestBody GameDTO game){
        UserPreference userPreference = get(userId);
        userPreferenceService.addGame(userPreference, String.valueOf(game.getGameId()));
        userPreferenceService.updateUserPreference(userPreference);
    }

    @DeleteMapping("/{userId}/game/{gameId}")
    public void deleteGame(@PathVariable int userId, @PathVariable int orderId){
        UserPreference userPreference = get(userId);
        userPreferenceService.deleteGame(userPreference, ""+orderId);
        userPreferenceService.updateUserPreference(userPreference);
    }
}
