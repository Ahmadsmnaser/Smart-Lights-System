package com.smartlights.repository;

import com.smartlights.model.LightStatusLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LightLogRepository extends MongoRepository<LightStatusLog, String> {

    List<LightStatusLog> findTop10ByLightIdOrderByTimestampDesc(String lightId);

    Optional<LightStatusLog> findTopByLightIdOrderByTimestampDesc(String lightId);
}
