package com.smartlights.config;

import com.smartlights.entity.Light;
import com.smartlights.repository.LightRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    ApplicationRunner seedLights(LightRepository repo, @Value("${app.devices.count}") int deviceCount) {
        return args -> {
            if (repo.count() == 0) {
                List<Light> lightsToSeed = new ArrayList<>();

                for (int i = 1; i <= deviceCount; i++) {
                    String id = "L" + i;
                    String name = "Light " + i;
                    lightsToSeed.add(new Light(id, name, false, false));
                }

                repo.saveAll(lightsToSeed);
                System.out.println("Seeded " + deviceCount + " lights into SQL Server.");
            } else {
                System.out.println("Lights table already seeded. Skipping seed.");
            }
        };
    }
}

