package com.quant.craft.backend.infrastructure.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FlywayConfig {

    @Bean
    @Profile("prod")
    public Flyway flyway(FlywayProperties flywayProperties) {
        Flyway flyway = Flyway.configure()
                .baselineOnMigrate(flywayProperties.isBaselineOnMigrate())
                .baselineVersion(flywayProperties.getBaselineVersion())
                .dataSource(flywayProperties.getUrl(), flywayProperties.getUser(), flywayProperties.getPassword())
                .load();

        flyway.repair();
        flyway.migrate();
        return flyway;
    }

    @Bean
    @Profile("prod")
    public FlywayProperties flywayProperties() {
     return new FlywayProperties();
    }
}
