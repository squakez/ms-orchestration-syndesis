package io.syndesis.demo.debezium.demo.userpreference.repository;

import io.syndesis.demo.debezium.demo.userpreference.domain.UserPreference;
import org.springframework.data.repository.CrudRepository;

public interface UserPreferenceRepository extends CrudRepository<UserPreference, Integer> {
}
